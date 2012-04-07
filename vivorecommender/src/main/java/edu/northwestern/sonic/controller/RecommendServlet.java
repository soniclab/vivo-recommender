package edu.northwestern.sonic.controller;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import edu.northwestern.sonic.dataaccess.vivo.Identification;
import edu.northwestern.sonic.dataaccess.vivo.Researcher;
import edu.northwestern.sonic.model.Recommend;
import edu.northwestern.sonic.model.User;

public class RecommendServlet extends HttpServlet {

	private Logger logger = Logger.getLogger(this.getClass());
	private RequestDispatcher recommendJsp;
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		ServletContext context = config.getServletContext();
		recommendJsp = context.getRequestDispatcher("/recommend.jsp");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String researchTopic = req.getParameter("search");
		if(researchTopic == null || researchTopic == ""){
			researchTopic = (String)req.getSession().getAttribute("researchTopic");
			if(researchTopic == null || researchTopic == ""){
				req.setAttribute("message", "Please enter a valid 'Research Topic'.");
				recommendJsp.forward(req, resp);
				return;
			}
		}
		Researcher researcher = new Researcher();
		User seeker;
		try {
			seeker = researcher.getUser((String)req.getSession().getAttribute("userEmail"));
			String imageUrl = researcher.getImage(seeker.getUri());
			Map<String,String[]> experts = getRecommendations(researchTopic,seeker);
			logger.debug("Forwarding to recommend.jsp");
			String[] egoDetails = new String[]{seeker.getUri().toString(),seeker.getName(),imageUrl};
			req.setAttribute("egoDetails", egoDetails);
			req.setAttribute("experts", experts);
			req.setAttribute("researchTopic",researchTopic);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			logger.error(e, e);
		}
		recommendJsp.forward(req, resp);
	}
	
	private Map<String,String[]> getRecommendations(String researchTopic, User seeker) throws URISyntaxException{
		Identification identification = new Identification();
		Set<URI> identifiedExperts;
		Recommend recommend = new Recommend();
		List<URI> combinedList = new ArrayList<URI>();
		identifiedExperts = identification.identifyExpertsByResearchArea(researchTopic);
		combinedList.addAll(recommend.affiliation(identifiedExperts, seeker));
		combinedList.addAll(recommend.friendOfFriend(identifiedExperts, seeker));
		return makeUsers(combinedList);
	}
	
	private Map<String,String[]> makeUsers(List<URI> combinedList) throws URISyntaxException{
		Map<String,String[]> experts = Collections.synchronizedMap(new HashMap<String,String[]>());
		Researcher researcher = new Researcher();
		URI uri = null;
		Iterator<URI> combinedItr = combinedList.iterator();
		while(combinedItr.hasNext()){
			uri = combinedItr.next();
			experts.put(uri.toString(), new String[]{researcher.getLabel(uri),
					researcher.getImage(uri)});
		}
		return experts;
	}
}
