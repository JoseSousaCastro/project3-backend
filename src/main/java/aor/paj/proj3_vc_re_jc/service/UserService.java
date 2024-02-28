package aor.paj.proj3_vc_re_jc.service;

import aor.paj.proj3_vc_re_jc.bean.TaskBean;
import aor.paj.proj3_vc_re_jc.bean.UserBean;
import aor.paj.proj3_vc_re_jc.dto.*;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import javax.management.relation.Role;
import java.util.ArrayList;

@Path("/users")
public class UserService {

    @Inject
    UserBean userBean;
    @Inject
    TaskBean taskBean;

    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(@HeaderParam("username") String username, @HeaderParam("password") String password) {
        String token = userBean.login(username,password);
        if (token != null) {
            return Response.status(200).entity(token).build();
        }
        return Response.status(403).entity("Wrong Username or Password!").build();
    }

    @POST
    @Path("/register")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response registerUser(UserDto user) {
        if (userBean.register(user)) {
            return Response.status(200).entity("The new user is registered").build();
        }
        return Response.status(401).entity("There is a user with the same username").build();
    }

    @GET
    @Path("/logout")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response logout(@HeaderParam("token") String token) {
        if (userBean.logout(token)) {
            return Response.status(200).entity("Logout Successful!").build();
        } else {
            return Response.status(401).entity("Invalid Token!").build();
        }
    }

    @PUT
    @Path("/editProfile")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editProfile(EditDto user, @HeaderParam("token") String token) {
        if (userBean.tokenExist(token)) {
            userBean.updateProfile(user, token);
            return Response.status(200).entity("Profile updated!").build();
        } else {
            userBean.logout(token);
            return Response.status(401).entity("Invalid Token!").build();
        }
    }

    @GET
    @Path("/checkProfile")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkProfile(@HeaderParam("username") String username, @HeaderParam("token") String token) {
        if (userBean.tokenExist(token)) {
            CheckProfileDto u = userBean.checkProfile(username, token);
            return Response.status(200).entity(u).build();
        } else {
            userBean.logout(token);
            return Response.status(401).entity("Invalid Token!").build();
        }
    }

    @GET
    @Path("/roleByToken")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response roleByToken (@HeaderParam("token") String token) {
        if (userBean.tokenExist(token)) {
            RoleDto rdto = userBean.getRole(token);
            return Response.status(200).entity(rdto).build();
        } else {
            userBean.logout(token);
            return Response.status(401).entity("Invalid Token!").build();
        }
    }


    @PUT
    @Path("/editOtherProfile")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editOtherProfile(@HeaderParam("token") String token, EditDto user) {
        if (userBean.tokenExist(token)) {
            userBean.updateProfile(user, token);
            return Response.status(200).entity("Profile updated!").build();
        } else {
            userBean.logout(token);
            return Response.status(401).entity("Invalid Token!").build();
        }
    }

    @POST
    @Path("/createUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(@HeaderParam("token") String token, UserDto user) {
        if (userBean.tokenExist(token)) {
            userBean.createUser(token, user);
            return Response.status(200).entity("Profile updated!").build();
        } else {
            userBean.logout(token);
            return Response.status(401).entity("Invalid Token!").build();
        }
    }

    @GET
    @Path("/checkUsers")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkUsers(@HeaderParam("token") String token) {
        if (userBean.tokenExist(token)) {
            ArrayList<CheckProfileDto> dtos = userBean.checkAll(token);
            return Response.status(200).entity(dtos).build();
        } else {
            userBean.logout(token);
            return Response.status(401).entity("Invalid Token!").build();
        }
    }

    @PUT
    @Path("/deleteUser")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteUser(@HeaderParam("username") String username, @HeaderParam("token") String token) {
        if (userBean.tokenExist(token)) {
            userBean.deleteUser(username, token);
            return Response.status(200).entity("Profile 'deleted'").build();
        } else {
            userBean.logout(token);
            return Response.status(401).entity("Invalid Token!").build();
        }
    }

    @PUT
    @Path("/updateRole")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateRole(RoleDto user, @HeaderParam("token") String token) {
        if (userBean.tokenExist(token)) {
            userBean.updateRole(user, token);
            return Response.status(200).entity("Role updated").build();
        } else {
            userBean.logout(token);
            return Response.status(401).entity("Invalid Token!").build();
        }
    }
}