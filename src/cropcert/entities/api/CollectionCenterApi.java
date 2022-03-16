package cropcert.entities.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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

import com.google.inject.Inject;

import cropcert.entities.filter.Permissions;
import cropcert.entities.filter.TokenAndUserAuthenticated;
import cropcert.entities.model.CollectionCenter;
import cropcert.entities.model.CollectionCenterPerson;
import cropcert.entities.model.response.CollectionCenterShow;
import cropcert.entities.service.CollectionCenterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Path("cc")
@Api("Collection  center")
public class CollectionCenterApi {

	private CollectionCenterService collectionCenterService;

	@Inject
	public CollectionCenterApi(CollectionCenterService collectionCenterService) {
		this.collectionCenterService = collectionCenterService;
	}

	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get cc by id", response = CollectionCenter.class)
	public Response find(@Context HttpServletRequest request, @PathParam("id") Long id) {
		CollectionCenter collectionCenter = collectionCenterService.findById(id);
		if (collectionCenter == null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(collectionCenter).build();
	}

	@Path("code/{code}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get cc by its code", response = CollectionCenter.class)
	public Response findByCode(@Context HttpServletRequest request, @PathParam("code") Long code) {
		CollectionCenter collectionCenter = collectionCenterService.findByPropertyWithCondition("code", code, "=");
		if (collectionCenter == null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.ok().entity(collectionCenter).build();
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all the collection centers", response = CollectionCenter.class, responseContainer = "List")
	public Response findAll(@Context HttpServletRequest request, @DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		List<CollectionCenter> collectionCenters;
		if (limit == -1 || offset == -1)
			collectionCenters = collectionCenterService.findAll();
		else
			collectionCenters = collectionCenterService.findAll(limit, offset);
		return Response.ok().entity(collectionCenters).build();
	}

	@Path("coCode/{coCode}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get list of cc by co-operative code", response = CollectionCenterShow.class, responseContainer = "List")
	public Response findAll(@Context HttpServletRequest request, @PathParam("coCode") Long coCode) {
		List<CollectionCenterShow> collectionCenterShows = collectionCenterService.findAllByCoCode(request, coCode);
		return Response.ok().entity(collectionCenterShows).build();
	}

	@Path("origin")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get map of origins by cc codes", response = Map.class, responseContainer = "Map")
	public Response getOriginNames(@Context HttpServletRequest request,
			@DefaultValue("") @QueryParam("ccCodes") String ccCodes) {
		Map<String, Object> originMap = collectionCenterService.getOriginNames(request, ccCodes);
		return Response.ok().entity(originMap).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Save the cc", response = CollectionCenter.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response save(@Context HttpServletRequest request, String jsonString) {
		CollectionCenter collectionCenter;
		try {
			collectionCenter = collectionCenterService.save(jsonString);
			return Response.status(Status.CREATED).entity(collectionCenter).build();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).entity("Creation failed").build();
	}
	
	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Delete the collection center by id", response = CollectionCenterPerson.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response delete(@Context HttpServletRequest request, @PathParam("id") Long id) {
		CollectionCenter cc = collectionCenterService.delete(id);
		return Response.status(Status.ACCEPTED).entity(cc).build();
	}

}
