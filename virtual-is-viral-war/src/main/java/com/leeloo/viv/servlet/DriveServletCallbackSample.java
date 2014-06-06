package com.leeloo.viv.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.jdo.JDOHelper;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.AuthorizationCodeResponseUrl;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.appengine.auth.oauth2.AbstractAppEngineAuthorizationCodeCallbackServlet;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.drive.DriveScopes;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class DriveServletCallbackSample extends AbstractAppEngineAuthorizationCodeCallbackServlet {

	private static String CLIENT_ID = "782217327038-f4j1lbb7gb9kpq1l7uaa3k844ub5hg12.apps.googleusercontent.com";
	  private static String CLIENT_SECRET = "DI54P-kGJO_PWZtYv073daKn";
	  
	  @Override
	  protected void onSuccess(HttpServletRequest req, HttpServletResponse resp, Credential credential)
	      throws ServletException, IOException {
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
	    url.setRawPath("/callback");
	    return url.build();
	  }

//	  @Override
//	  protected AuthorizationCodeFlow initializeFlow() throws IOException {
//	    return new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(), new JacksonFactory(),
//	        "CLIENT_ID", "CLIENT_SECRET",
//	        Collections.singleton(DriveScopes.DRIVE)).setCredentialStore(
//	        new JdoCredentialStore(JDOHelper.getPersistenceManagerFactory("transactions-optional")))
//	        .build();
//	  }
	  
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

	  @Override
	  protected String getUserId(HttpServletRequest req) throws ServletException, IOException {
		  UserService userService = UserServiceFactory.getUserService();
		  return userService.getCurrentUser().getUserId();  
	  }
	}
