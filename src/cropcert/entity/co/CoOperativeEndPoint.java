package cropcert.entity.co;

import java.io.IOException;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.hibernate.exception.ConstraintViolationException;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.google.inject.Inject;


@Path("co")
public class CoOperativeEndPoint{

	private CoOperativeService coOperativeService;
	
	@Inject
	public CoOperativeEndPoint(CoOperativeService coOperativeService) {
		this.coOperativeService = coOperativeService;
	}
	
	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@PathParam("id") Long id) {
		CoOperative coOperative = coOperativeService.findById(id);
		if(coOperative==null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(coOperative).build();
	}
	
	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CoOperative> findAll() {
		return coOperativeService.findAll();
	}
	
	@Path("coCode")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public CoOperative getByCoCode(@DefaultValue("-1") @QueryParam("coCode") String coCodeString) {
		Integer coCode = Integer.parseInt(coCodeString);
		return coOperativeService.findByPropertyWithCondtion("coCode", coCode, "=");
	}
	
	@Path("few")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CoOperative> findAll(@QueryParam("limit") int limit, @QueryParam("offset") int offset) {
		return coOperativeService.findAll(limit, offset);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(String  jsonString) {
		try {
			CoOperative coOperative = coOperativeService.save(jsonString);
			return Response.status(Status.CREATED).entity(coOperative).build();
		} catch(ConstraintViolationException e) {
			return Response.status(Status.CONFLICT).tag("Dublicate key").build();
		}
		catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
