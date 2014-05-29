package com.leeloo.viv.servlet;

import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {
  @Override
  public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    
    UserService userService = UserServiceFactory.getUserService();
    
    if (userService.isUserLoggedIn()) {
        resp.sendRedirect("/#/Board");
    } else {
        resp.sendRedirect(userService.createLoginURL("/#/Board"));
    }
  }

}