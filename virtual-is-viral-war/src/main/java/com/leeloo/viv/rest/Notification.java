package com.leeloo.viv.rest;

public class Notification{
  
  public String Id;
	public String User;
  public String Notification;
  public String WorkId;
  public Boolean Read;

  public Notification(String id, String user, String notification, String workId, Boolean read)
  {
    Id = id;
    User = user;
    Notification = notification;
    WorkId = workId;
    Read = read;
  }
}
