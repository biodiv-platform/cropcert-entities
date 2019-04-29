package cropcert.entity;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

@Path("logout")
public class Logout {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String logout(@Context HttpServletRequest request) {
        request.getSession().invalidate();
        return "logged_out";
    }
}
