package com.leeloo.viv.work.repository;

import com.leeloo.viv.work.Notification;
import com.leeloo.viv.work.Work;

public class NotificationFactory {
	
	private IIdGenerator idGenerator;
	public NotificationFactory(IIdGenerator idGenerator){
		this.idGenerator = idGenerator;
	}
	
	public Notification createNotification(String user, Work work) {
		String notification = user + " has commented on your work " + work.name;
		String id = idGenerator.generateId();
		return new Notification(id, work.user, work.id,  notification); 
	}	
}
