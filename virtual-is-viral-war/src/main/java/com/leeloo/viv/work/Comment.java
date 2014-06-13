package com.leeloo.viv.work;

import java.util.Date;

import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

@Entity
public class Comment{
  
  @Id public String id;
  public String user;
  public String comment;
  @Index public Date date; 
 
  public Comment(){}
  
  public Comment(String id, String user, String comment)
  {
    this.id = id;
    this.user = user;
    this.comment = comment;
    this.date = new Date();
  }
}
