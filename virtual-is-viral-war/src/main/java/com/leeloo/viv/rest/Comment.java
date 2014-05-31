package com.leeloo.viv.rest;

public class Comment{
  
  public String id;
  public String user;
  public String comment; 
 
  public Comment(String id, String user, String comment)
  {
    this.id = id;
    this.user = user;
    this.comment = comment;
  }
}
