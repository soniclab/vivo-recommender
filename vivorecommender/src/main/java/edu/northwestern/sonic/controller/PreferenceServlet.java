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
		
		req.setAttribute("affiliation", ego.isAffiliation());
		req.setAttribute("mqualified", ego.isMostQualified());
		req.setAttribute("foaf",ego.isFriendOfFriend());
		req.setAttribute("exchange", ego.isExchange());
		req.setAttribute("ftc", ego.isFollowCrowd());
		req.setAttribute("boaf",ego.isBirdsOfFeather());
		req.setAttribute("mobilizing",ego.isMobilizing());
		req.setAttribute("lucky", ego.isFeelingLucky());
		req.setAttribute("cocitation", ego.isCitation());
	
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
		
		ego.setAffiliation("ON".equals(affiliation));
		ego.setMostQualified("ON".equals(mostQualified));
		ego.setFriendOfFriend("ON".equals(foaf));
		ego.setExchange("ON".equals(exchange));
		ego.setFollowCrowd("ON".equals(ftc));
		ego.setBirdsOfFeather("ON".equals(boaf));
		ego.setMobilizing("ON".equals(mobilizing));
		ego.setFeelingLucky("ON".equals(lucky));
		ego.setCitation("ON".equals(cocitation));
		
	}  
}
