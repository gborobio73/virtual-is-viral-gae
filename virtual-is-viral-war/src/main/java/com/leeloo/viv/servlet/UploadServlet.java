package com.leeloo.viv.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.leeloo.viv.repository.IdGenerator;
import com.leeloo.viv.repository.WorkFactory;
import com.leeloo.viv.repository.WorkRepo;
import com.leeloo.viv.rest.Comment;
import com.leeloo.viv.rest.Work;

public class UploadServlet extends HttpServlet {
	
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    private WorkFactory workFactory = new WorkFactory(new IdGenerator());
    private WorkRepo workRepository = new WorkRepo();
    
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

    	UserService userService = UserServiceFactory.getUserService();
        
        if (!userService.isUserLoggedIn()) {
            res.sendRedirect("/login.html");
        }
    	
        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);		
        List<BlobKey> blobKey = blobs.get("myTweetPic");

        if (blobKey == null) {
            res.sendRedirect("/Error.html");
        } else {
        	String imageId = blobKey.get(0).getKeyString();
        	String title = req.getParameter("title");
        	String description = req.getParameter("description");
        	User currentUser = userService.getCurrentUser();
        	
        	Work work = workFactory.createWork(currentUser.getNickname(), title, description, imageId);
        	workRepository.save(work);
        	
        	res.sendRedirect("/#/Board");
        }
    }
    
    @Entity
    class NewWork {
        @Id String id; // Can be Long, long, or String
        String user;
        String name;
        String description;
        String imageId;
        List<Comment> comments;
    }
}