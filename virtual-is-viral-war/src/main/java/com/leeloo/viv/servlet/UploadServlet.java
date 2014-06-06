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
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.leeloo.viv.work.repository.IdGenerator;
import com.leeloo.viv.work.repository.WorkFactory;
import com.leeloo.viv.work.repository.WorkRepo;
import com.leeloo.viv.work.usecase.UseCases;

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
        List<BlobKey> blobKey = blobs.get("myFileName");

        if (blobKey == null) {
            res.sendRedirect("/error.html");
        } else {
        	String imageId = blobKey.get(0).getKeyString();
        	String title = req.getParameter("title");
        	String description = req.getParameter("description");
        	User currentUser = userService.getCurrentUser();
        	
        	new UseCases().createWork(currentUser.getNickname(), title, description, imageId);
        	
        	res.sendRedirect("/login");
        }
    }   
}