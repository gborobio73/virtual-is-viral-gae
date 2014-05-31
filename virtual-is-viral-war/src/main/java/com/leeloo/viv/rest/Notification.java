package com.leeloo.viv.rest;

public class Notification{
  
  public String id;
  public String user;
  public String notification;
  public String workId;
  public Boolean read;

  public Notification(String id, String user, String notification, String workId, Boolean read)
  {
    this.id = id;
    this.user = user;
    this.notification = notification;
    this.workId = workId;
    this.read = read;
  }
}
