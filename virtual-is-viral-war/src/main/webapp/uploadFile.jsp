<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page import ="com.google.api.client.http.GenericUrl" %>
 
<%
  BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
  GenericUrl url = new GenericUrl(request.getRequestURL().toString());
  System.out.println(request.getRequestURL().toString());
  
  url.setRawPath("/upload");  
  String uploadUrl = url.build();
  System.out.println(uploadUrl);
%>

<form action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data">
  Title : <input type="text" name="title"/><br/>
  Description: <input type="text" name="description"/><br/>
  File :
  <input type="text" name="filename"/>
  <input type="file" name="myFileName"/>
  <input type="submit" value="Upload Picture"/>
</form>
