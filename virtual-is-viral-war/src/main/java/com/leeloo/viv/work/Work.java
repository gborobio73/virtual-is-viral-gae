package com.leeloo.viv.work;

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
	public Date created;
	
	public Work(){
		if(comments == null)
		{
			comments = new ArrayList<Comment>();
		}
	}
	
	public Work(String id, String user, String name, String description, String imageId)
	{
	  this.id = id;
	  this.user = user;
	  this.name = name;
	  this.description = description;
	  this.imageId = imageId;
	  this.comments = new ArrayList<Comment>();
	  this.created = new Date();
	}

	public void addComment(String commentUser, String commentText) {
		String id = getNewId();
		comments.add(new Comment(id, commentUser, commentText));		
	}

	private String getNewId() {
		if(comments.size()==0) return "1";
		else{
			String lastId = comments.get(comments.size()-1).id;
			return Integer.toString(Integer.parseInt(lastId) + 1);
		}
		
	}

	public void deleteComment(String commentIdToDelete, String whosDeleting) {
		Iterator<Comment> it = comments.iterator();
		while (it.hasNext()) {
			Comment comment = it.next();
			if (comment.id.equals(commentIdToDelete)) {
				
				if(comment.user.equals(whosDeleting)){
					it.remove();
				}
				
			}	
		}		
	}
}
