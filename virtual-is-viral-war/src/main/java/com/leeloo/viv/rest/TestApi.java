package com.leeloo.viv.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import com.google.gson.Gson;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;

@Path("test")
public class TestApi {

    @GET
    @Path("/user")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getAllWorks() {

        UserService userService = UserServiceFactory.getUserService();
        User currentUser = userService.getCurrentUser();

        if (currentUser != null) {
            Gson gson = new Gson();
        //return gson.toJson(works);
            return Response.ok("All works. You're " + currentUser.getNickname() 
                + "Logout here: " + userService.createLogoutURL("")).build();
        } else {
            return Response.ok("Who are you? ").build();
        }
        
    }
}
