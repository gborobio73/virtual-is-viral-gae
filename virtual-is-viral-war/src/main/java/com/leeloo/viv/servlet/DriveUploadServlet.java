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
import com.google.api.client.auth.oauth2.TokenResponse;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeRequestUrl;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.client.json.jackson2.JacksonFactory;
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

public class DriveUploadServlet extends HttpServlet {
	
	private static final String CLIENT_ID = "782217327038-f4j1lbb7gb9kpq1l7uaa3k844ub5hg12.apps.googleusercontent.com";
	private static final String CLIENT_SECRET = "DI54P-kGJO_PWZtYv073daKn";
	//private static final Collection<String> SCOPE = Arrays.asList("https://www.googleapis.com/auth/userinfo.profile;https://www.googleapis.com/auth/userinfo.email".split(";"));	
	
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
    	
        
        String userId = userService.getCurrentUser().getUserId();
        if (userId != null) {
        	List<String> scopes = Arrays.asList(
        		      // Required to access and manipulate files.
        		      "https://www.googleapis.com/auth/drive.file",
        		      // Required to identify the user in our data store.
        		      "https://www.googleapis.com/auth/userinfo.email",
        		      "https://www.googleapis.com/auth/userinfo.profile");
        	GoogleAuthorizationCodeRequestUrl urlBuilder =
        	        new GoogleAuthorizationCodeRequestUrl(
        	        		CLIENT_ID,"urn:ietf:wg:oauth:2.0:oob",
        	        scopes).setAccessType("offline").setApprovalPrompt("force");
        	String url =  urlBuilder.build();
        	res.sendRedirect(url);
        	
//        	GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(),
//        			new JacksonFactory(), CLIENT_ID, CLIENT_SECRET,  Arrays.asList(DriveScopes.DRIVE))
//        	.setAccessType("offline").setApprovalPrompt("auto").build();
//    		
//        	Credential credential = flow.loadCredential(userId);
//        	if (credential == null)
//        	{
//        		String redirectURI = req.getScheme() + "://" +req.getServerName()  + req.getServerPort() +req.getContextPath() + "/upload";
//        		
//        		String url = flow.newAuthorizationUrl().setRedirectUri(redirectURI).build();
//        		res.sendRedirect(url);
//        	}
        	System.out.println("debugging");
        	//Drive.Builder();
        	//CredentialManager credentialManager new CredentialManager(getClientSecrets(), TRANSPORT, JSON_FACTORY);
            //return credentialManager.get(userId);
        }
        
        
//        Map<String, List<BlobKey>> blobs = blobstoreService.getUploads(req);		
//        List<BlobKey> blobKey = blobs.get("myFileName");
//
//        if (blobKey == null) {
//            res.sendRedirect("/error.html");
//        } else {
//        	String imageId = blobKey.get(0).getKeyString();
//        	String title = req.getParameter("title");
//        	String description = req.getParameter("description");
//        	User currentUser = userService.getCurrentUser();
//        	
//        	new UseCases().createWork(currentUser.getNickname(), title, description, imageId);
//        	
//        	res.sendRedirect("/login");
//        }
        
        
    }   
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
      
      UserService userService = UserServiceFactory.getUserService();
      String userId = userService.getCurrentUser().getUserId();
      if (!userService.isUserLoggedIn()) {          
          resp.sendRedirect("/login.html");
      }
      
      String code = req.getParameter("code");
      if (code != null) {

    	  GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(new NetHttpTransport(),
      			new JacksonFactory(), CLIENT_ID, CLIENT_SECRET,  Arrays.asList(DriveScopes.DRIVE))
      	.setAccessType("offline").setApprovalPrompt("auto").build();
    	  
        GoogleAuthorizationCodeTokenRequest tokenRequest = flow.newTokenRequest(code);
        TokenResponse tokenResponse = tokenRequest.execute();
        Credential credential = flow.createAndStoreCredential(tokenResponse, userId);
        // request userinfo
        
      }
    }
}