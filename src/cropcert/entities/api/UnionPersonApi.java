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
import cropcert.entities.model.UnionPerson;
import cropcert.entities.service.UnionPersonService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Path("unionPerson")
@Api("Union person")
public class UnionPersonApi {

	private UnionPersonService unionPersonService;

	@Inject
	public UnionPersonApi(UnionPersonService unionService) {
		this.unionPersonService = unionService;
	}

	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get the Union person by user id", response = UnionPerson.class)
	public Response findByUserId(@Context HttpServletRequest request, @PathParam("id") Long id) {
		UnionPerson unionPerson = unionPersonService.findByUserId(id);
		if (unionPerson == null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(unionPerson).build();
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all the Union", response = UnionPerson.class, responseContainer = "List")
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
	@ApiOperation(value = "Get the Union person by user id", response = UnionPerson.class)
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
	@ApiOperation(value = "Save the union person", response = UnionPerson.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response save(@Context HttpServletRequest request, String jsonString) {
		UnionPerson unionPerson;
		try {
			unionPerson = unionPersonService.save(jsonString);
			return Response.status(Status.CREATED).entity(unionPerson).build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).entity("Creation failed").build();
	}

	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Delete the Union person by id", response = UnionPerson.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response delete(@Context HttpServletRequest request, @PathParam("id") Long id) {
		UnionPerson unionPerson = unionPersonService.deleteByUserId(id);
		return Response.status(Status.ACCEPTED).entity(unionPerson).build();
	}
}
