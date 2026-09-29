package cropcert.entities.api;

import java.io.IOException;
import java.util.List;
import java.util.Map;

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
import cropcert.entities.model.CollectionCenterEntity;
import cropcert.entities.model.response.CollectionCenterShow;
import cropcert.entities.service.CollectionCenterEntityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("cc")
@Tag(name = "Collection  center")
public class CollectionCenterEntitiesApi {

	private static final Logger logger = LoggerFactory.getLogger(CollectionCenterEntitiesApi.class);

	private CollectionCenterEntityService collectionCenterEntityService;

	@Inject
	public CollectionCenterEntitiesApi(CollectionCenterEntityService collectionCenterEntityService) {
		this.collectionCenterEntityService = collectionCenterEntityService;
	}

	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get cc by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Collection center found", content = @Content(schema = @Schema(implementation = CollectionCenterEntity.class))),
			@ApiResponse(responseCode = "204", description = "No content") })
	public Response find(@Context HttpServletRequest request, @PathParam("id") Long id) {
		CollectionCenterEntity collectionCenter = collectionCenterEntityService.findById(id);
		if (collectionCenter == null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(collectionCenter).build();
	}

	@Path("code/{code}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get cc by its code")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Collection center found", content = @Content(schema = @Schema(implementation = CollectionCenterEntity.class))),
			@ApiResponse(responseCode = "204", description = "No content") })
	public Response findByCode(@Context HttpServletRequest request, @PathParam("code") Long code) {
		CollectionCenterEntity collectionCenter = collectionCenterEntityService.findByPropertyWithCondition("code",
				code, "=");
		if (collectionCenter == null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.ok().entity(collectionCenter).build();
	}

	@Path("name/{name}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get cc by its name")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Collection center found", content = @Content(schema = @Schema(implementation = CollectionCenterEntity.class))),
			@ApiResponse(responseCode = "204", description = "No content") })
	public Response findByName(@Context HttpServletRequest request, @PathParam("name") String name) {
		CollectionCenterEntity collectionCenter = collectionCenterEntityService.findByPropertyWithCondition("name",
				name, "=");
		if (collectionCenter == null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.ok().entity(collectionCenter).build();
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get all the collection centers")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "All collection centers", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CollectionCenterEntity.class)))) })
	public Response findAll(@Context HttpServletRequest request, @DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		List<CollectionCenterEntity> collectionCenters;
		if (limit == -1 || offset == -1)
			collectionCenters = collectionCenterEntityService.findAll();
		else
			collectionCenters = collectionCenterEntityService.findAll(limit, offset);
		return Response.ok().entity(collectionCenters).build();
	}

	@Path("coCode/{coCode}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get list of cc by co-operative code")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Collection centers by cooperative code", content = @Content(array = @ArraySchema(schema = @Schema(implementation = CollectionCenterShow.class)))) })
	public Response findAll(@Context HttpServletRequest request, @PathParam("coCode") Long coCode) {
		List<CollectionCenterShow> collectionCenterShows = collectionCenterEntityService.findAllByCoCode(request,
				coCode);
		return Response.ok().entity(collectionCenterShows).build();
	}

	@Path("origin")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get map of origins by cc codes")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Origins map", content = @Content(schema = @Schema(implementation = Map.class))) })
	public Response getOriginNames(@Context HttpServletRequest request,
			@DefaultValue("") @QueryParam("ccCodes") String ccCodes) {
		Map<String, Object> originMap = collectionCenterEntityService.getOriginNames(ccCodes);
		return Response.ok().entity(originMap).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Save the cc")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "CC saved", content = @Content(schema = @Schema(implementation = CollectionCenterEntity.class))),
			@ApiResponse(responseCode = "204", description = "Creating cc failed") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response save(@Context HttpServletRequest request,
			@RequestBody(description = "CC json string", required = true) String jsonString) {
		CollectionCenterEntity collectionCenter;
		try {
			collectionCenter = collectionCenterEntityService.save(jsonString);
			return Response.status(Status.CREATED).entity(collectionCenter).build();
		} catch (IOException e) {
			logger.error(e.getMessage());
		}
		return Response.status(Status.NO_CONTENT).entity("Creating cc failed").build();
	}

	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@Operation(summary = "Delete the collection center by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "CC deleted", content = @Content(schema = @Schema(implementation = CollectionCenterEntity.class))) })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response delete(@Context HttpServletRequest request, @PathParam("id") Long id) {
		CollectionCenterEntity cc = collectionCenterEntityService.delete(id);
		return Response.status(Status.ACCEPTED).entity(cc).build();
	}

}
