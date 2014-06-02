package com.leeloo.viv.repository;

import java.util.ArrayList;

import com.leeloo.viv.rest.Comment;
import com.leeloo.viv.rest.Work;

public class WorkFactory {
	
	private IIdGenerator idGenerator;


	public WorkFactory(IIdGenerator idGenerator){
		this.idGenerator = idGenerator;
	}
	

	public Work createWork(String user, String name, String description, String imageId)
	{
		String id = idGenerator.generateId();
		
		return new Work(id, user, name, description, imageId, new ArrayList<Comment>());
	}
}
