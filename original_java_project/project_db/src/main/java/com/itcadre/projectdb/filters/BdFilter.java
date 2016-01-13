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
import com.itcadre.projectdb.util.LDAPAuth;

/**
 * Servlet Filter implementation class BdFilter
 */
@WebFilter({"/contract", "/proposal", "/quote"})
public class BdFilter implements Filter {

    /**
     * Default constructor. 
     */
    public BdFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		if (request instanceof HttpServletRequest) {
			HttpSession session = ((HttpServletRequest) request).getSession();
			String userRights = null;
			if (session.getAttribute(Constants.USERRIGHTS) != null) {
				userRights = (String) session
						.getAttribute(Constants.USERRIGHTS);
			}

			if (Constants.ALLMEMBER.equals(userRights)
					|| Constants.BDMEMBER.equals(userRights)) {
				chain.doFilter(request, response);
			} else {
				((HttpServletResponse) response).sendRedirect("./home");
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
		// TODO Auto-generated method stub
	}

}
