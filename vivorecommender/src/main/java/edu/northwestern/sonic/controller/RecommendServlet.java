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

@SuppressWarnings("serial")
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
		User ego;
		try {
			HttpSession session = req.getSession();
			ego = (User)session.getAttribute("ego");
			Map<String,List<String>> experts = getRecommendations(researchTopic,ego);
			logger.debug("Forwarding to recommend.jsp");
			String[] egoDetails = new String[]{ego.getUri().toString(),ego.getName(),ego.getImageUrl()};
			req.setAttribute("egoDetails", egoDetails);
			req.setAttribute("experts", experts);
			req.setAttribute("researchTopic",researchTopic);
			session.setAttribute("experts", experts);
		} catch (Exception e) {
			logger.error(e, e);
		}
		recommendJsp.forward(req, resp);
	}
	
	/**
	 * @author Anup
	 * @param researchTopic
	 * @param ego
	 * @return Map of uris with list of details for recommended experts.
	 * @throws URISyntaxException
	 */
	private Map<String,List<String>> getRecommendations(String researchTopic, User ego) throws URISyntaxException{
		Identification identification = new Identification();
		Recommend recommend = new Recommend();
		List<List<User>> combinedList = new ArrayList<List<User>>();
		
		Set<URI> identifiedExperts = identification.identifyExpertsByResearchArea(researchTopic);
		Set<URI> identifiedByK = identification.identifyExpertsByKeyword(researchTopic);
		
		identifiedExperts.addAll(identifiedByK);
		
		List<User> affList = null;
		List<User> fofList = null;
		List<User> bofList = null;
		List<User> cocitList = null;
		List<User> exchangeList = null;
		List<User> mquaList = null;
		List<User> ftcList = null;
		
		if(ego.isAffiliation())
			affList = recommend.affiliation(identifiedExperts, ego);
		if(ego.isFriendOfFriend())
			fofList = recommend.friendOfFriend(identifiedExperts, ego);
		if(ego.isBirdsOfFeather())
			bofList = recommend.birdsOfFeather(identifiedExperts, ego);
		if(ego.isCitation())
			cocitList = recommend.cocitation(identifiedExperts, ego.getUri());
		if(ego.isMostQualified())
			mquaList = recommend.mostQualified(identifiedExperts, ego,researchTopic);
		/*if(ego.isExchange())
			exchangeList = recommend.exchange(identifiedExperts, ego);
		if(ego.isFollowCrowd())
			ftcList = recommend.followTheCrowd(identifiedExperts, ego);*/
		
		List<String> heuristics = new ArrayList<String>();
		if(affList !=null && affList.size() > 0){
			combinedList.add(affList);
			heuristics.add("'Affiliation'");
		}
		if(fofList !=null && fofList.size() > 0){
			combinedList.add(fofList);
			heuristics.add("'Friend of a Friend'");
		}
		if(bofList !=null && bofList.size() > 0){
			combinedList.add(bofList);
			heuristics.add("'Birds of Feather'");
		}
		if(cocitList !=null && cocitList.size() > 0){
			combinedList.add(cocitList);
			heuristics.add("'Co-citation'");
		}
		if(exchangeList !=null && exchangeList.size() > 0){
			combinedList.add(exchangeList);
			heuristics.add("'Social Exchange'");
		}
		if(mquaList !=null && mquaList.size() > 0){
			combinedList.add(mquaList);
			heuristics.add("'Most Qualified'");
		}
		if(ftcList !=null && ftcList.size() > 0){
			combinedList.add(ftcList);
			heuristics.add("'Follow the Crowd'");
		}
		return makeFinalList(combinedList,heuristics);
	}
	
	/**
	 * @author Anup
	 * @param combinedList
	 * @param heuristics
	 * @return Map of uris with list of details for recommended experts.
	 * @throws URISyntaxException
	 */
	private Map<String,List<String>> makeFinalList(List<List<User>> combinedList, List<String> heuristics) throws URISyntaxException{
		Map<String,List<String>> experts = Collections.synchronizedMap(new HashMap<String,List<String>>());
		Researcher researcher = new Researcher();
		List<User> list = null;
		User expert = null;
		int count = 0;
		List<String> details;
		Iterator<List<User>> combinedItr = combinedList.iterator();
		while(combinedItr.hasNext()){
			list = combinedItr.next();
			Iterator<User> itr = list.iterator();
			while(itr.hasNext()){
				expert = itr.next();
				if(!experts.containsKey(expert.getUri().toString())){
					experts.put(expert.getUri().toString(), new ArrayList<String>());
					details = experts.get(expert.getUri().toString());
					details.add(researcher.getLabel(expert.getUri()));
					details.add(researcher.getImage(expert.getUri()));
					details.add(heuristics.get(count));
					experts.put(expert.getUri().toString(), details);
				}else{
					details = experts.get(expert.getUri().toString());
					details.add(heuristics.get(count));
					experts.put(expert.getUri().toString(), details);
				}
			}
			count++;
		}
		return experts;
	}
	
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#destroy()
	 */
	@Override
	public void destroy() {
		logger.info("RecommendServlet destroy method called.");
		super.destroy();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#finalize()
	 */
	@Override
	protected void finalize() throws Throwable {
		logger.info("RecommendServlet finalize method called.");
		super.finalize();
	}
}
