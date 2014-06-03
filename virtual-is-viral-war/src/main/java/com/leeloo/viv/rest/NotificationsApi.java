package com.leeloo.viv.rest;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.gson.Gson;
import com.leeloo.viv.work.Notification;
import com.leeloo.viv.work.repository.NotificationRepo;
import com.leeloo.viv.work.repository.NotificationsRepository;


@Path("notifications")
public class NotificationsApi {

    @GET
    @Path("/all")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getUserNotifications() {
        UserService userService = UserServiceFactory.getUserService();
        if (!userService.isUserLoggedIn()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } 

        User currentUser = userService.getCurrentUser();
        Gson gson = new Gson();
        List<Notification> notifications = new NotificationRepo().getUserNotifications(currentUser.getNickname());
        return Response.ok().entity(gson.toJson(notifications)).build();
    }

    @GET
    @Path("/unreadamount/")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getUnreadNotificationsAmount() {
        UserService userService = UserServiceFactory.getUserService();
        if (!userService.isUserLoggedIn()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } 

        User currentUser = userService.getCurrentUser();
        Gson gson = new Gson();
        List<Notification> notifications = new NotificationRepo().getUserUnreadNotifications(currentUser.getNickname());
        return Response.ok().entity(gson.toJson(notifications.size())).build();
    }
    
}
