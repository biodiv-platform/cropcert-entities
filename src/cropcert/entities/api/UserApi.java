package cropcert.entities.api;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.pac4j.core.profile.CommonProfile;

import com.fasterxml.jackson.databind.ObjectMapper;
import javax.inject.Inject;

import com.strandls.user.controller.UserServiceApi;
import com.strandls.user.pojo.UserDTO;
import com.strandls.user.pojo.UserRoles;

import cropcert.entities.Headers;
import cropcert.entities.model.CollectionCenterEntity;
import cropcert.entities.model.CollectionCenterPerson;
import cropcert.entities.model.CooperativeEntity;
import cropcert.entities.model.CooperativePerson;
import cropcert.entities.model.Farmer;
import cropcert.entities.model.ICSManager;
import cropcert.entities.model.Inspector;
import cropcert.entities.model.UnionEntities;
import cropcert.entities.model.UnionPerson;
import cropcert.entities.model.UserEntityDTO;
import cropcert.entities.service.CollectionCenterPersonService;
import cropcert.entities.service.CooperativePersonService;
import cropcert.entities.service.FarmerService;
import cropcert.entities.service.ICSManagerService;
import cropcert.entities.service.InspectorService;
import cropcert.entities.service.UnionPersonService;
import cropcert.entities.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import net.minidev.json.JSONArray;

import com.strandls.authentication_utility.filter.ValidateUser;
import com.strandls.authentication_utility.util.AuthUtil;
import com.strandls.user.controller.AuthenticationServiceApi;

@Path("user")
@Api("User")
public class UserApi {

	private UserService userService;

	@Inject
	private UserServiceApi userServiceApi;

	@Inject
	private AuthenticationServiceApi authenticationServiceApi;

	@Inject
	private Headers headers;

	@Inject
	private FarmerService farmerService;

	@Inject
	private CooperativePersonService cooperativePersonService;

	@Inject
	private InspectorService inspectorService;

	@Inject
	private CollectionCenterPersonService collectionCenterPersonService;

	@Inject
	private ICSManagerService icsManagerService;

	@Inject
	private UnionPersonService unionPersonService;

	@Inject
	private ObjectMapper om;

	@Inject
	public UserApi(UserService userService) {
		this.userService = userService;
	}

	@GET
	@Path("me")
	@Produces(MediaType.APPLICATION_JSON)

	@ValidateUser

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@ApiOperation(value = "Get the current user", response = Map.class)
	public Response getUser(@Context HttpServletRequest request) {
		Map<String, Object> myData = userService.getMyData(request);
		return Response.ok().entity(myData).build();
	}

	@GET
	@Path("union/all")
	@Produces(MediaType.APPLICATION_JSON)

	@ValidateUser

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@ApiOperation(value = "Get the current user unions", response = Map.class)
	public Response getMyUnion(@Context HttpServletRequest request) {
		List<UnionEntities> result = userService.getMyUnionData(request);
		return Response.ok().entity(result).build();
	}

	@GET
	@Path("co/all")
	@Produces(MediaType.APPLICATION_JSON)

	@ValidateUser

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@ApiOperation(value = "Get the current user cooperatives", response = Map.class)
	public Response getMyCoopertive(@Context HttpServletRequest request) {
		List<CooperativeEntity> result = userService.getMyCoopertiveData(request);
		return Response.ok().entity(result).build();
	}

	@GET
	@Path("cc/all")
	@Produces(MediaType.APPLICATION_JSON)

	@ValidateUser

	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@ApiOperation(value = "Get the current user collectionCenters", response = Map.class)
	public Response getMyCollectionCenter(@Context HttpServletRequest request) {
		List<CollectionCenterEntity> result = userService.getMyCollectionCenterData(request);
		return Response.ok().entity(result).build();
	}

	@POST
	@Path("signup")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)

	@ValidateUser

	@ApiOperation(value = "Create new user", notes = "Returns the created user", response = Map.class)
	public Response signUp(@Context HttpServletRequest request,
			@ApiParam(name = "userDTO") UserEntityDTO userEntityDTO) {

		CommonProfile profile = AuthUtil.getProfileFromRequest(request);
		JSONArray roles = (JSONArray) profile.getAttribute("roles");
		if (!roles.contains("ROLE_ADMIN")) {
			return Response.status(Status.BAD_REQUEST).entity("Unable to create user").build();

		}

		try {
			UserDTO userDTO = userEntityDTO.getUser();
			UnionPerson unionPerson = userEntityDTO.getUnionPerson();
			Inspector inspector = userEntityDTO.getInspector();
			ICSManager icsManager = userEntityDTO.getIscManager();
			Farmer farmer = userEntityDTO.getFarmer();
			CooperativePerson coPerson = userEntityDTO.getCoPerson();
			CollectionCenterPerson ccPerson = userEntityDTO.getCcPerson();
			UserRoles userRole = userEntityDTO.getUserRole();

			if (userDTO == null || userRole == null || userRole.getRoles() == null || userRole.getRoles().isEmpty()) {
				return Response.status(Status.BAD_REQUEST).entity("User details cannot be empty").build();
			}

//			user create

			authenticationServiceApi = headers.addAuthHeaders(authenticationServiceApi,
					request.getHeader(HttpHeaders.AUTHORIZATION));
			userServiceApi = headers.addUserHeaders(userServiceApi, request.getHeader(HttpHeaders.AUTHORIZATION));

			Map<String, Object> response = authenticationServiceApi.signUp(userDTO);

			UserDTO user = om.convertValue(response.get("user"), UserDTO.class);

			if (user == null) {
				return Response.status(Status.BAD_REQUEST).entity("User details cannot be empty").build();
			}

			userRole.setId(user.getId());
			userServiceApi.updateUserRoles(userRole);

//			user role update
			if (unionPerson != null) {
				unionPerson.setUserId(user.getId());
				unionPersonService.save(unionPerson);
			} else if (inspector != null) {
				inspector.setUserId(user.getId());
				inspectorService.save(inspector);
			} else if (icsManager != null) {
				icsManager.setUserId(user.getId());
				icsManagerService.save(icsManager);
			} else if (coPerson != null) {
				coPerson.setUserId(user.getId());
				cooperativePersonService.save(coPerson);
			} else if (ccPerson != null) {
				ccPerson.setUserId(user.getId());
				collectionCenterPersonService.save(ccPerson);
			} else if (farmer != null) {
				farmer.setUserId(user.getId());
				farmerService.save(farmer);
			}

			return Response.status(Status.CREATED).entity(user).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).entity("Creation failed").build();

	}
}
