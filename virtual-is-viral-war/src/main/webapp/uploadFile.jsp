<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
<%@ page import ="com.google.api.client.http.GenericUrl" %>
 
<%
  BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();  
%>

<form action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data">
  Title : <input type="text" name="title"/><br/>
  Description: <input type="text" name="description"/><br/>
  <input type="file" name="myFileName"/>
  <input type="submit" value="Upload Picture"/>
</form>
