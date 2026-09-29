package cropcert.entities.api;

import java.io.IOException;
import java.util.List;

import jakarta.inject.Inject;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.Response.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cropcert.entities.filter.Permissions;
import cropcert.entities.filter.TokenAndUserAuthenticated;
import cropcert.entities.model.UnionEntities;
import cropcert.entities.service.UnionEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("union")
@Tag(name = "UnionEntities")
public class UnionEntitiesApi {

	private static final Logger logger = LoggerFactory.getLogger(UnionEntitiesApi.class);

	private UnionEntityService unionService;

	@Inject
	public UnionEntitiesApi(UnionEntityService collectionCenterService) {
		this.unionService = collectionCenterService;
	}

	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get the Union by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Union found", content = @Content(schema = @Schema(implementation = UnionEntities.class))),
			@ApiResponse(responseCode = "204", description = "No content") })
	public Response find(@Context HttpServletRequest request, @PathParam("id") Long id) {
		UnionEntities union = unionService.findById(id);
		if (union == null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(union).build();
	}

	@Path("code/{code}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get union by its code")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Union found", content = @Content(schema = @Schema(implementation = UnionEntities.class))),
			@ApiResponse(responseCode = "204", description = "No content") })
	public Response findByCode(@Context HttpServletRequest request, @PathParam("code") Long code) {
		UnionEntities union = unionService.findByCode(code);
		if (union == null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.ok().entity(union).build();
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get all the Union")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "All unions", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UnionEntities.class)))) })
	public Response findAll(@Context HttpServletRequest request, @DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		List<UnionEntities> unions;
		if (limit == -1 || offset == -1)
			unions = unionService.findAll();
		else
			unions = unionService.findAll(limit, offset);
		return Response.ok().entity(unions).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Save the Union")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Union saved", content = @Content(schema = @Schema(implementation = UnionEntities.class))),
			@ApiResponse(responseCode = "204", description = "Creation failed") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response save(@Context HttpServletRequest request,
			@RequestBody(description = "Union json string", required = true) String jsonString) {
		UnionEntities union;
		try {
			union = unionService.save(jsonString);
			return Response.status(Status.CREATED).entity(union).build();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return Response.status(Status.NO_CONTENT).entity("Creation failed").build();
	}

	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@Operation(summary = "Delete the union by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "Union deleted", content = @Content(schema = @Schema(implementation = UnionEntities.class))) })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response delete(@Context HttpServletRequest request, @PathParam("id") Long id) {
		UnionEntities union = unionService.delete(id);
		return Response.status(Status.ACCEPTED).entity(union).build();
	}
}
