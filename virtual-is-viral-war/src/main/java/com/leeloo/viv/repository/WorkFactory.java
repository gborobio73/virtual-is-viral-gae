package com.leeloo.viv.repository;

import java.util.ArrayList;
import java.util.UUID;

import com.leeloo.viv.rest.Comment;
import com.leeloo.viv.rest.Work;

public class WorkFactory {

	public Work createWork(String user, String name, String description, String imageId)
	{
		String id = UUID.randomUUID().toString();
		
		return new Work(id, user, name, description, imageId, new ArrayList<Comment>());
	}
}
