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
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.annotation.Entity;
import com.googlecode.objectify.annotation.Id;
import com.leeloo.viv.rest.Comment;

public class UploadServlet extends HttpServlet {
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();

    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res)
        throws ServletException, IOException {

        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);
		
        List<BlobKey> blobKey = blobs.get("myTweetPic");

        if (blobKey == null) {
            res.sendRedirect("/");
        } else {
        	String imageId = blobKey.get(0).getKeyString();
        	NewWork work = new NewWork();
        	work.id = UUID.randomUUID().toString();
        	work.name= req.getParameter("title");
        	work.description= req.getParameter("description");
        	work.imageId= imageId;
        	work.comments= new ArrayList<Comment>();
        	ObjectifyService.register(NewWork.class);
        	ObjectifyService.ofy().save().entity(work).now();
        	//Car c = ofy().load().type(Car.class).id("123123").now();
        	//ofy().delete().entity(c);
        	
            //res.sendRedirect("/serve?blob-key=" + blobKey.get(0).getKeyString());
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