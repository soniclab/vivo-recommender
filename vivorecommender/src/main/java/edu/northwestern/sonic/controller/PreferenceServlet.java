package edu.northwestern.sonic.controller;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import edu.northwestern.sonic.dataaccess.vivo.Researcher;
import edu.northwestern.sonic.model.User;

public class PreferenceServlet extends HttpServlet{
	private Logger logger = Logger.getLogger(this.getClass());
	private RequestDispatcher preferenceJsp;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		ServletContext context = config.getServletContext();
		preferenceJsp = context.getRequestDispatcher("/preference.jsp");
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		logger.debug("doGet()");
		HttpSession session = req.getSession();
		User ego = (User)session.getAttribute("ego");
		// code to set the checkboxs on screen
		preferenceJsp.forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		logger.debug("doPost()");
		// code to make changes to preferences of ego based on checkboxs on screen

		
	}  
}
