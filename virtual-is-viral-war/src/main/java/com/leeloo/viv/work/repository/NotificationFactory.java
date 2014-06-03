package com.leeloo.viv.work.repository;

import com.leeloo.viv.work.Notification;

public class NotificationFactory {
	
	private IIdGenerator idGenerator;
	public NotificationFactory(IIdGenerator idGenerator){
		this.idGenerator = idGenerator;
	}
	
	public Notification createNotification(String toWhom, String workId, String notification){	
		String id = idGenerator.generateId();
		return new Notification(id, toWhom, workId,  notification, false); 
	}	
}
