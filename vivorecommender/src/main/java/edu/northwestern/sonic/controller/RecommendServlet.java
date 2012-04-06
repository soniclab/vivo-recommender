package edu.northwestern.sonic.controller;

import java.io.IOException;
import java.net.URISyntaxException;
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
		Researcher researcher = new Researcher();
		User seeker = researcher.getUser("eabuss@ufl.edu");
		String imageUrl = researcher.getImage(seeker.getUri());
		Map<String,String[]> experts = getRecommendations(researchTopic,seeker,researcher);
		logger.debug("Forwarding to recommend.jsp");
		String[] egoDetails = new String[]{seeker.getUri().toString(),seeker.getName(),imageUrl};
		req.setAttribute("egoDetails", egoDetails);
		req.setAttribute("experts", experts);
		req.setAttribute("researchTopic",researchTopic);
		recommendJsp.forward(req, resp);
	}
	
	private Map<String,String[]> getRecommendations(String researchTopic, User seeker, Researcher researcher){
		List<String> identifiedExperts = researcher.identifyExpertsByResearchArea(researchTopic);
		Recommend recommend = new Recommend();
		List<String> combinedList = new ArrayList<String>();
		combinedList.addAll(recommend.affiliation(identifiedExperts, seeker));
		combinedList.addAll(recommend.friendOfFriend(identifiedExperts, seeker));
		return makeUsers(combinedList,researcher);
	}
	
	private Map<String,String[]> makeUsers(List<String> combinedList, Researcher researcher){
		Map<String,String[]> experts = Collections.synchronizedMap(new HashMap<String,String[]>());
		String uri = null;
		Iterator<String> combinedItr = combinedList.iterator();
		while(combinedItr.hasNext()){
			uri = combinedItr.next();
			experts.put(uri, new String[]{researcher.getLabel(uri),
					researcher.getImage(uri)});
		}
		return experts;
	}
}
