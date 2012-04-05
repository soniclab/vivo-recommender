package edu.northwestern.sonic.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import edu.northwestern.sonic.bean.User;
import edu.northwestern.sonic.model.Recommend;
import edu.northwestern.sonic.sparql.SparqlEngine;

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
		SparqlEngine sparqlEngine = new SparqlEngine();
		User seeker = sparqlEngine.getUser("eabuss@ufl.edu");
		String imageUrl = sparqlEngine.getImage(seeker.getUri());
		Map<String,String[]> experts = getRecommendations(researchTopic,seeker,sparqlEngine);
		logger.debug("Forwarding to recommend.jsp");
		String[] egoDetails = new String[]{seeker.getUri(),seeker.getName(),imageUrl};
		req.setAttribute("egoDetails", egoDetails);
		req.setAttribute("experts", experts);
		req.setAttribute("researchTopic",researchTopic);
		recommendJsp.forward(req, resp);
	}
	
	private Map<String,String[]> getRecommendations(String researchTopic, User seeker, SparqlEngine sparqlEngine){
		List<String> identifiedExperts = sparqlEngine.identifyExpertsByResearchArea(researchTopic);
		Recommend recommend = new Recommend();
		List<String> combinedList = new ArrayList<String>();
		combinedList.addAll(recommend.affiliation(identifiedExperts, seeker));
		combinedList.addAll(recommend.friendOfFriend(identifiedExperts, seeker));
		return makeUsers(combinedList,sparqlEngine);
	}
	
	private Map<String,String[]> makeUsers(List<String> combinedList, SparqlEngine sparqlEngine){
		Map<String,String[]> experts = Collections.synchronizedMap(new HashMap<String,String[]>());
		String uri = null;
		Iterator<String> combinedItr = combinedList.iterator();
		while(combinedItr.hasNext()){
			uri = combinedItr.next();
			experts.put(uri, new String[]{sparqlEngine.getLabel(uri),
					sparqlEngine.getImage(uri)});
		}
		return experts;
	}
}
