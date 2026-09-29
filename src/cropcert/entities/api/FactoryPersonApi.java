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
import cropcert.entities.model.CollectionCenterPerson;
import cropcert.entities.model.FactoryPerson;
import cropcert.entities.service.FactoryPersonService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("factory")
@Tag(name = "Factory person")
public class FactoryPersonApi {

	private FactoryPersonService factoryPersonService;

	private static final Logger logger = LoggerFactory.getLogger(FactoryPersonApi.class);

	@Inject
	public FactoryPersonApi(FactoryPersonService farmerService) {
		this.factoryPersonService = farmerService;
	}

	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get factory person by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Factory person found", content = @Content(schema = @Schema(implementation = FactoryPerson.class))),
			@ApiResponse(responseCode = "204", description = "No content") })
	public Response find(@Context HttpServletRequest request, @PathParam("id") Long id) {
		FactoryPerson factoryPerson = factoryPersonService.findById(id);
		if (factoryPerson == null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(factoryPerson).build();
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get all the factory persons")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "All factory persons", content = @Content(array = @ArraySchema(schema = @Schema(implementation = FactoryPerson.class)))) })
	public Response findAll(@Context HttpServletRequest request, @DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		List<FactoryPerson> factoryPersons;
		if (limit == -1 || offset == -1)
			factoryPersons = factoryPersonService.findAll();
		else
			factoryPersons = factoryPersonService.findAll(limit, offset);
		return Response.ok().entity(factoryPersons).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Save the factory person")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Factory person saved", content = @Content(schema = @Schema(implementation = FactoryPerson.class))),
			@ApiResponse(responseCode = "204", description = "Creating factory person failed") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response save(@Context HttpServletRequest request,
			@RequestBody(description = "Factory person json string", required = true) String jsonString) {
		FactoryPerson factoryPerson;
		try {
			factoryPerson = factoryPersonService.save(jsonString);
			return Response.status(Status.CREATED).entity(factoryPerson).build();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return Response.status(Status.NO_CONTENT).entity("Creating factory person failed").build();
	}

	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@Operation(summary = "Delete the factory person by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "Factory person deleted", content = @Content(schema = @Schema(implementation = CollectionCenterPerson.class))) })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response delete(@Context HttpServletRequest request, @PathParam("id") Long id) {
		FactoryPerson factoryPerson = factoryPersonService.delete(id);
		return Response.status(Status.ACCEPTED).entity(factoryPerson).build();
	}
}
