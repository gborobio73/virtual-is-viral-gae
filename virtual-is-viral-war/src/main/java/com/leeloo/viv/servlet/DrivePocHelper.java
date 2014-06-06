package com.leeloo.viv.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.Drive.Files;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.api.services.drive.model.Permission;

public class DrivePocHelper {
	public static  void writeAndReadFiles(HttpServletResponse resp,  Credential credential ) throws IOException {
		
		System.out.println(System.getProperty("user.home"));
		
		PrintWriter writer = resp.getWriter();
        Drive drive = new Drive.Builder(new NetHttpTransport(),new JacksonFactory(), credential).setApplicationName(
		          "Virtual is viral").build();
        writer.write("<html><body>Listing My files<br>");		 
        
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
