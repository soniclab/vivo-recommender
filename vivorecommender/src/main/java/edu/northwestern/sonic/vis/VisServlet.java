package edu.northwestern.sonic.vis;


import edu.northwestern.sonic.dataaccess.vivo.Researcher;
import edu.northwestern.sonic.model.Recommend;
import edu.northwestern.sonic.model.User;
import edu.northwestern.sonic.network.Network;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.net.URI;
import java.text.DecimalFormat;
import java.util.*;

/**
 * @author Jinling, Anup
 * This servlet helps get data relevant to ego such as his cocitation, citation and authorship network with 
 * identified experts, convert it to json and send it back to view using d3.
 */
@SuppressWarnings("serial")
public class VisServlet extends HttpServlet {
    private static Log logger = LogFactory.getLog(VisServlet.class);
    private String uriPrefix;
    private Network citNet;
    private Network coAuthorNet;
    private Network coCitNet;
    private Recommend recommend;
    private Researcher researcher;
    final static DecimalFormat df = new DecimalFormat("#0.0##");
    
    
    public void init(ServletConfig config) throws ServletException {
    	citNet = new Network(); // citation network
        coAuthorNet = new Network(); // coauthorship network
        coCitNet = new Network(); // cocitation network
        recommend = new Recommend();
        researcher = new Researcher();
	}
 
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
    	 logger.info("start to get data for visualization.");
 
        HttpSession session = req.getSession(false); // get session
        if (session == null) {
            res.sendRedirect(""); 
            return;
        }
        
        User ego = (User) session.getAttribute("ego"); // get the ego
        Set<URI> expertsURI = (Set<URI>) session.getAttribute("expertsURI"); // get all the identified experts
        String researchTopic =   (String)session.getAttribute("researchTopic"); // get the research topic

        expertsURI.add(ego.getUri());
        
        Set<User> users = new LinkedHashSet<User>();

        users.add(ego);
        
        citNet = recommend.getCitation(expertsURI);
        coAuthorNet = recommend.getCoAuthorship(expertsURI);
        coCitNet = recommend.getCoCitation(expertsURI);
        
        // add recommended experts
        for (URI uri : expertsURI) {
            try {
                users.add(researcher.getUser(uri));
            } catch (Exception e) {

            }
        }


        Collection<String> citNetUriStr = citNet.getVertices();

        for (String uriStr : citNetUriStr) {
            try {
                URI uri = new URI(uriStr);
                users.add(researcher.getUser(uri));
            } catch (Exception e) {

            }
        }


        Collection<String> coAuthorNetUriStr = coAuthorNet.getVertices();

        for (String uriStr : coAuthorNetUriStr) {
            try {
                URI uri = new URI(uriStr);
                users.add(researcher.getUser(uri));
            } catch (Exception e) {

            }
        }

        Collection<String> coCitNetUriStr = coCitNet.getVertices();

        for (String uriStr : coCitNetUriStr) {
            try {
                URI uri = new URI(uriStr);
                users.add(researcher.getUser(uri));
            } catch (Exception e) {

            }
        }




        //create json string
        StringBuilder jsonStr = new StringBuilder();
        jsonStr.append("{\"nodes\":[");
        // node names
        List<String> nodes = new ArrayList<String>();
        // node types
        List<String> nodeTypes = new ArrayList<String>();

        List<String> emails = new ArrayList<String>();
       
        List<String> departments = new ArrayList<String>();
        List<String> uris = new ArrayList<String>();
        Map<String, String> nodeSepMap = new HashMap<String, String>();

        int j = 1;
        for (User user : users) {
            if(!uris.contains(user.getUri().toString())) {
            nodes.add(user.getName());
            emails.add(user.getEmail());
            departments.add(user.getDepartment());
            uris.add(user.getUri().toString());

            if (j == 1)
                nodeTypes.add("Requester");
            else if (expertsURI.contains(user.getUri()))  {
                nodeTypes.add("Recommendation");

            }
            else
                nodeTypes.add("Expert");

            nodeSepMap.put(user.getUri().toString(), j + "");
                j++;
            }

        }

        String[] nodesArray = nodes.toArray(new String[nodes.size()]);
        String[] nodeTypesArray = nodeTypes.toArray(new String[nodeTypes.size()]);
        String[] emailsArray = emails.toArray(new String[emails.size()]);
        String[] departmentsArray = departments.toArray(new String[departments.size()]);
         String[] urisArray = uris.toArray(new String[uris.size()]);

