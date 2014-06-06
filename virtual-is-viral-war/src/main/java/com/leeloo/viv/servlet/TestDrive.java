package com.leeloo.viv.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.googleapis.extensions.appengine.auth.oauth2.AppIdentityCredential;
import com.google.api.client.googleapis.services.CommonGoogleClientRequestInitializer;
import com.google.api.client.googleapis.services.GoogleClientRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveRequestInitializer;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class TestDrive extends HttpServlet {
	  @Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
	    
	    UserService userService = UserServiceFactory.getUserService();
	    
	    if (!userService.isUserLoggedIn()) {
	        
	        resp.sendRedirect("/login.html");
	    }
	    
	    HttpTransport httpTransport = new NetHttpTransport();
	    JsonFactory jsonFactory = new JacksonFactory();
	    AppIdentityCredential credential =
	        new AppIdentityCredential.Builder(Arrays.asList(DriveScopes.DRIVE)).build();
	    // API_KEY is from the Google Console as a server API key
	    GoogleClientRequestInitializer keyInitializer =
	        new CommonGoogleClientRequestInitializer("AIzaSyA9mh3kQqnN6UM7rmBunMk0OwgcILg7RqU");
	    
	    Drive service = new Drive.Builder(new NetHttpTransport(), new JacksonFactory(), credential).build();
//	    Drive service = new Drive.Builder(httpTransport, jsonFactory, null)
//	        .setHttpRequestInitializer(credential)
//	        .setGoogleClientRequestInitializer(keyInitializer)
//	        .build();
	    
//	    DriveRequestInitializer initializer = new DriveRequestInitializer("AIzaSyA9mh3kQqnN6UM7rmBunMk0OwgcILg7RqU");
//	    Drive service = new Drive.Builder(new NetHttpTransport(), new JacksonFactory(), null)
//	        .setApplicationName("Google-PlusSample/1.0")
//	        .setDriveRequestInitializer(initializer)
//	        .build();
	    List<File> result = new ArrayList<File>();
	    Files.List request = service.files().list();
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

	    System.out.println(result.size());

	    System.out.println("yep yep ");
	    
//	    List<String> scopes = Arrays.asList(
//  		      // Required to access and manipulate files.
//  		      "https://www.googleapis.com/auth/drive.file",
//  		      // Required to identify the user in our data store.
//  		      "https://www.googleapis.com/auth/userinfo.email",
//  		      "https://www.googleapis.com/auth/userinfo.profile");
//	    
//	    AppIdentityCredential credential =new AppIdentityCredential(scopes);
//	    Drive service =  new Drive.Builder(new NetHttpTransport(), new JacksonFactory(), credential).build();
//	    
//	    System.out.println("yep");
	    
	  }

	}