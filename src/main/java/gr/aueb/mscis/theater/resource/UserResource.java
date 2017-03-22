package gr.aueb.mscis.theater.resource;

import gr.aueb.mscis.theater.model.User;
import gr.aueb.mscis.theater.persistence.JPAUtil;
import gr.aueb.mscis.theater.service.FlashMessageService;
import gr.aueb.mscis.theater.service.FlashMessageServiceImpl;
import gr.aueb.mscis.theater.service.UserService;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;

@Path("user")
public class UserResource {

    @Context
    UriInfo uriInfo;
    
    @GET
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<UserInfo> getallUsers() {

        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        UserService userService = new UserService(flashserv);
        List<User> users = userService.findAllUsers();
        
        List<UserInfo> userInfo = UserInfo.wrap(users);

        em.close();

        return userInfo;
    }

    @GET
    @Path("{userId:[0-9]*}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public UserInfo getUser(@PathParam("userId") int userId) {

        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        UserService userService = new UserService(flashserv);
        User user = userService.findUserById(userId);

        UserInfo userInfo = null;

        if(user != null){
            userInfo = new UserInfo(user);
        }

        em.close();

        return userInfo;
    }

    @POST
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createUser(UserInfo h){
        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        UserService userService = new UserService(flashserv);

        User user = h.getUser(em);

        user = userService.saveUser(user);

        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI userUri = ub.path("user"+"/"+Integer.toString(user.getId())).build();

        em.close();

        return Response.created(userUri).build();
    }

    @PUT
    @Path("{userId:[0-9]*}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updateUser(UserInfo h){
        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        UserService userService = new UserService(flashserv);

        User dbuser = userService.findUserById(h.getId());

        //Validation - Todo other validations for Seat, Sector and User
        if(dbuser == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        User user = h.getUser(em);

        if(dbuser.getId() != user.getId()){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        userService.saveUser(user);

        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI userUri = ub.path("user"+"/"+Integer.toString(user.getId())).build();

        em.close();

        return Response.ok(userUri).build();
    }

    @DELETE
    @Path("{userId:[0-9]*}")
    public Response deleteUser(@PathParam("userId") int userId){
        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        UserService userService = new UserService(flashserv);

        User dbuser = userService.findUserById(userId);

        if(dbuser == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        userService.deleteUser(userId);

        em.close();

        return Response.ok().build();
    }
}