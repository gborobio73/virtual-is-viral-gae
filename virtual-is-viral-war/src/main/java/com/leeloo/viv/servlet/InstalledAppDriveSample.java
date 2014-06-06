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
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.DataStoreFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

public class InstalledAppDriveSample extends HttpServlet {
	private static final String APPLICATION_NAME = "Virtual Is Viral";

	  private static String CLIENT_ID = "782217327038-f4j1lbb7gb9kpq1l7uaa3k844ub5hg12.apps.googleusercontent.com";
	  private static String CLIENT_SECRET = "DI54P-kGJO_PWZtYv073daKn";
	  /**
	   * Global instance of the {@link DataStoreFactory}. The best practice is to make it a single
	   * globally shared instance across your application.
	   */
	  
	  /** Global instance of the HTTP transport. */
	  private static HttpTransport httpTransport;

	  /** Global instance of the JSON factory. */
	  private static final JsonFactory JSON_FACTORY =  new JacksonFactory();

	  private static Drive drive;
	@Override
	  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		try {
		      httpTransport = GoogleNetHttpTransport.newTrustedTransport();
		      // authorization
		      Credential credential = authorize();
		      // set up global Plus instance
		      drive = new Drive.Builder(httpTransport, JSON_FACTORY, credential).setApplicationName(
		          APPLICATION_NAME).build();
		      // run commands
		      addFile();
		      listFiles(resp);
//		      listActivities();
//		      getActivity();
//		      getProfile();
//		      // success!
		      return;
		    } catch (IOException e) {
		      System.err.println(e.getMessage());
		    } catch (Throwable t) {
		      t.printStackTrace();
		    }
		    

	  }
	
	 private static Credential authorize() throws Exception {
		    // load client secrets
//		    GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
//		        new InputStreamReader(PlusSample.class.getResourceAsStream("/client_secrets.json")));
//		    if (clientSecrets.getDetails().getClientId().startsWith("Enter")
//		        || clientSecrets.getDetails().getClientSecret().startsWith("Enter ")) {
//		      System.out.println(
//		          "Enter Client ID and Secret from https://code.google.com/apis/console/?api=plus "
//		          + "into plus-cmdline-sample/src/main/resources/client_secrets.json");
//		      System.exit(1);
//		    }
		    // set up authorization code flow
//		    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
//		        httpTransport, JSON_FACTORY, clientSecrets,
//		        Collections.singleton(PlusScopes.PLUS_ME)).setDataStoreFactory(
//		        dataStoreFactory).build();
		    GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
		            httpTransport, JSON_FACTORY, CLIENT_ID, CLIENT_SECRET, Arrays.asList(DriveScopes.DRIVE_FILE))
		            .setAccessType("online")
		            .setApprovalPrompt("auto").build();
		    // authorize
		    return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("user");
		  }

		  
		  	private static void addFile() throws IOException {
		  		File body = new File();
		  	    body.setTitle("My image");
		  	    body.setDescription("A test image");
		  	    body.setMimeType("image/jpeg");
		  	    
		  	    java.io.File fileContent = new java.io.File("9.jpg");
		  	    FileContent mediaContent = new FileContent("image/jpeg", fileContent);

		  	    File file = drive.files().insert(body, mediaContent).execute();
		  	    
			  	  Permission permission = new Permission();
			      permission.setRole("reader");
			      permission.setType("anyone");
			      permission.setValue("me");
			      drive.permissions().insert(file.getId(), permission).execute();

		  	    System.out.println("File ID: " + file.getId());
			
		  	}

			private static void listFiles(HttpServletResponse resp) throws IOException {
				// TODO Auto-generated method stub
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
			    System.out.println(result.size());
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
