package com.leeloo.viv.rest.jsonpojos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.leeloo.viv.work.Comment;
import com.leeloo.viv.work.Work;

public class UiWork  {
	public String id;
	public String user;
	public String name;
	public String description;
	public String imagelUrl;
	public List<Comment> comments = new ArrayList<Comment>();
	public Date created;
	
	private String displayInPixels = "=s1600";
	
	public UiWork (Work work)
	{
		this.id = work.id;
		this.user = work.user;
		this.name = work.name;
		this.description = work.description;
		this.imagelUrl = work.imageUrl + displayInPixels;
		this.comments = work.comments;
		this.created = work.created;
	}

}
