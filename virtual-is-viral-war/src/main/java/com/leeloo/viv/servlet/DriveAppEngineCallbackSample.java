package com.leeloo.viv.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeCallbackServlet;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

public class DriveAppEngineCallbackSample extends AbstractAppEngineAuthorizationCodeCallbackServlet {
	private static final String CLIENT_ID = "782217327038-f4j1lbb7gb9kpq1l7uaa3k844ub5hg12.apps.googleusercontent.com";
	private static final String CLIENT_SECRET = "DI54P-kGJO_PWZtYv073daKn";
	  @Override
	  protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential)
	      throws ServletException, IOException {
		  
		  final String dir = System.getProperty("user.dir");
	        System.out.println("current dir = " + DriveAppEngineCallbackSample.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		  
		  Drive service = new Drive.Builder(new NetHttpTransport(), new JacksonFactory(), credential).build();
		  
		  File body = new File();
		    body.setTitle("My other document");
		    body.setDescription("A test document");
		    body.setMimeType("text/plain");
		    
		    java.io.File fileContent = new java.io.File("document.txt");
		    FileContent mediaContent = new FileContent("text/plain", fileContent);

		    File file = service.files().insert(body, mediaContent).execute();
		    System.out.println("File ID: " + file.getId());
		  
		  resp.sendRedirect("/");
	  }

	  @Override
	  protected void onError(
	      HttpServletRequest req, HttpServletResponse resp, AuthorizationCodeResponseUrl errorResponse)
	      throws ServletException, IOException {
	    // handle error
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
//	        "[[ENTER YOUR CLIENT ID]]", "[[ENTER YOUR CLIENT SECRET]]",scopes).setCredentialStore(
//	        new AppEngineCredentialStore()).build();
	  }
	}