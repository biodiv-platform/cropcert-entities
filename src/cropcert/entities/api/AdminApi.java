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
import cropcert.entities.model.Admin;
import cropcert.entities.service.AdminService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

@Path("admin")
@Api("Admin")
@ApiImplicitParams({
		@ApiImplicitParam(name = "Authorization", value = "Authorization token", required = true, dataType = "string", paramType = "header") })
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
	@ApiOperation(value = "Get the admin by id", response = Admin.class)
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
	@ApiOperation(value = "Get all the admins", response = Admin.class, responseContainer = "List")
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
	@ApiOperation(value = "Save the admin", response = Admin.class)
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response save(@Context HttpServletRequest request, String jsonString) {
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
	@ApiOperation(value = "Delete the admin by id", response = Admin.class)
	@TokenAndUserAuthenticated(permissions = { Permissions.ADMIN })
	public Response delete(@Context HttpServletRequest request, @PathParam("id") Long id) {
		Admin admin = adminService.delete(id);
		return Response.status(Status.ACCEPTED).entity(admin).build();
	}

}
