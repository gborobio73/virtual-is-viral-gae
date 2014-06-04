package com.leeloo.viv.work.repository;

import static com.googlecode.objectify.ObjectifyService.register;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.leeloo.viv.work.Notification;

public class NotificationRepo {
	
	public NotificationRepo(){
		register(Notification.class);		
	}
	
	public List<Notification> getAll()	{
		return ofy().load().type(Notification.class).list();
	}

	public void save(Notification noti) {
    	ofy().save().entity(noti).now();		
	}

	public List<Notification> getUserNotifications(String toWhom) {
		return ofy().load().type(Notification.class).filter("toWhom", toWhom).list();	
		}

	public List<Notification> getUserUnreadNotifications(String toWhom) {
		return ofy().load().type(Notification.class).filter("toWhom", toWhom).filter("read", false).list();
	}
	
}