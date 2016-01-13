package com.itcadre.projectdb.util;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.directory.Attribute;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;

public class LDAPAuth {

	

	public static String authenticateJndi(String username, String password)
			throws Exception {

		InitialContext cxt = new InitialContext();
		String domain = (String) cxt.lookup("java:/comp/env/ldapdomain");
		String serverUrl = (String) cxt.lookup("java:/comp/env/ldapserver");
		String systemUsername = (String) cxt
				.lookup("java:/comp/env/ldapusername");
		String systemPassword = (String) cxt
				.lookup("java:/comp/env/ldappassword");
		String engMemberString = (String) cxt
				.lookup("java:/comp/env/ldapengmember");
		String bdMemberString = (String) cxt
				.lookup("java:/comp/env/ldapbdmember");
		
		Properties props = new Properties();
		props.put(Context.INITIAL_CONTEXT_FACTORY,
				"com.sun.jndi.ldap.LdapCtxFactory");
		props.put(Context.PROVIDER_URL, "ldap://" + serverUrl);
		props.put(Context.SECURITY_AUTHENTICATION, "simple");
		props.put(Context.SECURITY_PROTOCOL, "simple");
		props.put(Context.SECURITY_PRINCIPAL, domain + "\\" + systemUsername);
		props.put(Context.SECURITY_CREDENTIALS, systemPassword);

		InitialDirContext systemContext = new InitialDirContext(props);
		try {
			SearchControls ctrls = new SearchControls();
			ctrls.setReturningAttributes(new String[] { "givenName", "sn",
					"memberOf", "sAMAccountName" });
			ctrls.setSearchScope(SearchControls.SUBTREE_SCOPE);

			NamingEnumeration<javax.naming.directory.SearchResult> answers = systemContext
					.search("dc=itcadre, dc=local", "(cn=" + username + ")",
							ctrls);
			javax.naming.directory.SearchResult result = answers.nextElement();
			if (result != null) {
				try {
					
					props = new Properties();
					props.put(Context.INITIAL_CONTEXT_FACTORY,
							"com.sun.jndi.ldap.LdapCtxFactory");
					props.put(Context.PROVIDER_URL, "ldap://" + serverUrl);
					props.put(Context.SECURITY_AUTHENTICATION, "simple");
					props.put(Context.SECURITY_PRINCIPAL, domain + "\\"
							+ username);
					props.put(Context.SECURITY_CREDENTIALS, password);
					InitialDirContext context = new InitialDirContext(props);
					
					
					Attribute membership = result.getAttributes().get("memberOf");
					boolean eng = false;
					boolean bd = false;
					
					if(membership.contains(engMemberString)) {
						eng = true;
					}
					
					if(membership.contains(bdMemberString)) {
						bd = true;
					}
					
					context.close();
					if(bd && eng)
						return Constants.ALLMEMBER;
					else if (bd)
						return Constants.BDMEMBER;
					else if (eng)
						return Constants.ENGMEMBER;
					else
						return  Constants.UNAUTH;
				} catch (Exception e) {
					return Constants.BAD_LOGIN;
				}
				
				//TODO expand this out based on further info
			} else {
				return Constants.BAD_LOGIN;
			}
		} finally {
			systemContext.close();
		}
	}
}
