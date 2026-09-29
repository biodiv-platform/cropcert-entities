package cropcert.entities.api;

import java.util.List;
import java.util.Map;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.pac4j.core.profile.CommonProfile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.strandls.authentication_utility.filter.ValidateUser;
import com.strandls.authentication_utility.util.AuthUtil;
import com.strandls.user.controller.AuthenticationServiceApi;
import com.strandls.user.controller.UserServiceApi;
import com.strandls.user.pojo.StringObjectMap;
import com.strandls.user.pojo.UserDTO;
import com.strandls.user.pojo.UserRoles;

import cropcert.entities.Headers;
import cropcert.entities.model.CollectionCenterEntity;
import cropcert.entities.model.CollectionCenterPerson;
import cropcert.entities.model.CooperativeEntity;
import cropcert.entities.model.CooperativePerson;
import cropcert.entities.model.ICSManager;
import cropcert.entities.model.Inspector;
import cropcert.entities.model.UnionEntities;
import cropcert.entities.model.UnionPerson;
import cropcert.entities.model.UserEntityDTO;
import cropcert.entities.service.CollectionCenterPersonService;
import cropcert.entities.service.CooperativePersonService;
import cropcert.entities.service.ICSManagerService;
import cropcert.entities.service.InspectorService;
import cropcert.entities.service.UnionPersonService;
import cropcert.entities.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import net.minidev.json.JSONArray;

@Path("user")
@Tag(name = "User")
public class UserApi {

	private UserService userService;

	@Inject
	private UserServiceApi userServiceApi;

	@Inject
	private AuthenticationServiceApi authenticationServiceApi;

	@Inject
	private Headers headers;

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
	@Operation(summary = "Get the current user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Current user data", content = @Content(schema = @Schema(implementation = Map.class))) })
	public Response getUser(@Context HttpServletRequest request) {
		Map<String, Object> myData = userService.getMyData(request);
		return Response.ok().entity(myData).build();
	}

	@GET
	@Path("union/all")
	@Produces(MediaType.APPLICATION_JSON)
	@ValidateUser
	@Operation(summary = "Get the current user unions")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Current user unions", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UnionEntities.class)))) })
	public Response getMyUnion(@Context HttpServletRequest request) {
		List<UnionEntities> result = userService.getMyUnionData(request);
		return Response.ok().entity(result).build();
	}

	@GET
	@Path("co/all")
	@Produces(MediaType.APPLICATION_JSON)
	@ValidateUser
	@Operation(summary = "Get the current user cooperatives")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Current user cooperatives", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CooperativeEntity.class)))) })
	public Response getMyCoopertive(@Context HttpServletRequest request) {
		List<CooperativeEntity> result = userService.getMyCoopertiveData(request);
		return Response.ok().entity(result).build();
	}

	@GET
	@Path("cc/all")
	@Produces(MediaType.APPLICATION_JSON)
	@ValidateUser
	@Operation(summary = "Get the current user collectionCenters")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Current user collection centers", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CollectionCenterEntity.class)))) })
	public Response getMyCollectionCenter(@Context HttpServletRequest request) {
		List<CollectionCenterEntity> result = userService.getMyCollectionCenterData(request);
		return Response.ok().entity(result).build();
	}

	@Path("union")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ValidateUser
	@Operation(summary = "Get list of co-operative from given union")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cooperatives by union", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CooperativeEntity.class)))) })
	public Response getCooperativesByUnion(@Context HttpServletRequest request,
			@DefaultValue("-1") @QueryParam("unionCodes") String unionCodes) {
		List<CooperativeEntity> cooperatives = userService.getCooperativesByUnion(request, unionCodes);
		return Response.ok().entity(cooperatives).build();
	}

	@Path("cooperative")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ValidateUser
	@Operation(summary = "Get list of co-operative from given union")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Collection centers by cooperative", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CollectionCenterEntity.class)))) })
	public Response getCollectionCenterByCooperative(@Context HttpServletRequest request,
			@DefaultValue("-1") @QueryParam("cooperativeCodes") String cooperativeCodes) {
		List<CollectionCenterEntity> cooperatives = userService.getCollectionCenterByCooperative(request,
				cooperativeCodes);
		return Response.ok().entity(cooperatives).build();
	}

	@POST
	@Path("signup")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@ValidateUser
	@Operation(summary = "Create new user", description = "Returns the created user")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "User created", content = @Content(schema = @Schema(implementation = UserDTO.class))),
			@ApiResponse(responseCode = "400", description = "Bad request"),
			@ApiResponse(responseCode = "204", description = "Creation failed") })
	public Response signUp(@Context HttpServletRequest request,
			@RequestBody(description = "User entity DTO", required = true, content = @Content(schema = @Schema(implementation = UserEntityDTO.class))) UserEntityDTO userEntityDTO) {

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

			StringObjectMap response = authenticationServiceApi.signUp(userDTO);

			UserDTO user = null;
			if (response != null && response.getData() != null) {
				user = om.convertValue(response.getData().get("user"), UserDTO.class);
			}

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
			}

			return Response.status(Status.CREATED).entity(user).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).entity("Creation failed").build();

	}
}
