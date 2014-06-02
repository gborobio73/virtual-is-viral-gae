package com.leeloo.viv.rest;

import java.util.*;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Work{
  
	@Id public String id;
	public String user;
	public String name;
	public String description;
	public String imageId;
	public List<Comment> comments;

	public Work(String id, String user, String name, String description, String imageId, List<Comment> comments)
	{
	  this.id = id;
	  this.user = user;
	  this.name = name;
	  this.description = description;
	  this.imageId = imageId;
	  this.comments = comments;
	}

	public void addComment(String commentUser, String commentText) {
		String id = Integer.toString(comments.size() + 1);
		comments.add(new Comment(id, commentUser, commentText));		
	}

	public void deleteComment(String commentIdToDelete) {
		Iterator<Comment> it = comments.iterator();
		while (it.hasNext()) {
			Comment comment = it.next();
			if (comment.id.equals(commentIdToDelete)) {
				it.remove();
			}	
		}		
	}
}
