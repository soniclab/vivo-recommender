package edu.northwestern.sonic.controller;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import edu.northwestern.sonic.model.User;

public class SecurityFilter implements Filter 
{
	private Logger logger = Logger.getLogger(this.getClass());
	public void init(FilterConfig filterConfig) throws ServletException
	{
	}

	public void destroy() 
	{
	}

	public void doFilter(
			ServletRequest request, 
			ServletResponse response,
			FilterChain chain) 
					throws IOException, ServletException 
					{
		logger.debug("doFilter()");
		HttpServletResponse resp = (HttpServletResponse) response;
		HttpServletRequest req = (HttpServletRequest) request;
		String servletPath = req.getServletPath();

		// Allow access to login functionality.
		if (servletPath.equals("/login"))
		{
			chain.doFilter(req, resp);
			return;
		}
		// Allow access to about.
		if (servletPath.equals("/about")) {
			chain.doFilter(req, resp);
			return;
		}

		// Allow access to home.
		if (servletPath.equals("/index.jsp")) {
			chain.doFilter(req, resp);
			return;
		}
		// All other functionality requires authentication.
		HttpSession session = req.getSession();
		User ego = (User)session.getAttribute("ego");
		if (ego != null)
		{
			// User is authenticated.
			chain.doFilter(req, resp);
			return;
		}

		// Request is not authorized.
		String researchTopic = req.getParameter("search");
		if(researchTopic!=null && !researchTopic.equals("")){
			session.setAttribute("researchTopic", researchTopic);
		}
		resp.sendRedirect("login");
	}   
}