        Set<String> nodetypeSet = new LinkedHashSet<String>();
        for (int i = 0; i < nodesArray.length; i++) {
            //add node to jsonStr
            nodetypeSet.add(nodeTypesArray[i]);
            String jname = "{" + "\"name\"" + ":";
            jname = jname + "\"" + nodesArray[i] + "\"" + ",";
            String jnodeType = "\"type\"" + ":";
            jnodeType = jnodeType + "\"" + nodeTypesArray[i] + "\"" + ",";

            String jemail = "\"email\"" + ":";
            jemail = jemail + "\"" + emailsArray[i] + "\"" + ",";
            String juri = "\"uri\"" + ":";
            juri = juri + "\"" + urisArray[i] + "\"" + ",";


            String jdepartment = "\"department\"" + ":";
            jdepartment = jdepartment + "\"" + departmentsArray[i] + "\"";


            // need to add node urlInfo
            String nodeinfos = jname + jnodeType + jemail + juri + jdepartment + "},";
            jsonStr.append(nodeinfos);


        }
        jsonStr.deleteCharAt(jsonStr.length() - 1);
        jsonStr.append("],\"links\": [");


        // Edges
        Set<String> edges = new HashSet<String>();
        String DEL = " ";
        Collection<String> citNetEdges = citNet.getEdges();
        if (citNetEdges != null && citNetEdges.size() > 0) {
            for (String edgeStr : citNetEdges) {
                String fnode = edgeStr.split(DEL)[0];
                String tnode = edgeStr.split(DEL)[1];
                if (nodeSepMap.keySet().contains(fnode) && nodeSepMap.keySet().contains(tnode) && !(fnode.equalsIgnoreCase(tnode))) {
                    String edgePair = nodeSepMap.get(fnode) + "-" + nodeSepMap.get(tnode);

                    edges.add(edgePair);
                }

            }
        }

        Collection<String> coAuthorNetEdges = coAuthorNet.getEdges();
        if (coAuthorNetEdges != null && coAuthorNetEdges.size() > 0) {
            for (String edgeStr : coAuthorNetEdges) {
                String fnode = edgeStr.split(DEL)[0];
                String tnode = edgeStr.split(DEL)[1];
                if (nodeSepMap.keySet().contains(fnode) && nodeSepMap.keySet().contains(tnode) && !(fnode.equalsIgnoreCase(tnode))) {
                    String edgePair = nodeSepMap.get(fnode) + "-" + nodeSepMap.get(tnode);

                    edges.add(edgePair);
                }

            }
        }

        Collection<String> coCitNetEdges = coCitNet.getEdges();
        if (coCitNetEdges != null && coCitNetEdges.size() > 0) {
            for (String edgeStr : coCitNetEdges) {
                String fnode = edgeStr.split(DEL)[0];
                String tnode = edgeStr.split(DEL)[1];
                if (nodeSepMap.keySet().contains(fnode) && nodeSepMap.keySet().contains(tnode) && !(fnode.equalsIgnoreCase(tnode))) {
                    String edgePair = nodeSepMap.get(fnode) + "-" + nodeSepMap.get(tnode);

                    edges.add(edgePair);
                }

            }
        }

        //add edge to jsonStr
        
        if(edges != null && edges.size() > 0 ) {
        	for (String edgepair : edges) {
           
            String jedgeSource = "{" + "\"source\"" + ":";
            String jedgeTarget = "\"target\"" + ":";
            String[] edgepairArray = edgepair.split("-");
            Long sIndex = Long.valueOf(edgepairArray[0]) - 1;
            Long tIndex = Long.valueOf(edgepairArray[1]) - 1;

            jedgeSource = jedgeSource + sIndex + ",";
            jedgeTarget = jedgeTarget + tIndex;
            
            String edgeinfos = jedgeSource + jedgeTarget  + "},";
            jsonStr.append(edgeinfos);
        }

        jsonStr.deleteCharAt(jsonStr.length() - 1);
        jsonStr.append("],\"others\": [");

        String jnode_prefix = "{" + "\"node_prefix\"" + ":";
        String jkeyword = "\"keyword\"" + ":";

        jnode_prefix = jnode_prefix + "\"" + uriPrefix + "\"" + ",";
        jkeyword = jkeyword + "\"" + researchTopic + "\"";
        String otherinfos = jnode_prefix + jkeyword + "},";
        jsonStr.append(otherinfos);

        jsonStr.deleteCharAt(jsonStr.length() - 1);
        jsonStr.append("],\"nodelegend\": [");

        for (String nodelegend : nodetypeSet) {
            String jlegendtype = "{" + "\"type\"" + ":";
            jlegendtype = jlegendtype + "\"" + nodelegend + "\"" + "},";

            jsonStr.append(jlegendtype);
        }

       
        jsonStr.deleteCharAt(jsonStr.length() - 1);
        jsonStr.append("]");
        jsonStr.append("}");


    }
        res.setContentType("application/json");
        res.setCharacterEncoding("UTF-8");

        res.getWriter().write(jsonStr.toString());
    

    }
}


