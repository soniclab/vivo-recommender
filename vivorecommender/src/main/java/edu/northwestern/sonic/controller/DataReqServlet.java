package edu.northwestern.sonic.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;
import java.util.Map.Entry;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

/**
 * @author anupsawant
 * This servlet produces Json output for the dynamic lists on recommendation page.
 */
public class DataReqServlet extends HttpServlet 
{
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException {

		HttpSession session = req.getSession();
		Map<String,List<String>> experts = (Map<String,List<String>>)session.getAttribute("experts");
	    Set<Entry<String,List<String>>> entrySet = experts.entrySet();
	    Iterator<Entry<String,List<String>>> itr = entrySet.iterator();
		Gson gson = new Gson();
		Entry<String,List<String>> entry = null;
		ExpertDetails expertDetails = null;
		String[] heuristics =  null;
		StringBuffer jsonOut = new StringBuffer("{ types: { \"Faculty Member\" : { " +
				"pluralLabel: \"Faculty Members\" } }, \"items\": [ ");
		while(itr.hasNext()){
			entry = itr.next();
			expertDetails = new ExpertDetails();
			expertDetails.setUri(entry.getKey());
			expertDetails.setLabel(entry.getValue().get(0));
			expertDetails.setImageURL(entry.getValue().get(1));
			heuristics = new String[entry.getValue().size()-2];
			for(int i = 2; i < entry.getValue().size(); i++){
				heuristics[i-2] = entry.getValue().get(i);
			}
			expertDetails.setHeuristics(heuristics);
			jsonOut.append(gson.toJson(expertDetails));
			if(itr.hasNext()){
				jsonOut.append(", ");
			}
		}
		
		jsonOut.append(" ] }");
		
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
		out.print(jsonOut);
		out.flush();
	}
}

class ExpertDetails {
	private String label;
	private String type = "Faculty Member";
	private String imageURL;
    private String uri;
    private String[] heuristics;
	
	public ExpertDetails(){
		super();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String[] getHeuristics() {
		return heuristics;
	}

	public void setHeuristics(String[] heuristics) {
		this.heuristics = heuristics;
	}
}
