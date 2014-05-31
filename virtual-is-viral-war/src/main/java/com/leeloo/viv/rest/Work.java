package com.leeloo.viv.rest;

import java.util.*;

public class Work{
  
  public String Id;
	public String User;
  public String Name;
  public String Description;
  public String ImageId;
  public List<Comment> Comments;

  public Work(String id, String user, String name, String description, String imageId, List<Comment> comments)
  {
    Id = id;
  	User = user;
  	Name = name;
  	Description = description;
    ImageId = imageId;
    Comments = comments;
  }
}
