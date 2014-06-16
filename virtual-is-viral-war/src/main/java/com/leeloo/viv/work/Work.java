package com.leeloo.viv.work;

import java.util.*;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Work{
  
	@Id public String id;
	@Index public String user;
	public String name;
	public String description;
	public String imageId;
	public String imageUrl;
	public List<Comment> comments = new ArrayList<Comment>();
	@Index public Date created;
	
	public Work(){	}
	
	public Work(String id, String user, String name, String description, String imageId, String imageUrl)
	{
	  this.id = id;
	  this.user = user;
	  this.name = name;
	  this.description = description;
	  this.imageId = imageId;
	  this.imageUrl = imageUrl;	  
	  this.created = new Date();
	}

	public void addComment(String commentUser, String commentText) {
		String id = getNewId();
		comments.add(new Comment(id, commentUser, commentText));		
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
	
	public void edit(String title, String description) {
		this.name = title;
		this.description=description;
	}
	public void setDetails(String name, String description) {
		this.name= name;
		this.description=description;
	}
	
	private String getNewId() {
		if(comments.size()==0) return "1";
		else{
			String lastId = comments.get(comments.size()-1).id;
			return Integer.toString(Integer.parseInt(lastId) + 1);
		}
		
	}
}
