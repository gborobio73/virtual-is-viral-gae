<html>
	
	<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
	<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>
	<%@ page import ="com.google.api.client.http.GenericUrl" %>
	 
	<%BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService(); %>
	
	<!-- form action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data">
	  Title : <input type="text" name="title"/><br/>
	  Description: <input type="text" name="description"/><br/>
	  <input type="file" name="myFileName"/>
	  <input type="submit" value="Upload Picture"/>
	</form-->
	
	<head>	
		<meta name="viewport" content="width=device-width, initial-scale=1.0" />
    	<meta http-equiv="content-type" content="text/html; charset=UTF-8">  
		<link rel="stylesheet" href="//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css">
		<script src="//ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
		<style>
			@media only screen and (max-width : 480px) {
			    /* Smartphone view: 1 tile */
			    body {
			        font-size: 15px;
			    }
		    }
		</style>
	</head>
	<body>
		<div class="panel panel-default" style="font: 14px">
		  <div class="panel-heading">
		    <h3 class="panel-title">Upload picture</h3>
		  </div>
		  <div class="panel-body">
		    <form role="form" action="<%= blobstoreService.createUploadUrl("/upload") %>" method="post" enctype="multipart/form-data">
			  <div class="form-group">
			    <label for="fileInput">File input</label>
			    <input type="file" id="fileInput" name="theFile">
			  </div>
			  <div class="form-group">
			    <label for="titleInput" class="col-sm-2 control-label">Title</label>
			    <div class="col-sm-10">
			      <input type="text" class="form-control" id="titleInput" name="title" 
			      placeholder="Enter title">	
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="descInput" class="col-sm-2 control-label">Description</label>
			    <div class="col-sm-10">
			      <input type="text" class="form-control" id="descInput" name="description" 
			      placeholder="Enter description">
			    </div>
			  </div>  
			  <button type="submit" class="btn btn-primary" onclick="showProgress()">Upload Picture</button>
			  <img id="progressSpinner" src="../img/ajax-loader-big.gif"/>
			  <button type="button" class="btn btn-default" onclick="navigateBorad()">Cancel</button>			  
			</form>
		  </div>
		</div>
	</body>
	<script type="text/javascript">
		$( window ).load(function() {
			$("#progressSpinner").hide();	
		});
		function showProgress(){
			$("#progressSpinner").show();	
		}
		function navigateBorad(){
			location.href = "./#/Borad";
		}
	</script>
</html>
