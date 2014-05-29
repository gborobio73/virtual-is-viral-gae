package com.leeloo.viv.servlet;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogoutServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {
    resp.setContentType("text/plain");
    String baseURL = req.getRequestURI();
    String loginURL="";
    String logOutURL="";
    UserService userService = UserServiceFactory.getUserService();
    
    logOutURL = userService.createLogoutURL(baseURL);
    
    resp.sendRedirect(userService.createLogoutURL("/logout.html"));
  }

}