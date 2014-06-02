package com.leeloo.viv.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.googlecode.objectify.ObjectifyService;
import com.leeloo.viv.rest.Comment;
import com.leeloo.viv.rest.NullWork;
import com.leeloo.viv.rest.Work;

public class WorkRepo {

	public WorkRepo(){
		ObjectifyService.register(Work.class);
		ObjectifyService.register(Comment.class);
	}
	
	public Work get(String id) {
		Work w= ObjectifyService.ofy().load().type(Work.class).id(id).now();
		return w;
	}

	public void save(Work work) {
    	ObjectifyService.ofy().save().entity(work).now();		
	}
}
