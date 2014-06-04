package com.leeloo.viv.servlet.drive;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson.JacksonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.gson.Gson;

public class ViewDriveFile  extends HttpServlet {
	public static final String KEY_SESSION_USERID = "user_id";
	 protected static final HttpTransport TRANSPORT = new NetHttpTransport();
	  protected static final JsonFactory JSON_FACTORY = new JacksonFactory();
	  private CredentialManager credentialManager = null;
	  
	  public static final String CLIENT_SECRETS_FILE_PATH= "/WEB-INF/client_secrets.json";
	  
	  @Override
	  public void init() throws ServletException {
	    super.init();
	    // init credential manager
	    GoogleClientSecrets secrets = getClientSecrets();
	    //credentialManager = new CredentialManager(secrets, TRANSPORT, JSON_FACTORY);
	  }
	  
	  
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
		      throws IOException {
		    //Drive service = getDriveService(getCredential(req, resp));
		    //String fileId = req.getParameter("file_id");

//		    if (fileId == null) {
//		      sendError(resp, 400, "The `file_id` URI parameter must be specified.");
//		      return;
//		    }
//
//		    File file = null;
//		    try {
//		      file = service.files().get(fileId).execute();
//		    } catch (GoogleJsonResponseException e) {
//		      if (e.getDetails().code == 401) {
//		        // The user has revoked our token or it is otherwise bad.
//		        // Delete the local copy so that their next page load will recover.
//		        deleteCredential(req, resp);
//		        sendGoogleJsonResponseError(resp, e);
//		        return;
//		      }
//		    }
//
//		    if (file != null) {
//		      String content = downloadFileContent(service, file);
//		      if (content == null) {
//		        content = "";
//		      }
//		      sendJson(resp, new ClientFile(file, content));
//		    } else {
//		      sendError(resp, 404, "File not found");
//		    }
		  }
	
	protected Credential getCredential(HttpServletRequest req,
		      HttpServletResponse resp) {
		    String userId = (String) req.getSession().getAttribute(KEY_SESSION_USERID);
		    if (userId != null) {
		      return credentialManager.get(userId);
		    }
		    return null;
		  }
	
	protected Drive getDriveService(Credential credential) {
	    return new Drive.Builder(TRANSPORT, JSON_FACTORY, credential).build();
	  }
	
	private GoogleClientSecrets getClientSecrets() {
	    // TODO: do not read on each request
	    InputStream stream =
	        getServletContext().getResourceAsStream(CLIENT_SECRETS_FILE_PATH);
	    Reader reader = new InputStreamReader(stream);
	    try {
	      return GoogleClientSecrets.load(JSON_FACTORY, reader);
	    } catch (IOException e) {
	      throw new RuntimeException("No client_secrets.json found");
	    }
	  }
	
	 protected void sendJson(HttpServletResponse resp, int code, Object obj) {
		    try {
		      // TODO(burcud): Initialize Gson instance for once.
		      resp.setContentType("application/json");
		      resp.getWriter().print(new Gson().toJson(obj).toString());
		    } catch (IOException e) {
		      throw new RuntimeException(e);
		    }
		  }

		  /**
		   * Dumps the given object to JSON and responds with HTTP 200.
		   * @param resp  Response object.
		   * @param obj   An object to be dumped as JSON.
		   */
		  protected void sendJson(HttpServletResponse resp, Object obj) {
		    sendJson(resp, 200, obj);
		  }

		  /**
		   * Responds with the given HTTP status code and message.
		   * @param resp  Response object.
		   * @param code  HTTP status code to respond with.
		   * @param message Message body.
		   */
		  protected void sendError(HttpServletResponse resp, int code, String message) {
		    try {
		      resp.sendError(code, message);
		    } catch (IOException e) {
		      throw new RuntimeException(message);
		    }
		  }

		  /**
		   * Transforms a GoogleJsonResponseException to an HTTP response.
		   * @param resp  Response object.
		   * @param e     Exception object to transform.
		   */
		  protected void sendGoogleJsonResponseError(HttpServletResponse resp,
		      GoogleJsonResponseException e) {
		    sendError(resp, e.getDetails().code, e.getLocalizedMessage());
		  }
		  
		  protected void deleteCredential(HttpServletRequest req,
			      HttpServletResponse resp) {
			    String userId = (String) req.getSession().getAttribute(KEY_SESSION_USERID);
			    if (userId != null) {
			      credentialManager.delete(userId);
			      req.getSession().removeAttribute(KEY_SESSION_USERID);
			    }
			  }
		  
		  private String downloadFileContent(Drive service, File file)
			      throws IOException {
			    GenericUrl url = new GenericUrl(file.getDownloadUrl());
			    HttpResponse response = service.getRequestFactory().buildGetRequest(url)
			        .execute();
			    try {
			      return new Scanner(response.getContent()).useDelimiter("\\A").next();
			    } catch (java.util.NoSuchElementException e) {
			      return "";
			    }
			  }
}
