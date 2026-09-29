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

import org.json.JSONException;

import cropcert.entities.filter.Permissions;
import cropcert.entities.filter.TokenAndUserAuthenticated;
import cropcert.entities.model.Admin;
import cropcert.entities.service.AdminService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@Path("admin")
@Tag(name = "Admin")
public class AdminApi {

	private AdminService adminService;

	@Inject
	public AdminApi(AdminService farmerService) {
		this.adminService = farmerService;
	}

	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get the admin by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Admin found", content = @Content(schema = @Schema(implementation = Admin.class))),
			@ApiResponse(responseCode = "204", description = "No content") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response find(@Context HttpServletRequest request, @PathParam("id") Long id) {
		Admin admin = adminService.findById(id);
		if (admin == null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(admin).build();
	}

	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Operation(summary = "Get all the admins")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "All admins", content = @Content(array = @ArraySchema(schema = @Schema(implementation = Admin.class)))) })
	@TokenAndUserAuthenticated(permissions = { Permissions.DEFAULT })
	public Response findAll(@Context HttpServletRequest request, @DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {

		List<Admin> admins;
		if (limit == -1 || offset == -1)
			admins = adminService.findAll();
		else
			admins = adminService.findAll(limit, offset);

		return Response.ok().entity(admins).build();
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Operation(summary = "Save the admin")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Admin saved", content = @Content(schema = @Schema(implementation = Admin.class))),
			@ApiResponse(responseCode = "204", description = "Creation failed") })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response save(@Context HttpServletRequest request,
			@RequestBody(description = "Admin json string", required = true) String jsonString) {
		Admin admin;
		try {
			admin = adminService.save(jsonString);
			return Response.status(Status.CREATED).entity(admin).build();
		} catch (IOException | JSONException e) {
			e.printStackTrace();
		}
		return Response.status(Status.NO_CONTENT).entity("Creation failed").build();
	}

	@Path("{id}")
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.TEXT_PLAIN)
	@Operation(summary = "Delete the admin by id")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "202", description = "Admin deleted", content = @Content(schema = @Schema(implementation = Admin.class))) })
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response delete(@Context HttpServletRequest request, @PathParam("id") Long id) {
		Admin admin = adminService.delete(id);
		return Response.status(Status.ACCEPTED).entity(admin).build();
	}

}
