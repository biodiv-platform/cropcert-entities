package cropcert.entities.api;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
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

import cropcert.entities.filter.Permissions;
import cropcert.entities.filter.TokenAndUserAuthenticated;
import cropcert.entities.model.UnionEntities;
import cropcert.entities.service.UnionEntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Path("unionentities")
@Api("UnionEntities")
public class UnionEntitiesApi {

	private UnionEntityService unionEntityService;

	@Inject
	public UnionEntitiesApi(UnionEntityService unionEntityService) {
		this.unionEntityService = unionEntityService;
	}

	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get the Union by id", response = UnionEntities.class)
	public Response findbyId(@Context HttpServletRequest request, @PathParam("id") Long id) {
		UnionEntities union = unionEntityService.findById(id);
		if (union == null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(union).build();
	}

	@Path("code/{code}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get union by its code", response = UnionEntities.class)
	public Response findByCode(@Context HttpServletRequest request, @PathParam("code") Long code) {
		UnionEntities union = unionEntityService.findByCode(code);
		if (union == null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.ok().entity(union).build();
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all the Union", response = UnionEntities.class, responseContainer = "List")
	public Response findAll(@Context HttpServletRequest request, @DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		List<UnionEntities> unions;
		if (limit == -1 || offset == -1)
			unions = unionEntityService.findAll();
		else
			unions = unionEntityService.findAll(limit, offset);
		return Response.ok().entity(unions).build();
	}

	@Path("create")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Save the Union", response = UnionEntities.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response save(@Context HttpServletRequest request, String jsonString) {
		UnionEntities union;
		try {
			union = unionEntityService.save(jsonString);
			return Response.status(Status.CREATED).entity(union).build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).entity("Creation failed").build();
	}

	@Path("delete/{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Delete the union by id", response = UnionEntities.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response delete(@Context HttpServletRequest request, @PathParam("id") Long id) {
		UnionEntities union = unionEntityService.delete(id);
		return Response.status(Status.ACCEPTED).entity(union).build();
	}

}
