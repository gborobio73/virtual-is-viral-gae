package com.leeloo.viv.work.repository;

import java.util.*;

import com.leeloo.viv.work.Notification;

public class NotificationsRepository{

//	private List<Notification> notifications = Arrays.asList(
//    		new Notification("1", "gborobio", "Leeloo liked your work", "1", false),
//            new Notification("2", "gborobio", "Erika commented your work", "3", false),
//            new Notification("3", "gborobio", "Erika commented your work", "12", false),
//            new Notification("4", "gborobio", "Leeloo liked your work", "7", true),
//            new Notification("5", "gborobio", "Leeloo commented your work", "7", true),
//            new Notification("6", "erika.perttuli", "Leeloo liked your work", "1", false),
//            new Notification("7", "leeloo.turku", "Gonzalo commented your work", "3", false),
//            new Notification("8", "leeloo.turku", "Erika commented your work", "12", true),
//            new Notification("9", "leeloo.turku", "Gonzalo liked your work", "7", true),
//            new Notification("10", "erika.perttuli", "Leeloo commented your work", "7", false)
//        );
//
//
//    public List<Notification> getUserNotifications(String user)
//    {
//    	List<Notification> userNotifications = new ArrayList<Notification>();
//    	for(Notification notification : notifications)
//		{
//		    if (notification.user.toLowerCase().equals(user.toLowerCase())) {
//		    	userNotifications.add(notification);
//		    }
//		}
//        return userNotifications;
//    }
//
//    public List<Notification> getUserUnreadNotifications(String user){
//        List<Notification> unread = new ArrayList<Notification>();
//
//        List<Notification> userNotifications = getUserNotifications(user);
//
//        for(Notification notification : userNotifications)
//        {
//            if (!notification.read) {
//                unread.add(notification);
//            }
//        }
//        return unread;
//    }
//  

}