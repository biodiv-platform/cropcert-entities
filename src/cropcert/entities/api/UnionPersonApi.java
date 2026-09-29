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
import cropcert.entities.model.UnionPerson;
import cropcert.entities.service.UnionPersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("unionPerson")
@Tag(name = "Union person")
public class UnionPersonApi {

	private UnionPersonService unionPersonService;

	private static final Logger logger = LoggerFactory.getLogger(UnionPersonApi.class);

	@Inject
	public UnionPersonApi(UnionPersonService unionService) {
		this.unionPersonService = unionService;
	}

	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get the Union person by user id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Union person found", content = @Content(schema = @Schema(implementation = UnionPerson.class))),
			@ApiResponse(responseCode = "204", description = "No content") })
	public Response findByUserId(@Context HttpServletRequest request, @PathParam("id") Long id) {
		UnionPerson unionPerson = unionPersonService.findByUserId(id);
		if (unionPerson == null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(unionPerson).build();
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get all the Union")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "All union persons", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UnionPerson.class)))) })
	public Response findAll(@Context HttpServletRequest request, @DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		List<UnionPerson> unions;
		if (limit == -1 || offset == -1)
			unions = unionPersonService.findAll();
		else
			unions = unionPersonService.findAll(limit, offset);
		return Response.ok().entity(unions).build();
	}

	@Path("unioncode/{unionCode}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get the Union person by user id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Union persons by code", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UnionPerson.class)))) })
	public Response findByCode(@Context HttpServletRequest request, @PathParam("unionCode") Long unionCode,
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		List<UnionPerson> unionPerson;
		if (limit == -1 || offset == -1)
			unionPerson = unionPersonService.findByUnionId(unionCode, 0, 0, "unionCode desc");
		else
			unionPerson = unionPersonService.findByUnionId(unionCode, limit, offset, "unionCode desc");

		return Response.status(Status.CREATED).entity(unionPerson).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Save the union person")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Union person saved", content = @Content(schema = @Schema(implementation = UnionPerson.class))),
			@ApiResponse(responseCode = "204", description = "Creation failed") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response save(@Context HttpServletRequest request,
			@RequestBody(description = "Union person json string", required = true) String jsonString) {
		UnionPerson unionPerson;
		try {
			unionPerson = unionPersonService.save(jsonString);
			return Response.status(Status.CREATED).entity(unionPerson).build();
		} catch (IOException e) {
			logger.error(e.getMessage());

		}
		return Response.status(Status.NO_CONTENT).entity("Creating union person failed").build();
	}

	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@Operation(summary = "Delete the Union person by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "Union person deleted", content = @Content(schema = @Schema(implementation = UnionPerson.class))) })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response delete(@Context HttpServletRequest request, @PathParam("id") Long id) {
		UnionPerson unionPerson = unionPersonService.deleteByUserId(id);
		return Response.status(Status.ACCEPTED).entity(unionPerson).build();
	}
}
