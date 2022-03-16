package cropcert.entities.api;

import java.util.Map;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.pac4j.core.profile.CommonProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Inject;

import cropcert.entities.filter.SecurityInterceptor;
import cropcert.entities.service.AuthenticateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Path("auth")
@Api(value = "Authenticate")
public class AuthenticateApi {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticateApi.class);

	@Inject
	private AuthenticateService authenticateService;

	@POST
	@Path("login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED)
	@ApiOperation(value = "Authenticate the user by userName and password", response = Map.class)
	public Response authenticate(@FormParam("userName") String userName, @FormParam("password") String password) {
		try {
			CommonProfile profile = authenticateService.authenticate(userName, password);
			Map<String, Object> result = authenticateService.buildTokenResponse(profile,
					Long.parseLong(profile.getId()), true);
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
		}
	}

	@POST
	@Path("renew")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get new set of refresh token and access token", response = Map.class)
	public Response getNewSetOfTokens(@QueryParam("refreshToken") String refreshToken) {

		// check for the valid refresh token
		CommonProfile profile = SecurityInterceptor.jwtAuthenticator.validateToken(refreshToken);
		if (profile == null) {
			System.out.println("Invalid refresh token");
			return Response.status(Response.Status.NOT_ACCEPTABLE).entity("Invalid refresh token").build();
		}
		try {
			Map<String, Object> result = authenticateService.buildTokenResponse(profile,
					Long.parseLong(profile.getId()), true);
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return Response.status(Response.Status.FORBIDDEN).entity(e.getMessage()).build();
		}
	}
}
