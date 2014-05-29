package com.leeloo.viv.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.NewCookie;
import java.util.List;

import com.google.gson.Gson;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.users.User;


@Path("works")
public class WorksApi {

    @GET
    @Path("/all")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getAllWorks() {
        UserService userService = UserServiceFactory.getUserService();
        if (!userService.isUserLoggedIn()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } 

        Gson gson = new Gson();
        List<Work> works = new WorkRepository().getAllWorks();
        //return gson.toJson(works);
        return Response.ok().entity(gson.toJson(works)).build();
    }

    @GET
    @Path("/user")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getUserWorks() {
        UserService userService = UserServiceFactory.getUserService();
        if (!userService.isUserLoggedIn()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } 

        User currentUser = userService.getCurrentUser();

        Gson gson = new Gson();
        List<Work> works = new WorkRepository().getUserWorks(currentUser.getNickname());
        return Response.ok().entity(gson.toJson(works)).build();
    }

    @GET
    @Path("/work/{id}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getWork(@PathParam("id") String id) {
        Gson gson = new Gson();
        Work work = new WorkRepository().getWork(id);
        return Response.ok().entity(gson.toJson(work)).build();
    }
}
