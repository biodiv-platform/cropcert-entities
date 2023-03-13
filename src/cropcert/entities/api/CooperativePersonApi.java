package cropcert.entities.api;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.json.JSONException;

import javax.inject.Inject;

import cropcert.entities.filter.Permissions;
import cropcert.entities.filter.TokenAndUserAuthenticated;
import cropcert.entities.model.CooperativePerson;
import cropcert.entities.service.CooperativePersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Path("coUser")
@Api("Cooperative person")
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
	@ApiOperation(value = "Get co-operative person by id", response = CooperativePerson.class)
	public Response find(@Context HttpServletRequest request, @PathParam("id") Long id) {
		CooperativePerson ccPerson = coPersonService.findById(id);
		if (ccPerson == null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(ccPerson).build();
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all the co-operatvie persons", response = CooperativePerson.class, responseContainer = "List")
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
	@ApiOperation(value = "Save the co operative person", response = CooperativePerson.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response save(@Context HttpServletRequest request, String jsonString) {
		CooperativePerson coPerson;
		try {
			coPerson = coPersonService.save(jsonString);
			return Response.status(Status.CREATED).entity(coPerson).build();
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).entity("Creation failed").build();
	}

	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Delete the cooperative person by id", response = CooperativePerson.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response delete(@Context HttpServletRequest request, @PathParam("id") Long id) {
		CooperativePerson ccPerson = coPersonService.delete(id);
		return Response.status(Status.ACCEPTED).entity(ccPerson).build();
	}

}
