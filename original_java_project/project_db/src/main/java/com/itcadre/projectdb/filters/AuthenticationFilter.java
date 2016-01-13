package com.itcadre.projectdb.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itcadre.projectdb.controllers.LoginServlet;
import com.itcadre.projectdb.util.Constants;

/**
 * Servlet Filter implementation class AuthenticationFilter
 */
@WebFilter({ "/home", "/", "/contract", "/proposal", "/quote", "/project", "/search" })
public class AuthenticationFilter implements Filter {

	/**
	 * Default constructor.
	 */
	public AuthenticationFilter() {
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			HttpSession session = ((HttpServletRequest) request).getSession();
			Boolean loggedIn = false;
			if (session.getAttribute(Constants.LOGGED_IN) != null) {
				loggedIn = (Boolean) session
						.getAttribute(Constants.LOGGED_IN);
			}

			if (loggedIn) {
				chain.doFilter(request, response);
			} else {
				((HttpServletResponse) response).sendRedirect("./login");
				return;
			}
		} else {
			throw new IOException(
					"This system only responds to properly formed http requests");
		}
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
	}

}
