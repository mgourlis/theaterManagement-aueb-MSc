package gr.aueb.mscis.theater.resource;

import gr.aueb.mscis.theater.model.User;
import gr.aueb.mscis.theater.model.UserType;
import gr.aueb.mscis.theater.persistence.JPAUtil;
import gr.aueb.mscis.theater.service.*;

import javax.persistence.EntityManager;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.ResponseCache;
import java.net.URI;
import java.util.List;

@Path("user")
public class UserResource {

    @Context
    UriInfo uriInfo;
    
    @GET
    @Path("customers")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<UserInfo> getallCustomers() {

        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        UserService userService = new UserService(flashserv);
        List<User> users = userService.findAllCustomers();

        List<UserInfo> userInfo = UserInfo.wrap(users);

        em.close();

        return userInfo;
    }

    @GET
    @Path("employees")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public List<UserInfo> getallEmployees() {

        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        UserService userService = new UserService(flashserv);
        List<User> users = userService.findAllEmployees();

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
    @Path("customer")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createCustomer(UserInfo h){
        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        UserService userService = new UserService(flashserv);

        User user = h.getNewUser(em,UserType.Customer);

        user = userService.saveUser(user);

        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI userUri = ub.path("user"+"/"+Integer.toString(user.getId())).build();

        em.close();

        return Response.created(userUri).build();
    }

    @POST
    @Path("employee")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createEmployee(UserInfo h){
        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        UserService userService = new UserService(flashserv);

        User user = h.getNewUser(em,null);

        user = userService.saveUser(user);

        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI userUri = ub.path("user"+"/"+Integer.toString(user.getId())).build();

        em.close();

        return Response.created(userUri).build();
    }

    @PUT
    @Path("customer/{userId:[0-9]*}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updateCustomer(UserInfo h){
        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        UserService userService = new UserService(flashserv);

        User dbuser = userService.findUserById(h.getId());

        if(dbuser == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        User user = h.getExistingUser(em,UserType.Customer);

        if(dbuser.getId() != user.getId()){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        userService.saveUser(user);

        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI userUri = ub.path("user"+"/"+Integer.toString(user.getId())).build();

        em.close();

        return Response.ok(userUri).build();
    }

    @PUT
    @Path("employee/{userId:[0-9]*}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updateEmployee(UserInfo h){
        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        UserService userService = new UserService(flashserv);

        User dbuser = userService.findUserById(h.getId());

        if(dbuser == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        User user = h.getExistingUser(em,null);

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

    @POST
    @Path("login")
    public Response loginUser(@FormParam("email") String email,
                              @FormParam("password") String password){

        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();
        SerialNumberProvider serialprov = new SerialNumberProviderImpl();

        AuthenticateService auth = new AuthenticateService(flashserv,serialprov);

        if(auth.login(email,password) == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();

        return Response.ok().build();
    }

    @GET
    @Path("logout/{userId:[0-9]*}")
    public Response logoutUser(@PathParam("userId") int userId,
                               @QueryParam("token") String token){

        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();
        SerialNumberProvider serialprov = new SerialNumberProviderImpl();

        AuthenticateService auth = new AuthenticateService(flashserv,serialprov);

        if(auth.logout(userId,token) == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();

        return Response.ok().build();
    }

    @GET
    @Path("authenticated/{userId:[0-9]*}")
    public Response isAuthenicated(@PathParam("userId") int userId,
                               @QueryParam("token") String token){

        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();
        SerialNumberProvider serialprov = new SerialNumberProviderImpl();

        AuthenticateService auth = new AuthenticateService(flashserv,serialprov);

        if(auth.isAuthenticated(userId,token) == null)
            return Response.ok(false).build();

        return Response.ok(true).build();
    }
}