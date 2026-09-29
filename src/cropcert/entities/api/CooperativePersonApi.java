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

import cropcert.entities.filter.Permissions;
import cropcert.entities.filter.TokenAndUserAuthenticated;
import cropcert.entities.model.CooperativePerson;
import cropcert.entities.model.UnionEntities;
import cropcert.entities.service.CooperativePersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("coUser")
@Tag(name = "Cooperative person")
public class CooperativePersonApi {

	private CooperativePersonService coPersonService;

	@Inject
	public CooperativePersonApi(CooperativePersonService farmerService) {
		this.coPersonService = farmerService;
	}

	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get co-operative person by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cooperative person found", content = @Content(schema = @Schema(implementation = CooperativePerson.class))),
			@ApiResponse(responseCode = "204", description = "No content") })
	public Response find(@Context HttpServletRequest request, @PathParam("id") Long id) {
		CooperativePerson ccPerson = coPersonService.findByUserId(id);
		if (ccPerson == null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(ccPerson).build();
	}

	@Path("cocode/{cocode}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get union by its code")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Cooperative persons", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UnionEntities.class)))) })
	public Response findByCode(@Context HttpServletRequest request, @PathParam("cocode") Long coCode,
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		List<CooperativePerson> unionPerson;
		if (limit == -1 || offset == -1)
			unionPerson = coPersonService.findByCooperativeId(coCode, 0, 0, "coCode desc");
		else
			unionPerson = coPersonService.findByCooperativeId(coCode, limit, offset, "coCode desc");

		return Response.status(Status.CREATED).entity(unionPerson).build();
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get all the co-operatvie persons")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "All cooperative persons", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CooperativePerson.class)))) })
	public Response findAll(@Context HttpServletRequest request, @DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {

		List<CooperativePerson> coPersons;
		if (limit == -1 || offset == -1)
			coPersons = coPersonService.findAll();
		else
			coPersons = coPersonService.findAll(limit, offset);
		return Response.ok().entity(coPersons).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Save the co operative person")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Cooperative person saved", content = @Content(schema = @Schema(implementation = CooperativePerson.class))),
			@ApiResponse(responseCode = "204", description = "Creation failed") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response save(@Context HttpServletRequest request,
			@RequestBody(description = "Cooperative person json string", required = true) String jsonString) {
		CooperativePerson coPerson;
		try {
			coPerson = coPersonService.save(jsonString);
			return Response.status(Status.CREATED).entity(coPerson).build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).entity("Creation failed").build();
	}

	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@Operation(summary = "Delete the cooperative person by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "Cooperative person deleted", content = @Content(schema = @Schema(implementation = CooperativePerson.class))) })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response delete(@Context HttpServletRequest request, @PathParam("id") Long id) {
		CooperativePerson ccPerson = coPersonService.deleteByUserId(id);
		return Response.status(Status.ACCEPTED).entity(ccPerson).build();
	}

}
