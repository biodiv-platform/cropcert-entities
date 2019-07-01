package cropcert.entity.cc;

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

@Path("cc")
public class CollectionCenterEndPoint{

	private CollectionCenterService collectionCenterService;
	
	@Inject
	public CollectionCenterEndPoint(CollectionCenterService collectionCenterService) {
		this.collectionCenterService = collectionCenterService;
	}
	
	@Path("{id}")
	@GET
	@Consumes(MediaType.TEXT_PLAIN)
	@Produces(MediaType.APPLICATION_JSON)
	public Response find(@PathParam("id") Long id) {
		CollectionCenter collectionCenter = collectionCenterService.findById(id);
		if(collectionCenter==null)
			return Response.status(Status.NO_CONTENT).build();
		return Response.status(Status.CREATED).entity(collectionCenter).build();
	}
	
	@Path("all")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CollectionCenter> findAll(
			@DefaultValue("-1") @QueryParam("limit") Integer limit,
			@DefaultValue("-1") @QueryParam("offset") Integer offset) {
		if(limit==-1 || offset ==-1)
			return collectionCenterService.findAll();
		else
			return collectionCenterService.findAll(limit, offset);
	}

	@Path("coOperativeId/{coOperativeId}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public List<CollectionCenter> findAll(@PathParam("coOperativeId") Long coOperativeId) {
		return collectionCenterService.getByPropertyWithCondtion("coOperativeId", coOperativeId, "=", -1, -1);
	}
	
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response save(String  jsonString) {
		try {
			CollectionCenter collectionCenter = collectionCenterService.save(jsonString);
			return Response.status(Status.CREATED).entity(collectionCenter).build();
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
