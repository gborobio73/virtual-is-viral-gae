package com.leeloo.viv.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

import com.google.gson.Gson;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.users.User;


@Path("notifications")
public class NotificationsApi {

    @GET
    @Path("/")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getUserNotifications(@PathParam("user") String user) {
        UserService userService = UserServiceFactory.getUserService();
        if (!userService.isUserLoggedIn()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } 

        User currentUser = userService.getCurrentUser();
        Gson gson = new Gson();
        List<Notification> notifications = new NotificationsRepository().getUserNotifications(currentUser.getNickname());
        return Response.ok().entity(gson.toJson(notifications)).build();
    }

    @GET
    @Path("/unreadamount/")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getUnreadNotificationsAmount(@PathParam("user") String user) {
        UserService userService = UserServiceFactory.getUserService();
        if (!userService.isUserLoggedIn()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } 

        User currentUser = userService.getCurrentUser();
        Gson gson = new Gson();
        List<Notification> notifications = new NotificationsRepository().getUserUnreadNotifications(currentUser.getNickname());
        return Response.ok().entity(gson.toJson(notifications.size())).build();
    }
    
}
