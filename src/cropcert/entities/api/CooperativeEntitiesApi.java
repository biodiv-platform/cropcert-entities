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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cropcert.entities.filter.Permissions;
import cropcert.entities.filter.TokenAndUserAuthenticated;
import cropcert.entities.model.CooperativeEntity;
import cropcert.entities.service.CooperativeEntityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Path("co")
@Api("CooperativeEntites")
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
	@ApiOperation(value = "Get co-operative by id", response = CooperativeEntity.class)
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
	@ApiOperation(value = "Get co-opearative by its code", response = CooperativeEntity.class)
	public Response findByCode(@Context HttpServletRequest request, @PathParam("code") Long code) {
		CooperativeEntity cooperative = cooperativeEntityService.findByCode(code);
		if (cooperative == null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.ok().entity(cooperative).build();
	}

	@Path("union")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get list of co-operative from given union", response = CooperativeEntity.class, responseContainer = "List")
	public Response getByUnion(@Context HttpServletRequest request,
			@DefaultValue("-1") @QueryParam("unionCode") Long unionCode) {
		List<CooperativeEntity> cooperatives = cooperativeEntityService.getByUnion(unionCode);
		return Response.ok().entity(cooperatives).build();
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all the co-operative", response = CooperativeEntity.class, responseContainer = "List")
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
	@ApiOperation(value = "Save the co-operative", response = CooperativeEntity.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response save(@Context HttpServletRequest request, String jsonString) {
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
	@ApiOperation(value = "Delete the cooperative by id", response = CooperativeEntity.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response delete(@Context HttpServletRequest request, @PathParam("id") Long id) {
		CooperativeEntity cooperative = cooperativeEntityService.delete(id);
		return Response.status(Status.ACCEPTED).entity(cooperative).build();
	}

}
