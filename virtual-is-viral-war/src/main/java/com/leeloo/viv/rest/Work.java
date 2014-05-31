package com.leeloo.viv.rest;

import java.util.*;

public class Work{
  
  public String id;
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
}
