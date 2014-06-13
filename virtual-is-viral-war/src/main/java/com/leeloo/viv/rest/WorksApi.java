package com.leeloo.viv.rest;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import java.util.List;

import com.google.gson.Gson;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.appengine.api.users.User;
import com.leeloo.viv.rest.jsonpojos.CommentToDelete;
import com.leeloo.viv.rest.jsonpojos.NewComment;
import com.leeloo.viv.rest.jsonpojos.UiWork;
import com.leeloo.viv.rest.jsonpojos.UiWorkMapper;
import com.leeloo.viv.work.repository.WorkRepo;
import com.leeloo.viv.work.usecase.UseCases;


@Path("works")
public class WorksApi {
	private  Gson gson = new Gson();
	
    @GET
    @Path("/all")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getAllWorks() {
        UserService userService = UserServiceFactory.getUserService();
        if (!userService.isUserLoggedIn()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } 

        List<UiWork> works = new UiWorkMapper().map(new WorkRepo().getAll());
        
        return Response.ok().entity(gson.toJson(works)).build();
    }

    @GET
    @Path("/user")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getUserWorks() {
        UserService userService = UserServiceFactory.getUserService();
        if (!userService.isUserLoggedIn()) {
            return Response.status(Response.Status.UNAUTHORIZED).build();
        } 

        User currentUser = userService.getCurrentUser();

        List<UiWork> works =  new UiWorkMapper().map(new WorkRepo().getFromUser(currentUser.getNickname()));
        return Response.ok().entity(gson.toJson(works)).build();
    }

    @GET
    @Path("/work/{id}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getWork(@PathParam("id") String id) {
    	 UserService userService = UserServiceFactory.getUserService();
         if (!userService.isUserLoggedIn()) {
             return Response.status(Response.Status.UNAUTHORIZED).build();
         } 
        
        UiWork work =  new UiWorkMapper().map(new WorkRepo().get(id));
        return Response.ok().entity(gson.toJson(work)).build();
    }
    
    @POST
    @Path("/comment/add")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addComment(NewComment comment) {
    	UserService userService = UserServiceFactory.getUserService();
    	if (!userService.isUserLoggedIn()) {
	        return Response.status(Response.Status.UNAUTHORIZED).build();
	    } 
    	
    	User user = userService.getCurrentUser();
    	new UseCases().addCommentToWork(comment.workId, comment.commentText, user.getNickname());
	    return Response.ok().build();
    }
    
    @POST
    @Path("/comment/delete")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteComment(CommentToDelete comment) {
    	UserService userService = UserServiceFactory.getUserService();
    	if (!userService.isUserLoggedIn()) {
	        return Response.status(Response.Status.UNAUTHORIZED).build();
	    } 
    	
    	User user = userService.getCurrentUser();
    	new UseCases().deleteCommentFromWork(comment.workId, comment.commentId, user.getNickname());
	    return Response.ok().build();
    }
}
