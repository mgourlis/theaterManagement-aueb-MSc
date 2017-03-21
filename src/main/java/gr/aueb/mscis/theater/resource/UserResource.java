package gr.aueb.mscis.theater.resource;

import java.net.URI;

import javax.persistence.EntityManager;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import javax.ws.rs.core.UriInfo;

import gr.aueb.mscis.theater.model.User;
import gr.aueb.mscis.theater.persistence.JPAUtil;

@Path("user")
public class UserResource {
	
    @Context
    UriInfo uriInfo;

/*    @POST
    @Consumes("application/json")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createUser(NewUserInfo newUserInfo) {

        EntityManager em = JPAUtil.getCurrentEntityManager();

		User useer = newUserInfo.createNewUser(em);
		
//		UserInfo userInfo = new userInfo();
//		userInfo =
//      UserInfo.wrap(User);

		UriBuilder ub = uriInfo.getAbsolutePathBuilder();
		URI newUserUri = ub.path(Integer.toString(User.getId())).build();

        em.close();
		
		return Response.created(newUserUri).build();
    }
*/
}