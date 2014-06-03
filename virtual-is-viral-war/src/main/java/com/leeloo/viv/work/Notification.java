package com.leeloo.viv.work;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Notification{
	@Id
	public String id;
	public String toWhom;
	public String notification;
	public String workId;
	public Boolean read;

	public Notification(String id, String toWhom, String workId, String notification, Boolean read)
	{
	    this.id = id;
	    this.toWhom = toWhom;
	    this.workId = workId;
	    this.notification = notification;    
	    this.read = read;
	}
}
