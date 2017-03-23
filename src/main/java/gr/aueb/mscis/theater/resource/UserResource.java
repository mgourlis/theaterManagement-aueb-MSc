package gr.aueb.mscis.theater.resource;

import gr.aueb.mscis.theater.model.User;
import gr.aueb.mscis.theater.model.UserType;
import gr.aueb.mscis.theater.persistence.JPAUtil;
import gr.aueb.mscis.theater.service.*;

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
    @Path("{email}")
    @Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public UserInfo getUser(@PathParam("email") String email) {

        if(!emailValidation(email)){
            return null;
        }

        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        UserService userService = new UserService(flashserv);
        User user = userService.findUserByEmail(email);

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
        URI userUri = ub.path("user"+"/"+user.getEmail()).build();

        em.close();

        return Response.created(userUri).build();
    }

    @POST
    @Path("employee")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response createEmployee(@QueryParam("type") UserType type,
                                   UserInfo h){
        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        UserService userService = new UserService(flashserv);

        User user = h.getNewUser(em,type);

        user = userService.saveUser(user);

        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI userUri = ub.path("user"+"/"+user.getEmail()).build();

        em.close();

        return Response.created(userUri).build();
    }

    @PUT
    @Path("{email}")
    @Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON})
    public Response updateUser(@PathParam("email") String email,
                                   UserInfo h) {

        if(!emailValidation(email)){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        UserService userService = new UserService(flashserv);

        User dbuser = userService.findUserByEmail(email);

        if(dbuser == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        User user = h.getExistingUser(em);

        if(dbuser.getId() != user.getId()){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        User uptuser = userService.saveUser(user);

        UriBuilder ub = uriInfo.getAbsolutePathBuilder();
        URI userUri = ub.path("user"+"/"+uptuser.getEmail()).build();

        em.close();

        return Response.ok(userUri).build();
    }

    @DELETE
    @Path("{email}")
    public Response deleteUser(@PathParam("email") String email){
        if(!emailValidation(email)){
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();

        UserService userService = new UserService(flashserv);

        User dbuser = userService.findUserByEmail(email);

        if(dbuser == null){
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if(!userService.deleteUser(dbuser.getId()))
            return Response.status(Response.Status.BAD_REQUEST).build();

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
    @Path("logout/{email}")
    public Response logoutUser(@PathParam("email") String email,
                               @QueryParam("token") String token){

        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();
        SerialNumberProvider serialprov = new SerialNumberProviderImpl();

        AuthenticateService auth = new AuthenticateService(flashserv,serialprov);
        UserService userService = new UserService(flashserv);

        User dbuser = userService.findUserByEmail(email);
        if(dbuser == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();

        if(auth.logout(dbuser.getId(),token) == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();

        return Response.ok().build();
    }

    @GET
    @Path("authenticated/{email}")
    public Boolean isAuthenicated(@PathParam("email") String email,
                               @QueryParam("token") String token){

        EntityManager em = JPAUtil.getCurrentEntityManager();

        FlashMessageService flashserv = new FlashMessageServiceImpl();
        SerialNumberProvider serialprov = new SerialNumberProviderImpl();

        AuthenticateService auth = new AuthenticateService(flashserv,serialprov);
        UserService userService = new UserService(flashserv);

        User dbuser = userService.findUserByEmail(email);
        if(dbuser == null)
            return false;

        if(auth.isAuthenticated(dbuser.getId(),token) == null)
            return false;

        return true;
    }

    private Boolean emailValidation(String email){
        String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
        java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
        java.util.regex.Matcher m = p.matcher(email);
        return m.matches();
    }
}