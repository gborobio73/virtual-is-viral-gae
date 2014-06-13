package com.leeloo.viv.work;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Notification{
	@Id public String id;
	@Index public String toWhom;
	public String workId;
	public String notification;
	@Index public Boolean read;
	@Index public Date created;
	
	public Notification(){}
	
	public Notification(String id, String toWhom, String workId, String notification)
	{
	    this.id = id;
	    this.toWhom = toWhom;
	    this.workId = workId;
	    this.notification = notification;    
	    this.read = false;
	    this.created = new Date();
	}

	public void markAsRead() {
		this.read = true;
	}

	public boolean isRead() {
		return this.read;
	}
}
