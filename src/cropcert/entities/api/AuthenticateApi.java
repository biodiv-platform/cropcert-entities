package cropcert.entities.api;

import java.util.Map;

import jakarta.inject.Inject;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import org.pac4j.core.profile.CommonProfile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cropcert.entities.filter.SecurityInterceptor;
import cropcert.entities.service.AuthenticateService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("auth")
@Tag(name = "Authenticate")
public class AuthenticateApi {

	private static final Logger logger = LoggerFactory.getLogger(AuthenticateApi.class);

	@Inject
	private AuthenticateService authenticateService;

	@POST
	@Path("renew")
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get new set of refresh token and access token")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "New set of tokens", content = @Content(schema = @Schema(implementation = Map.class))),
			@ApiResponse(responseCode = "406", description = "Invalid refresh token"),
			@ApiResponse(responseCode = "403", description = "Forbidden") })
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
