package com.leeloo.viv.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import java.util.List;

import com.google.gson.Gson;

@Path("notifications")
public class NotificationsApi {

    @GET
    @Path("/{user}")
    @Produces({ MediaType.APPLICATION_JSON })
    public String getUserNotifications(@PathParam("user") String user) {
        Gson gson = new Gson();
        List<Notification> notifications = new NotificationsRepository().getUserNotifications(user);
        return gson.toJson(notifications);
    }

    @GET
    @Path("/unreadamount/{user}")
    @Produces({ MediaType.APPLICATION_JSON })
    public String getUnreadNotificationsAmount(@PathParam("user") String user) {
        Gson gson = new Gson();
        List<Notification> notifications = new NotificationsRepository().getUserUnreadNotifications(user);
        return gson.toJson(notifications.size());
    }
    
}
