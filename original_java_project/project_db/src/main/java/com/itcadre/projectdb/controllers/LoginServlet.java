package com.itcadre.projectdb.controllers;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itcadre.projectdb.util.Constants;
import com.itcadre.projectdb.util.LDAPAuth;

/**
 * Servlet Used to manage login. If the user is not logged in presents the user
 * with a login page. If the user is logged in forwards them to the root
 * Servlet.
 */
@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String ACTION="action";
	private static final String LOGOUT_ACTION="destroy";
	

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		if(LOGOUT_ACTION.equals(request.getParameter(ACTION))){
			HttpSession session = request.getSession();
			session.invalidate();
		}
		handleRequest(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		handleRequest(request, response);
	}
	
	private void handleRequest(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException{
		
		HttpSession session = request.getSession();
		Object loggedIn = session.getAttribute(Constants.LOGGED_IN);
		String username = request.getParameter(Constants.USERNAME);
		String password = request.getParameter("password");

		if (loggedIn != null && ((Boolean) loggedIn) == true) {			
			response.sendRedirect("./home");
		} else if (username == null || password == null
				|| "".equals(username.trim()) || "".equals(password.trim())) {
			// Show the user the login page.
			request.getRequestDispatcher("/views/login/login.jsp").forward(request,
					response);
		} else {
			// Run them through the login process.
			String message = null;
			String loggedInType = "";
			boolean loggedin = false;

			try {
				loggedInType = LDAPAuth.authenticateJndi(username.trim(),
						password);
				if (Constants.ALLMEMBER.equals(loggedInType)
						|| Constants.BDMEMBER.equals(loggedInType)
						|| Constants.ENGMEMBER.equals(loggedInType)) {
					loggedin = true;
				}
			} catch (Exception e) {
				loggedin = false;
			}
			
			if (loggedin) {
				session.setAttribute(Constants.LOGGED_IN, true);
				session.setAttribute(Constants.USERNAME, username);
				session.setAttribute(Constants.USERRIGHTS, loggedInType);
				message="You have successfully logged in" ;
				response.sendRedirect("./home");
			} else {
				if(Constants.ERROR.equals(loggedInType)){
					message="There was an error logging your user into the proects database.  Please contact the domain administrator if this error persists.";
				} else if(Constants.UNAUTH.equals(loggedInType)){
					message="You do not have permission to log into this application please see the Domain Administrator to gain access.";
				} else if(Constants.BAD_LOGIN.equals(loggedInType)){
					message="Please recheck your username and password as the given credentials could not be verified.";
				}
				
				request.setAttribute("message", message);
				request.getRequestDispatcher("/views/login/login.jsp").forward(request,
						response);
			}
		}
	}
}
