package com.leeloo.viv.servlet;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.AuthorizationCodeFlow;
import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.extensions.appengine.auth.oauth2.AppEngineCredentialStore;
import com.google.api.client.extensions.appengine.datastore.AppEngineDataStoreFactory;
import com.google.api.client.extensions.appengine.datastore.AppEngineDataStoreFactory.Builder;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.appengine.api.blobstore.BlobKey;
import com.google.appengine.api.blobstore.BlobstoreService;
import com.google.appengine.api.blobstore.BlobstoreServiceFactory;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.leeloo.viv.work.repository.IdGenerator;
import com.leeloo.viv.work.repository.WorkFactory;
import com.leeloo.viv.work.repository.WorkRepo;
import com.leeloo.viv.work.usecase.UseCases;

public class AuthorizeDriveServlet extends HttpServlet {
	
	private static final String CLIENT_ID = "782217327038-f4j1lbb7gb9kpq1l7uaa3k844ub5hg12.apps.googleusercontent.com";
	private static final String CLIENT_SECRET = "DI54P-kGJO_PWZtYv073daKn";
	//private static final Collection<String> SCOPE = Arrays.asList("https://www.googleapis.com/auth/userinfo.profile;https://www.googleapis.com/auth/userinfo.email".split(";"));	
	
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    private WorkFactory workFactory = new WorkFactory(new IdGenerator());
    private WorkRepo workRepository = new WorkRepo();
            
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
      
      UserService userService = UserServiceFactory.getUserService();      
      if (!userService.isUserLoggedIn()) {          
          resp.sendRedirect("/login.html");
      }
      else{
    	  User user = userService.getCurrentUser();
          if(user == null){
        	  resp.sendRedirect("/login.html");
          }
          else{
        	  String userId = user.getUserId();                           
              List<String> scopes = Arrays.asList(
        		      // Required to access and manipulate files.
        		      "https://www.googleapis.com/auth/drive.file",
        		      // Required to identify the user in our data store.
        		      "https://www.googleapis.com/auth/userinfo.email",
        		      "https://www.googleapis.com/auth/userinfo.profile");
        	  
              AppEngineDataStoreFactory factory = new AppEngineDataStoreFactory();
              DataStore<StoredCredential> dataStore = factory.getDataStore(StoredCredential.DEFAULT_DATA_STORE_ID);
              
              GoogleAuthorizationCodeFlow flow =  new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(),new JacksonFactory(), 
          			CLIENT_ID, CLIENT_SECRET,  scopes).setAccessType("online").setApprovalPrompt("auto")
          			.setCredentialDataStore( dataStore)
          			.build();
              Credential credential = flow.loadCredential(userId);
              if(credential == null){
            	  GenericUrl callbackUrl = new GenericUrl(req.getRequestURL().toString());
            	  callbackUrl.setRawPath("/authorizedrivecallback");
            	  
            	  String url = flow.newAuthorizationUrl().setState("xyz").setRedirectUri(callbackUrl.build()).build();
            	  resp.sendRedirect(url);
              }
              else{
            	  resp.getWriter().write("<html><body><p>Already authorized</p></html></body>");
              }
          }          
      }      
    }
}