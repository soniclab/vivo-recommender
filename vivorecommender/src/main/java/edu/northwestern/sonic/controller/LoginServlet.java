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

import edu.northwestern.sonic.dataaccess.vivo.Researcher;
import edu.northwestern.sonic.model.User;
import edu.northwestern.sonic.persistence.JavatoRDF;
import edu.northwestern.sonic.persistence.TDBTransaction;

/**
 * @author Anup
 * Login Servlet helps authenticate user, get the state information if
 * the user state history is present TDB and redirect to necessary page.
 */
@SuppressWarnings("serial")
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
		
		if(userEmail !=null && !userEmail.equals("")){
			/* check to see if the ego state is present in TDB */
			TDBTransaction tdbTransaction = new TDBTransaction();
			ego = JavatoRDF.read(tdbTransaction.getDefaultModel(), userEmail);
		}else{
			logger.debug("authentication failed: blank e-mail id");
			req.setAttribute("message", "Sorry ! We could not identify you. " +
					"Please try again with your VIVO UFL email-id.");
			jsp.forward(req, resp);
			return;
		}
		
		/* if the ego state is not present in TDB then check with Endpoint */
		if(ego == null){
			ego = new Researcher().getUser(userEmail);
		}
		
		HttpSession session = req.getSession();
		
		/* If ego is not found then return Authentication failed else get 
		 * image URL and put the ego object in session */
		if (ego == null)
		{
			logger.debug("authentication failed: bad e-mail id");
			req.setAttribute("message", "Sorry ! We could not identify you. " +
					"Please try again with your VIVO UFL email-id.");
			jsp.forward(req, resp);
			return;
		}else {
			if(ego.getImageUrl()==null || ego.getImageUrl().equals("")){
				String imageUrl = new Researcher().getImage(ego.getUri());
				ego.setImageUrl(imageUrl);	
			}
			session.setAttribute("ego", ego);
		}
		
		/* Redirect the user to Recommendation results page if the query has
		 * already been fired or to the home page if there was no query fired
		 * before logging in.
		 */
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

