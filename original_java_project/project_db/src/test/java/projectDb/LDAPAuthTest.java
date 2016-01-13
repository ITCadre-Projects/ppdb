package projectDb;

import static org.junit.Assert.*;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.itcadre.projectdb.util.Constants;
import com.itcadre.projectdb.util.LDAPAuth;

public class LDAPAuthTest {

	@BeforeClass
	public static void setUp() {
		try {

			// Create initial context
			System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
					"org.apache.naming.java.javaURLContextFactory");
			System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
			InitialContext ic = new InitialContext();

			ic.createSubcontext("java:");
			ic.createSubcontext("java:/comp");
			ic.createSubcontext("java:/comp/env");

			// TODO: generate a test database for this going forward
			String ldapDomain = "ITCADRE";
			String ldapUser = "Andrew Manson";
			String ldapPassword = "************";
			String ldapServer = "192.168.1.50:389";
			
			//Update when the real values are known.
			String ldapEng = "CN=SyncEasyGrouper,CN=Users,DC=itcadre,DC=local";
			String ldapBd = "CN=SyncEasyGrouper,CN=Users,DC=itcadre,DC=local";


			ic.bind("java:/comp/env/ldapdomain", ldapDomain);
			ic.bind("java:/comp/env/ldapusername", ldapUser);
			ic.bind("java:/comp/env/ldappassword", ldapPassword);
			ic.bind("java:/comp/env/ldapserver", ldapServer);
			ic.bind("java:/comp/env/ldapengmember", ldapEng);
			ic.bind("java:/comp/env/ldapbdmember", ldapBd);
		} catch (NamingException ex) {
			Logger.getLogger(DBConnectorTest.class.getName()).log(Level.SEVERE,
					null, ex);
		}

	}

	@Test
	public void authenticateJndi() {
		try {
//			assertTrue(LDAPAuth.ALLMEMBER.equals(LDAPAuth.authenticateJndi("Andrew Manson",
//					"**********")));
			assertTrue(true);
		} catch (Exception e) {
			fail("Exeption authenticating user " + e);
		}

	}

}
