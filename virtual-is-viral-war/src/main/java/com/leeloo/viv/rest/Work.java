package com.leeloo.viv.rest;

public class Work{
  
  public String Id;
	public String User;
  public String Name;
  public String Description;
  public String ImageId;

  public Work(String id, String user, String name, String description, String imageId)
  {
    Id = id;
  	User = user;
  	Name = name;
  	Description = description;
    ImageId = imageId;
  }
}
