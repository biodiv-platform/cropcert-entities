package cropcert.entities.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.StreamingOutput;

import org.json.JSONException;
import org.pac4j.core.profile.CommonProfile;

import com.google.common.io.Files;
import com.google.inject.Inject;
import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

import cropcert.entities.filter.Permissions;
import cropcert.entities.filter.TokenAndUserAuthenticated;
import cropcert.entities.model.CollectionCenterPerson;
import cropcert.entities.model.User;
import cropcert.entities.service.UserService;
import cropcert.entities.util.AuthUtility;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Path("user")
@Api("User")
public class UserApi {

	private UserService userService;

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

//	@POST
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	@ApiOperation(value = "Save the user", response = User.class)
//	@ApiImplicitParams({
//			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
//	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
//	public Response save(@Context HttpServletRequest request, String jsonString) {
//		User user;
//		try {
//			user = userService.save(jsonString);
//			return Response.status(Status.CREATED).entity(user).build();
//		} catch (IOException | JSONException e) {
//			e.printStackTrace();
//		}
//		return Response.status(Status.NO_CONTENT).entity("Creation failed").build();
//	}
	
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
