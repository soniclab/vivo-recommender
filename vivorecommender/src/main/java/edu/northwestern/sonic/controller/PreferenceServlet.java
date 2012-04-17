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
	ServletContext context;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		context = config.getServletContext();
	}
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		logger.debug("doGet()");
		preferenceJsp = context.getRequestDispatcher("/preference.jsp");
		preferenceJsp.forward(req, resp);
	}
	
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		logger.debug("doPost()");
        String affiliation = req.getParameter("affiliation");
        String mostQualified = req.getParameter("mqualified");
        String foaf = req.getParameter("foaf");
        String exchange = req.getParameter("exchange");
        String ftc = req.getParameter("ftc");
        String boaf = req.getParameter("boaf");
        String mobilizing = req.getParameter("mobilizing");
		String lucky = req.getParameter("lucky");
		String cocitation = req.getParameter("cocitation");
		
		HttpSession session = req.getSession();
		User ego = (User)session.getAttribute("ego");
		
		ego.setAffiliation("affiliation".equals(affiliation));
		ego.setMostQualified("mqualified".equals(mostQualified));
		ego.setFriendOfFriend("foaf".equals(foaf));
		ego.setExchange("exchange".equals(exchange));
		ego.setFollowCrowd("ftc".equals(ftc));
		ego.setBirdsOfFeather("boaf".equals(boaf));
		ego.setMobilizing("mobilizing".equals(mobilizing));
		ego.setFeelingLucky("lucky".equals(lucky));
		ego.setCitation("cocitation".equals(cocitation));
		
		resp.sendRedirect("recommend");
	}  
}
