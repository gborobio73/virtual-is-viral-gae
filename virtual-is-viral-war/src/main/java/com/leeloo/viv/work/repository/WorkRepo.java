package com.leeloo.viv.work.repository;

import static com.googlecode.objectify.ObjectifyService.register;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.leeloo.viv.work.Comment;
import com.leeloo.viv.work.Work;

public class WorkRepo {
	
	public WorkRepo(){
		register(Work.class);
		register(Comment.class);
	}
	
	public Work get(String id) {
		Work w= ofy().load().type(Work.class).id(id).now();
		return w;
	}
	
	public List<Work> getAll()	{
		return ofy().load().type(Work.class).order("-created").list();
	}

	public List<Work> getFromUser(String user)	{
		return ofy().load().type(Work.class).filter("user", user).list();
	}
	
	public void save(Work work) {
    	ofy().save().entity(work).now();		
	}
	
}
