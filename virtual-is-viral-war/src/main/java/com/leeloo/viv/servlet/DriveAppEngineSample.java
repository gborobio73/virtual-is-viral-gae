package com.leeloo.viv.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeServlet;
import com.google.api.client.extensions.appengine.auth.oauth2.AppEngineCredentialStore;
import com.google.api.client.extensions.appengine.http.UrlFetchTransport;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;

public class DriveAppEngineSample extends AbstractAppEngineAuthorizationCodeServlet {
	private static final String CLIENT_ID = "782217327038-f4j1lbb7gb9kpq1l7uaa3k844ub5hg12.apps.googleusercontent.com";
	private static final String CLIENT_SECRET = "DI54P-kGJO_PWZtYv073daKn";
	  @Override
	  protected void doGet(HttpServletRequest request, HttpServletResponse response)
	      throws IOException {
	    System.out.println("Debug ug ug");
	  }

	  @Override
	  protected String getRedirectUri(HttpServletRequest req) throws ServletException, IOException {
	    GenericUrl url = new GenericUrl(req.getRequestURL().toString());
	    url.setRawPath("/oauth2callback");
	    return url.build();
	  }

	  @Override
	  protected AuthorizationCodeFlow initializeFlow() throws IOException {
		  List<String> scopes = Arrays.asList(
    		      // Required to access and manipulate files.
    		      "https://www.googleapis.com/auth/drive.file",
    		      // Required to identify the user in our data store.
    		      "https://www.googleapis.com/auth/userinfo.email",
    		      "https://www.googleapis.com/auth/userinfo.profile");
		  
      	return new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(),
			new JacksonFactory(), CLIENT_ID, CLIENT_SECRET,  scopes)
	.setAccessType("offline").setApprovalPrompt("auto").build();
	
//	    return new GoogleAuthorizationCodeFlow.Builder(new UrlFetchTransport(), new JacksonFactory(),
//	        "[[ENTER YOUR CLIENT ID]]", "[[ENTER YOUR CLIENT SECRET]]",scopes).setCredentialDataStore(  new CredentialStore()).build();
	  }

	
	}