package cropcert.entities.api;

import java.io.IOException;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cropcert.entities.filter.Permissions;
import cropcert.entities.filter.TokenAndUserAuthenticated;
import cropcert.entities.model.ICSManager;
import cropcert.entities.model.UnionPerson;
import cropcert.entities.service.ICSManagerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("icsManager")
@Tag(name = "ICSManager")
public class ICSManagerApi {

	private ICSManagerService icsManagerService;

	private static final Logger logger = LoggerFactory.getLogger(ICSManagerApi.class);

	@Inject
	public ICSManagerApi(ICSManagerService icsManagerService) {
		this.icsManagerService = icsManagerService;
	}

	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get the ICS Manager by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "ICS Manager found", content = @Content(schema = @Schema(implementation = UnionPerson.class))),
			@ApiResponse(responseCode = "204", description = "No content") })
	public Response find(@Context HttpServletRequest request, @PathParam("id") Long id) {
		ICSManager icsManager = icsManagerService.findById(id);
		if (icsManager == null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(icsManager).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Save the ics manager")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "ICS Manager saved", content = @Content(schema = @Schema(implementation = UnionPerson.class))),
			@ApiResponse(responseCode = "204", description = "Creation failed") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response save(@Context HttpServletRequest request,
			@RequestBody(description = "ICS manager json string", required = true) String jsonString) {
		ICSManager icsManager;
		try {
			icsManager = icsManagerService.save(jsonString);
			return Response.status(Status.CREATED).entity(icsManager).build();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return Response.status(Status.NO_CONTENT).entity("Creating ics manager failed").build();
	}

	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@Operation(summary = "Delete the ICS Manger by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "ICS Manager deleted", content = @Content(schema = @Schema(implementation = UnionPerson.class))) })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response delete(@Context HttpServletRequest request, @PathParam("id") Long id) {
		ICSManager icsManager = icsManagerService.delete(id);
		return Response.status(Status.ACCEPTED).entity(icsManager).build();
	}
}
