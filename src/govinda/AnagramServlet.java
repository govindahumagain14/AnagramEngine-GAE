package govinda;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.jdo.PersistenceManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.users.User;
import com.google.appengine.api.users.UserService;
import com.google.appengine.api.users.UserServiceFactory;


@SuppressWarnings("serial")
public class AnagramServlet extends HttpServlet {


	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		// set the response type to be html text
		// we are outputting html
		resp.setContentType("text/html");
		
		// access to the google user service
		UserService us = UserServiceFactory.getUserService();
		User u = us.getCurrentUser();
		
		req.setAttribute("user", u);
		
		PersistenceManager pm = null;
		ArrayList<String> wordlist = new ArrayList<>();
		Anagramwordlist awl = null;
		
		String userinput= "";
		try
		{
			userinput = (req.getParameter("searchword")).toString();
		}
		catch(NullPointerException e){
			resp.sendRedirect("/");
		}

		char[] arr = userinput.toCharArray();
		Arrays.sort(arr);
		userinput = new String(arr);

		if(userinput.length() > 0) 
		{
			Key user_key = KeyFactory.createKey("Anagramwordlist", userinput);
			pm = PMF.get().getPersistenceManager();
			try 
			{
				awl = pm.getObjectById(Anagramwordlist.class, user_key);
				wordlist=awl.getAnagramwordlist(user_key);
				req.setAttribute("output", "Anagram word is found.");
				req.setAttribute("list", wordlist);
				pm.close();
			}
				catch(Exception e) {
					req.setAttribute("output", "No Anagram Found for Input");
				}
			
		}else {
			req.setAttribute("output", "Invalid Anagram Search ");
		}

		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/root.jsp");
		rd.forward(req, resp);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws
	IOException, ServletException 
	{
		UserService us = UserServiceFactory.getUserService();
		User u = us.getCurrentUser();
		req.setAttribute("user", u);
		
		PersistenceManager pm = null;

		String userinput= null;	
		try{
			userinput = (req.getParameter("addword")).toString();
		}
		catch(NullPointerException e){
			
		}

		char[] arr = userinput.toCharArray();
		Arrays.sort(arr);
		userinput = new String(arr);
		
		Anagramwordlist awl=null;
		
		
		if(userinput.length() > 0) 
		{
			Key user_key = KeyFactory.createKey("Anagramwordlist", userinput);
			pm = PMF.get().getPersistenceManager();
			try 
			{
				awl = pm.getObjectById(Anagramwordlist.class, user_key);
				
				if (awl.addWord(userinput)){
					awl.addWord(req.getParameter("addword"));
					pm.makePersistent(awl);
				req.setAttribute("output", " Anagram is added to the list : " + userinput);
			}else {
				req.setAttribute("output", "Anagram is already on the list : " + userinput );
				}
			}
			catch(Exception e) 
			{
				//Create key if not exist
				awl = new Anagramwordlist(user_key);
				awl.addWord(req.getParameter("addword"));
				pm.makePersistent(awl);
				req.setAttribute("output", "New Anagram is created : " + userinput);
			}
			pm.close();
		}else {
			req.setAttribute("output", "Invalid Anagram Add");
		}

		RequestDispatcher rd = req.getRequestDispatcher("/WEB-INF/root.jsp");
		rd.forward(req, resp);
	}

}