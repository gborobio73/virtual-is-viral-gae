package com.leeloo.viv.repository;

import static com.googlecode.objectify.ObjectifyService.register;
import static com.googlecode.objectify.ObjectifyService.ofy;

import java.util.List;

import com.leeloo.viv.rest.Comment;
import com.leeloo.viv.rest.Work;

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
		return ofy().load().type(Work.class).list();
	}

	public void save(Work work) {
    	ofy().save().entity(work).now();		
	}
	
}
