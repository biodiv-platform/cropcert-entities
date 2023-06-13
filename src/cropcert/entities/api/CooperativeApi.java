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

import javax.inject.Inject;

import cropcert.entities.filter.Permissions;
import cropcert.entities.filter.TokenAndUserAuthenticated;
import cropcert.entities.model.CollectionCenterPerson;
import cropcert.entities.model.Cooperative;
import cropcert.entities.service.CooperativeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Path("co")
@Api("Cooperative")
public class CooperativeApi {

	private CooperativeService cooperativeService;

	@Inject
	public CooperativeApi(CooperativeService cooperativeService) {
		this.cooperativeService = cooperativeService;
	}

	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get co-operative by id", response = Cooperative.class)
	public Response find(@Context HttpServletRequest request, @PathParam("id") Long id) {
		Cooperative cooperative = cooperativeService.findById(id);
		if (cooperative == null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(cooperative).build();
	}

	@Path("code/{code}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get co-opearative by its code", response = Cooperative.class)
	public Response findByCode(@Context HttpServletRequest request, @PathParam("code") Long code) {
		Cooperative cooperative = cooperativeService.findByCode(code);
		if (cooperative == null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.ok().entity(cooperative).build();
	}

	@Path("union")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get list of co-operative from given union", response = Cooperative.class, responseContainer = "List")
	public Response getByUnion(@Context HttpServletRequest request,
			@DefaultValue("-1") @QueryParam("unionCode") Long unionCode) {
		List<Cooperative> cooperatives = cooperativeService.getByPropertyWithCondtion("unionCode", unionCode, "=", -1,
				-1, "name");
		return Response.ok().entity(cooperatives).build();
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all the co-operative", response = Cooperative.class, responseContainer = "List")
	public Response findAll(@Context HttpServletRequest request, @DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {

		List<Cooperative> cooperatives;
		if (limit == -1 || offset == -1)
			cooperatives = cooperativeService.findAll();
		else
			cooperatives = cooperativeService.findAll(limit, offset);
		return Response.ok().entity(cooperatives).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Save the co-operative", response = Cooperative.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response save(@Context HttpServletRequest request, String jsonString) {
		Cooperative cooperative;
		try {
			cooperative = cooperativeService.save(jsonString);
			return Response.status(Status.CREATED).entity(cooperative).build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).entity("Creation failed").build();
	}

	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Delete the cooperative by id", response = CollectionCenterPerson.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response delete(@Context HttpServletRequest request, @PathParam("id") Long id) {
		Cooperative cooperative = cooperativeService.delete(id);
		return Response.status(Status.ACCEPTED).entity(cooperative).build();
	}
}
