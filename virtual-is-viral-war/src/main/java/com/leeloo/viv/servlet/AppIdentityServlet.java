package com.leeloo.viv.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.auth.oauth2.StoredCredential;
import com.google.api.client.extensions.appengine.datastore.AppEngineDataStoreFactory;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStore;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;
import com.google.apphosting.api.ApiProxy;

public class AppIdentityServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		
		String appId = ApiProxy.getCurrentEnvironment().getAppId();
		System.out.println("APP ID: "+appId);
		System.out.println("email: "+ApiProxy.getCurrentEnvironment().getEmail());
		System.out.println("auth domain: "+ApiProxy.getCurrentEnvironment().getAuthDomain());
		
		List<String> scopes = Arrays.asList(
  		      // Required to access and manipulate files.
  		      "https://www.googleapis.com/auth/drive.file",
  		      // Required to identify the user in our data store.
  		      "https://www.googleapis.com/auth/userinfo.email",
  		      "https://www.googleapis.com/auth/userinfo.profile");
		
		 AppIdentityCredential credential =new AppIdentityCredential(scopes);
		 
		 Drive drive = new Drive.Builder(new NetHttpTransport(),new JacksonFactory(), credential).setApplicationName(
		          "Virtual is viral").build();
		 PrintWriter writer = resp.getWriter();         
         writer.write("<html><body>Listing My files<br>");		  		
         List<File> result = new ArrayList<File>();
         Files.List request = drive.files().list();
		  do {
			  try {
				  FileList files = request.execute();

				  result.addAll(files.getItems());
				  request.setPageToken(files.getNextPageToken());
			  } catch (IOException e) {
				  System.out.println("An error occurred: " + e);
				  request.setPageToken(null);
		      }
		  } while (request.getPageToken() != null &&
				  request.getPageToken().length() > 0);
		    
		  writer.write("number of files:" + result.size());
		    
		  for(Iterator<File> i = result.iterator(); i.hasNext(); ) {
			  File item = i.next();
			  writer.write(item.getTitle() + "<br>");
			  writer.write(item.getId()+ "<br>");
			  writer.write(item.getDownloadUrl()+ "<br>");
		        //System.out.println(item.getPermissions().get(0).getRole());
		        //when doc is public https://drive.google.com/uc?id=FILE-ID
		  }
		  writer.write("</body></html>");        
	}
}
