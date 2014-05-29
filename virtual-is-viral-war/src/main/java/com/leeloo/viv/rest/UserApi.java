package com.leeloo.viv.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.NewCookie;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import java.io.IOException;

import com.google.gson.Gson;

@Path("user")
public class UserApi {

    private String imageUrl ="https://lh6.googleusercontent.com/-Wocm6KDwTU4/AAAAAAAAAAI/AAAAAAAADCU/d6Ylt7a8kok/photo.jpg?sz=50";

    @Context
    HttpServletResponse response;
    @Context
    HttpServletRequest request;

    @GET
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getCurrentUser() throws IOException {
        
        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        if (currentUser != null) {
            Gson gson = new Gson();
            VivUser vivUser = new VivUser(currentUser.getUserId(),currentUser.getNickname(), imageUrl );

            return Response.ok().entity(gson.toJson(vivUser)).build();
            //return Response.ok("User: " +currentUser.getNickname()).entity(gson.toJson(currentUser.getNickname())).build();
            //return gson.toJson(works);
            //return Response.ok("All works. You're " + currentUser.getNickname() 
            //    + "Logout here: " + userService.createLogoutURL("")).build();
        } else {

            //response.sendRedirect(userService.createLogoutURL(""));
            //response.sendRedirect();
            return Response.ok("Not signed in: " + userService.createLoginURL(request.getRequestURI())).build();
        }
    }   
}
