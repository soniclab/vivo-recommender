package edu.northwestern.sonic.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;

import com.hp.hpl.jena.rdf.model.Model;

import edu.northwestern.sonic.model.User;
import edu.northwestern.sonic.persistence.JavatoRDF;
import edu.northwestern.sonic.persistence.TDBTransaction;

/**
 * @author Anup
 * Servlet to Logout the User.
 * Saves ego state with preferences and invalidates the session
 */
public class LogoutServlet extends HttpServlet 
{
   private Logger logger = Logger.getLogger(this.getClass());
   
   protected void doGet(HttpServletRequest req, HttpServletResponse resp) 
   throws ServletException, IOException {
      logger.debug("doGet()");
      
      HttpSession session = req.getSession();
      TDBTransaction tdbTransaction = new TDBTransaction();
      User ego = (User) session.getAttribute("ego");
      
      /* Write ego state to TDB */
      Model m = JavatoRDF.write(ego);
      tdbTransaction.updateState(m);
      m.close();
      
      /* Invalidate the session and bring user to homepage */
      session.invalidate();
      String url = "index.jsp";
      resp.sendRedirect(url);
   }
}
