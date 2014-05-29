package com.leeloo.viv.rest;

import java.util.*;

public class NotificationsRepository{

	private List<Notification> notifications = Arrays.asList(
    		new Notification("1", "gborobio@gmail.com", "Leeloo liked your work", "1", false),
            new Notification("2", "gborobio@gmail.com", "Erika commented your work", "3", false),
            new Notification("3", "gborobio@gmail.com", "Erika commented your work", "12", false),
            new Notification("4", "gborobio@gmail.com", "Leeloo liked your work", "7", true),
            new Notification("5", "gborobio@gmail.com", "Leeloo commented your work", "7", true),
            new Notification("6", "erika.perttu@gmail.com", "Leeloo liked your work", "1", false),
            new Notification("7", "leeloo.turku@gmail.com", "Gonzalo commented your work", "3", false),
            new Notification("8", "leeloo.turku@gmail.com", "Erika commented your work", "12", true),
            new Notification("9", "leeloo.turku@gmail.com", "Gonzalo liked your work", "7", true),
            new Notification("10", "erika.perttu@gmail.com", "Leeloo commented your work", "7", false)
        );


    public List<Notification> getUserNotifications(String user)
    {
    	List<Notification> userNotifications = new ArrayList<Notification>();
    	for(Notification notification : notifications)
		{
		    if (notification.User.toLowerCase().equals(user.toLowerCase())) {
		    	userNotifications.add(notification);
		    }
		}
        return userNotifications;
    }

    public List<Notification> getUserUnreadNotifications(String user){
        List<Notification> unread = new ArrayList<Notification>();

        List<Notification> userNotifications = getUserNotifications(user);

        for(Notification notification : userNotifications)
        {
            if (!notification.Read) {
                unread.add(notification);
            }
        }
        return unread;
    }
  

}