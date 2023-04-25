package cropcert.entities.api;

import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.pac4j.core.profile.CommonProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
	@Path("renew")
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get new set of refresh token and access token", response = Map.class)
	public Response getNewSetOfTokens(@QueryParam("refreshToken") String refreshToken) {

		// check for the valid refresh token
		CommonProfile profile = SecurityInterceptor.jwtAuthenticator.validateToken(refreshToken);
		if (profile == null) {
			logger.error("Invalid refresh token");
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
