package edu.northwestern.sonic.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

public class AboutServlet extends HttpServlet 
{
	private Logger logger = Logger.getLogger(this.getClass());
	private RequestDispatcher jsp;

	public void init(ServletConfig config) throws ServletException {
		ServletContext context = config.getServletContext();
		jsp = context.getRequestDispatcher("/about.jsp");
	}
   
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
   throws ServletException, IOException {
      logger.debug("doGet()");
      jsp.forward(req, resp);
   }
}
