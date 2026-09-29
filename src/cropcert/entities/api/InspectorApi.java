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

import cropcert.entities.filter.Permissions;
import cropcert.entities.filter.TokenAndUserAuthenticated;
import cropcert.entities.model.Inspector;
import cropcert.entities.service.InspectorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("inspector")
@Tag(name = "Inspector")
public class InspectorApi {

	private InspectorService inspectorService;

	@Inject
	public InspectorApi(InspectorService inspectorService) {
		this.inspectorService = inspectorService;
	}

	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get the Inspector by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Inspector found", content = @Content(schema = @Schema(implementation = Inspector.class))),
			@ApiResponse(responseCode = "204", description = "No content") })
	public Response find(@Context HttpServletRequest request, @PathParam("id") Long id) {
		Inspector inspector = inspectorService.findById(id);
		if (inspector == null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(inspector).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Save the inspector person")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Inspector saved", content = @Content(schema = @Schema(implementation = Inspector.class))),
			@ApiResponse(responseCode = "204", description = "Creation failed") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response save(@Context HttpServletRequest request,
			@RequestBody(description = "Inspector json string", required = true) String jsonString) {
		Inspector inspector;
		try {
			inspector = inspectorService.save(jsonString);
			return Response.status(Status.CREATED).entity(inspector).build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).entity("Creation failed").build();
	}

	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@Operation(summary = "Delete the Inspector person by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "Inspector deleted", content = @Content(schema = @Schema(implementation = Inspector.class))) })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response delete(@Context HttpServletRequest request, @PathParam("id") Long id) {
		Inspector inspector = inspectorService.delete(id);
		return Response.status(Status.ACCEPTED).entity(inspector).build();
	}
}
