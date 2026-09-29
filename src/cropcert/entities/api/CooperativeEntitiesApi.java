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
import cropcert.entities.model.CooperativeEntity;
import cropcert.entities.service.CooperativeEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("co")
@Tag(name = "CooperativeEntites")
public class CooperativeEntitiesApi {

	private static final Logger logger = LoggerFactory.getLogger(CooperativeEntitiesApi.class);

	private CooperativeEntityService cooperativeEntityService;

	@Inject
	public CooperativeEntitiesApi(CooperativeEntityService cooperativeEntityService) {
		this.cooperativeEntityService = cooperativeEntityService;
	}

	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get co-operative by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cooperative found", content = @Content(schema = @Schema(implementation = CooperativeEntity.class))),
			@ApiResponse(responseCode = "204", description = "No content") })
	public Response find(@Context HttpServletRequest request, @PathParam("id") Long id) {
		CooperativeEntity cooperative = cooperativeEntityService.findById(id);
		if (cooperative == null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(cooperative).build();
	}

	@Path("code/{code}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get co-opearative by its code")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cooperative found", content = @Content(schema = @Schema(implementation = CooperativeEntity.class))),
			@ApiResponse(responseCode = "204", description = "No content") })
	public Response findByCode(@Context HttpServletRequest request, @PathParam("code") Long code) {
		CooperativeEntity cooperative = cooperativeEntityService.findByCode(code);
		if (cooperative == null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.ok().entity(cooperative).build();
	}

	@Path("union")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get list of co-operative from given union")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cooperatives by union", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CooperativeEntity.class)))) })
	public Response getByUnion(@Context HttpServletRequest request,
			@DefaultValue("-1") @QueryParam("unionCode") Long unionCode) {
		List<CooperativeEntity> cooperatives = cooperativeEntityService.getByUnion(unionCode);
		return Response.ok().entity(cooperatives).build();
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get all the co-operative")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "All cooperatives", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CooperativeEntity.class)))) })
	public Response findAll(@Context HttpServletRequest request, @DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {

		List<CooperativeEntity> cooperatives;
		if (limit == -1 || offset == -1)
			cooperatives = cooperativeEntityService.findAll();
		else
			cooperatives = cooperativeEntityService.findAll(limit, offset);
		return Response.ok().entity(cooperatives).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Save the co-operative")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Cooperative saved", content = @Content(schema = @Schema(implementation = CooperativeEntity.class))),
			@ApiResponse(responseCode = "204", description = "Creation failed") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response save(@Context HttpServletRequest request,
			@RequestBody(description = "Cooperative json string", required = true) String jsonString) {
		CooperativeEntity cooperative;
		try {
			cooperative = cooperativeEntityService.save(jsonString);
			return Response.status(Status.CREATED).entity(cooperative).build();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return Response.status(Status.NO_CONTENT).entity("Creation failed").build();
	}

	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@Operation(summary = "Delete the cooperative by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "Cooperative deleted", content = @Content(schema = @Schema(implementation = CooperativeEntity.class))) })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response delete(@Context HttpServletRequest request, @PathParam("id") Long id) {
		CooperativeEntity cooperative = cooperativeEntityService.delete(id);
		return Response.status(Status.ACCEPTED).entity(cooperative).build();
	}

}
