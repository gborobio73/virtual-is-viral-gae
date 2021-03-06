package com.leeloo.viv.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.NewCookie;
import java.util.List;

import com.google.gson.Gson;

import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.users.User;

import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;


@Path("upload")
public class UploadApi {
    @GET
    @Path("url")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getUrl() {

        String uploadFileURL="";
        UserService userService = UserServiceFactory.getUserService();
        
        BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
        
        uploadFileURL=blobstoreService.createUploadUrl("/upload");

        return Response.ok().entity(new Gson().toJson(new Url(uploadFileURL))).build();

    }

    private class Url
    {
        String uploadFileURL;
        
        public Url(String uploadFileURL)
        {
            this.uploadFileURL = uploadFileURL;
        }
    }
}
