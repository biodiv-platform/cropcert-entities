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
import cropcert.entities.model.CollectionCenterPerson;
import cropcert.entities.model.UnionEntities;
import cropcert.entities.service.CollectionCenterPersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("ccUser")
@Tag(name = "Collection Center Person")
public class CollectionCenterPersonApi {

	private CollectionCenterPersonService ccPersonService;

	@Inject
	public CollectionCenterPersonApi(CollectionCenterPersonService farmerService) {
		this.ccPersonService = farmerService;
	}

	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get cc person by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "CC person found", content = @Content(schema = @Schema(implementation = CollectionCenterPerson.class))),
			@ApiResponse(responseCode = "204", description = "No content") })
	@TokenAndUserAuthenticated(permissions = { Permissions.CC_PERSON })
	public Response find(@Context HttpServletRequest request, @PathParam("id") Long id) {
		CollectionCenterPerson ccPerson = ccPersonService.findByUserId(id);
		if (ccPerson == null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(ccPerson).build();
	}

	@Path("cccode/{cccode}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get union by its code")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Collection center persons", content = @Content(array = @ArraySchema(schema = @Schema(implementation = UnionEntities.class)))) })
	public Response findByCode(@Context HttpServletRequest request, @PathParam("cccode") Long ccCode,
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		List<CollectionCenterPerson> ccPerson;
		if (limit == -1 || offset == -1)
			ccPerson = ccPersonService.findByCollectionCenterId(ccCode, 0, 0, "ccCode desc");
		else
			ccPerson = ccPersonService.findByCollectionCenterId(ccCode, limit, offset, "ccCode desc");

		return Response.status(Status.CREATED).entity(ccPerson).build();
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get all the cc persons")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "All cc persons", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CollectionCenterPerson.class)))) })
	@TokenAndUserAuthenticated(permissions = { Permissions.CC_PERSON })
	public Response findAll(@Context HttpServletRequest request, @DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {

		List<CollectionCenterPerson> ccPersons;
		if (limit == -1 || offset == -1)
			ccPersons = ccPersonService.findAll();
		else
			ccPersons = ccPersonService.findAll(limit, offset);

		return Response.ok().entity(ccPersons).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Save the cc person")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "CC person saved", content = @Content(schema = @Schema(implementation = CollectionCenterPerson.class))),
			@ApiResponse(responseCode = "204", description = "Creation failed") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response save(@Context HttpServletRequest request,
			@RequestBody(description = "CC person json string", required = true) String jsonString) {
		CollectionCenterPerson ccPerson;
		try {
			ccPerson = ccPersonService.save(jsonString);
			return Response.status(Status.CREATED).entity(ccPerson).build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).entity("Creation failed").build();
	}

	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@Operation(summary = "Delete the cc person by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "CC person deleted", content = @Content(schema = @Schema(implementation = CollectionCenterPerson.class))) })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response delete(@Context HttpServletRequest request, @PathParam("id") Long id) {
		CollectionCenterPerson ccPerson = ccPersonService.deleteByUserId(id);
		return Response.status(Status.ACCEPTED).entity(ccPerson).build();
	}

}
