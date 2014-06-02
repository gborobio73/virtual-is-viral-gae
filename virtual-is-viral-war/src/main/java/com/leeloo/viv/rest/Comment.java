package com.leeloo.viv.rest;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;

@Entity
public class Comment{
  
  @Id public String id;
  public String user;
  public String comment; 
 
  public Comment(String id, String user, String comment)
  {
    this.id = id;
    this.user = user;
    this.comment = comment;
  }
}
