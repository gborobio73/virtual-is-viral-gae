package com.leeloo.viv.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
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
import com.google.api.client.extensions.appengine.datastore.AppEngineDataStoreFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
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

public class AuthorizeDriveServletCallback extends HttpServlet {
	
	private static final String CLIENT_ID = "782217327038-f4j1lbb7gb9kpq1l7uaa3k844ub5hg12.apps.googleusercontent.com";
	private static final String CLIENT_SECRET = "DI54P-kGJO_PWZtYv073daKn";
	//private static final Collection<String> SCOPE = Arrays.asList("https://www.googleapis.com/auth/userinfo.profile;https://www.googleapis.com/auth/userinfo.email".split(";"));	
	
    private BlobstoreService blobstoreService = BlobstoreServiceFactory.getBlobstoreService();
    private WorkFactory workFactory = new WorkFactory(new IdGenerator());
    private WorkRepo workRepository = new WorkRepo();
            
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
      
      UserService userService = UserServiceFactory.getUserService();
      String userId = userService.getCurrentUser().getUserId();
      if (!userService.isUserLoggedIn()) {          
          resp.sendRedirect("/login.html");
      }
      
      String code = req.getParameter("code");
      if (code != null) {

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
          
    	  String currentURI = new GenericUrl(req.getRequestURL().toString()).build();
          GoogleAuthorizationCodeTokenRequest tokenRequest = flow.newTokenRequest(code).setRedirectUri(currentURI);
          TokenResponse tokenResponse = tokenRequest.execute();
          Credential credential = flow.createAndStoreCredential(tokenResponse, userId);
          
          DrivePocHelper.writeAndReadFiles(resp, credential); 
      }
      
           

    }
}