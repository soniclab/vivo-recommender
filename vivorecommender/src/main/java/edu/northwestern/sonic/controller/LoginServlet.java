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
/*
 * author Anup 
 */
public class LoginServlet extends HttpServlet {
	private Logger logger = Logger.getLogger(this.getClass());
	private RequestDispatcher jsp;

	public void init(ServletConfig config) throws ServletException {
		ServletContext context = config.getServletContext();
		jsp = context.getRequestDispatcher("/login.jsp");
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		logger.debug("doGet()");
		String researchTopic = req.getParameter("search");
		if(researchTopic != null && !researchTopic.equals("")){
			HttpSession session = req.getSession();
			session.setAttribute("researchTopic", researchTopic);
		}
		jsp.forward(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {
		logger.debug("doPost()");

		String userEmail = req.getParameter("userEmail");
		User ego = null;
		try {
			ego = new Researcher().getUser(userEmail);
		} catch (URISyntaxException e) {
			logger.error(e,e);
		}
		HttpSession session = req.getSession();
		
		if (ego == null)
		{
			logger.debug("authentication failed: bad e-mail id");
			req.setAttribute("message", "Sorry ! We could not identify you.");
			jsp.forward(req, resp);
			return;
		}else {
			String userURI = ego.getUri().toString();
			//session.setAttribute("userURI", userURI);
			//session.setAttribute("userEmail", userEmail);
			session.setAttribute("ego", ego);
		}
		
		
		String researchTopic = (String)session.getAttribute("researchTopic");
		logger.debug("authenticated");
		String url = null;
		if(researchTopic != null && !researchTopic.equals("")){
			url = "recommend";
		}else{
			url = "index.jsp";
		}
		resp.sendRedirect(url);
	}  
}

