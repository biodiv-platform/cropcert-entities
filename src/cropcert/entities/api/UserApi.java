package cropcert.entities.api;

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
import com.google.inject.Inject;
import com.strandls.user.controller.UserServiceApi;
import com.strandls.user.pojo.UserDTO;
import com.strandls.user.pojo.UserRoles;

import cropcert.entities.Headers;
import cropcert.entities.model.CollectionCenterPerson;
import cropcert.entities.model.CooperativePerson;
import cropcert.entities.model.Farmer;
import cropcert.entities.model.ICSManager;
import cropcert.entities.model.Inspector;
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

//	@Path("{id}")
//	@GET
//	@Consumes(MediaType.TEXT_PLAIN)
//	@Produces(MediaType.APPLICATION_JSON)
//	@ApiOperation(value = "Get the user by id", response = User.class)
//	public Response find(@Context HttpServletRequest request, @PathParam("id") Long id) {
//		User user = userService.findById(id);
//		return Response.status(Status.CREATED).entity(user).build();
//	}

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

//	@Path("email/{email}")
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	@ApiOperation(value = "Get the user by email-id", response = User.class)
//	public Response getByEmail(@Context HttpServletRequest request,
//			@DefaultValue("") @PathParam("email") String email) {
//		User user = userService.getByEmail(email);
//		return Response.ok().entity(user).build();
//	}

//	@Path("userName/{userName}")
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	@ApiOperation(value = "Get the user by userName", response = User.class)
//	public Response getByUserName(@Context HttpServletRequest request,
//			@DefaultValue("") @PathParam("userName") String userName) {
//		User user = userService.getByUserName(userName);
//		return Response.ok().entity(user).build();
//	}

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

			if (userDTO == null && userRole != null && userRole.getRoles().isEmpty()) {
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
			} else {
				return Response.status(Status.BAD_REQUEST).entity("Unable to find Role details").build();
			}

			return Response.status(Status.CREATED).entity(user).build();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).entity("Creation failed").build();

	}

//	@POST
//	@Path("password")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
//	@ApiOperation(value = "update the user password", response = User.class)
//	public Response updatePassword(@Context HttpServletRequest request, @FormParam("password") String password) {
//		try {
//			if(request == null) throw new Exception("Token missing");
//			User user = userService.updatePassword(request, password);
//			return Response.status(Status.CREATED).entity(user).build();
//		} catch (Exception e) {
//			return Response.status(Status.BAD_REQUEST).entity("Password update failed").build();
//		}
//	}

//	@GET
//	@Path("sign")
//	@Consumes(MediaType.TEXT_PLAIN)
//	@ApiOperation(value = "Get the image by url", response = StreamingOutput.class)
//	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN, Permissions.ICS_MANAGER, Permissions.INSPECTOR })
//	public Response getSignature(@Context HttpServletRequest request) throws FileNotFoundException {
//		CommonProfile profile = AuthUtility.getCommonProfile(request);
//		Long id = Long.parseLong(profile.getId());
//		User user = userService.findById(id);
//		String sign = user.getSign();
//
//		if (sign == null)
//			return Response.status(Status.NO_CONTENT).entity("NO sign available").build();
//
//		String[] splits = sign.split("/");
//		int len = splits.length;
//		if (len <= 2)
//			return Response.status(Status.NO_CONTENT).entity("NO sign available").build();
//
//		String hashKey = splits[len - 2];
//		String image = splits[len - 1];
//
//		String fileLocation = userService.rootPath + File.separatorChar + hashKey + File.separatorChar + image;
//		InputStream in = new FileInputStream(new File(fileLocation));
//		
//		StreamingOutput sout;
//		sout = new StreamingOutput() {
//			@Override
//			public void write(OutputStream out) throws IOException, WebApplicationException {
//				byte[] buf = new byte[8192];
//				int c;
//				while ((c = in.read(buf, 0, buf.length)) > 0) {
//					out.write(buf, 0, c);
//					out.flush();
//				}
//				out.close();
//			}
//		};
//		
//		return Response.ok(sout).type("image/" + Files.getFileExtension(image)).build();
//	}

//	@Path("sign")
//	@POST
//	@Consumes(MediaType.MULTIPART_FORM_DATA)
//	@Produces(MediaType.APPLICATION_JSON)
//	@ApiOperation(value = "Upload the sign of the user", response = Map.class)
//	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN, Permissions.ICS_MANAGER, Permissions.INSPECTOR })
//	@ApiImplicitParams({
//			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
//	public Response uploadSignature(@Context HttpServletRequest request, @FormDataParam("sign") InputStream inputStream,
//			@FormDataParam("sign") FormDataContentDisposition fileDetails) throws IOException {
//
//		User user;
//		try {
//			user = userService.uploadSignature(request, inputStream, fileDetails);
//
//			return Response.status(Status.CREATED).entity(user).build();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//		return Response.status(Status.NO_CONTENT).entity("Creation failed").build();
//	}

//	@Path("{id}")
//	@DELETE
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.TEXT_PLAIN)
//	@ApiOperation(value = "Delete the user by id", response = CollectionCenterPerson.class)
//	@ApiImplicitParams({
//			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
//	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
//	public Response delete(@Context HttpServletRequest request, @PathParam("id") Long id) {
//		User user = userService.delete(id);
//		return Response.status(Status.ACCEPTED).entity(user).build();
//	}
}
