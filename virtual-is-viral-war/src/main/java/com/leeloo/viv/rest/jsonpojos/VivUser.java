package com.leeloo.viv.rest.jsonpojos;

public class VivUser{
  
  public String id;
  public String name;
  public String imageUrl;
  
  public VivUser(String id, String name, String imageUrl)
  {
    this.id = id;
    this.name = name;
    this.imageUrl = imageUrl;
  }
}
