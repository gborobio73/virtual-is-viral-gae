<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
 
<%
	BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
%>

<form action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data">
	Title : <input type="text" name="title"/><br/>
	Description: <input type="text" name="description"/><br/>
	File :
	<input type="text" name="filename"/>
	<input type="file" name="myTweetPic"/>
	<input type="submit" value="Upload Picture"/>
</form>

