package com.leeloo.viv.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.images.ImagesService;
import com.google.appengine.api.images.ImagesServiceFactory;
import com.google.appengine.api.images.ServingUrlOptions;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.leeloo.viv.work.usecase.UseCases;

public class UploadServlet extends HttpServlet {
	
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

    	UserService userService = UserServiceFactory.getUserService();
        
        if (!userService.isUserLoggedIn()) {
            res.sendRedirect("/login.html");
        }
    	
        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);		
        List<BlobKey> blobKey = blobs.get("theFile");

        if (blobKey == null) {
            res.sendRedirect("/error.html");
        } else {
        	
        	BlobKey currentUploadedBlob = blobKey.get(0);
        	
        	ImagesService imagesService = ImagesServiceFactory.getImagesService();
        	ServingUrlOptions options = ServingUrlOptions.Builder.withBlobKey(currentUploadedBlob);
        	String imageUrl = imagesService.getServingUrl(options);        	
        	String imageId = currentUploadedBlob.getKeyString();
        	
        	String title = req.getParameter("title");
        	if(title.length() == 0) title = "Unknown";
        	
        	String description = req.getParameter("description");
        	if(description.length() == 0) description = "Unknown";
        	
        	User currentUser = userService.getCurrentUser();        	
        	new UseCases().createWork(currentUser.getNickname(), title, description, imageId, imageUrl);
        	
        	res.sendRedirect("/login");
        }
    }   
}