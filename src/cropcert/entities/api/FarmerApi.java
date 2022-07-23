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
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.xml.bind.ValidationException;

import org.json.JSONException;

import javax.inject.Inject;

import org.glassfish.jersey.media.multipart.FormDataMultiPart;

import cropcert.entities.filter.Permissions;
import cropcert.entities.filter.TokenAndUserAuthenticated;
import cropcert.entities.model.Farmer;
import cropcert.entities.model.request.FarmerFileMetaData;
import cropcert.entities.service.FarmerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Path("farmer")
@Api("Farmer")
public class FarmerApi {

	private FarmerService farmerService;

	@Inject
	public FarmerApi(FarmerService farmerService) {
		this.farmerService = farmerService;
	}

	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get farmer by id", response = Farmer.class)
	public Response find(@Context HttpServletRequest request, @PathParam("id") Long id) {
		Farmer farmer = farmerService.findById(id);
		if (farmer == null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(farmer).build();
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get all the farmers", response = Farmer.class, responseContainer = "List")
	public Response findAll(@Context HttpServletRequest request, @DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		List<Farmer> farmers;
		if (limit == -1 || offset == -1)
			farmers = farmerService.findAll();
		else
			farmers = farmerService.findAll(limit, offset);
		return Response.ok().entity(farmers).build();
	}

	@Path("collection")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get list of farmer by collection center", response = Farmer.class, responseContainer = "List")
	public Response getFarmerForCollectionCenter(@Context HttpServletRequest request,
			@DefaultValue("-1") @QueryParam("ccCode") Long ccCode,
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		List<Farmer> farmers = farmerService.getByPropertyWithCondtion("ccCode", ccCode, "=", limit, offset,
				"firstName");
		return Response.ok().entity(farmers).build();
	}

	/**
	 * @param request    - HttpRequest
	 * @param ccCodes    - String with comma separated values
	 * @param farmerName - Name like farmerName
	 * @param limit      - limit
	 * @param offset     - offset
	 * @return
	 */
	@Path("ccCodes")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Get list of farmer by collection center", response = Farmer.class, responseContainer = "List")
	public Response getFarmerForMultipleCollectionCenter(@Context HttpServletRequest request,
			@DefaultValue("-1") @QueryParam("ccCodes") String ccCodes, @QueryParam("firstName") String firstName,
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		List<Farmer> farmers = farmerService.getFarmerForMultipleCollectionCenter(ccCodes, firstName, limit, offset);
		return Response.ok().entity(farmers).build();
	}

	@POST
	@Path("bulk")
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	@Produces(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Save the farmer in bulk", notes = "Input to the api is two files, one csv and other metadata.json. metadata.json can be form with the POJO of response", response = FarmerFileMetaData.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response bulkFarmerSave(@Context HttpServletRequest request, final FormDataMultiPart multiPart) {
		try {
			Map<String, Object> result = farmerService.bulkFarmerSave(request, multiPart);
			return Response.ok().entity(result).build();
		} catch (Exception e) {
			throw new WebApplicationException(
					Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e.getMessage()).build());
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ApiOperation(value = "Save the farmer", response = Farmer.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response save(@Context HttpServletRequest request, String jsonString) {
		Farmer farmer;
		try {
			farmer = farmerService.save(jsonString);
			return Response.status(Status.CREATED).entity(farmer).build();
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		} catch (ValidationException e) {
			return Response.status(Status.NO_CONTENT).entity(e.getMessage()).build();
		}
		return Response.status(Status.NO_CONTENT).entity("Creation failed").build();
	}

	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@ApiOperation(value = "Delete the farmer by id", response = Farmer.class)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response delete(@PathParam("id") Long id) {
		Farmer farmer = farmerService.delete(id);
		return Response.status(Status.ACCEPTED).entity(farmer).build();
	}
}
