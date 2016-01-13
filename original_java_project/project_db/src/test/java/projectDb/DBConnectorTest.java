package projectDb;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.junit.BeforeClass;
import org.junit.Test;
import org.postgresql.ds.PGPoolingDataSource;

import com.itcadre.projectdb.util.DBConnector;

public class DBConnectorTest {

	public static String PROJECT_SUMMARY_NAME = "Test Project Summary";
	public static String PROJECT_NAME = "Test Project";

	@BeforeClass
	public static void setUp() throws Exception {
		try {
			// Create initial context
			System.setProperty(Context.INITIAL_CONTEXT_FACTORY,
					"org.apache.naming.java.javaURLContextFactory");
			System.setProperty(Context.URL_PKG_PREFIXES, "org.apache.naming");
			InitialContext ic = new InitialContext();

			ic.createSubcontext("java:");
			ic.createSubcontext("java:/comp");
			ic.createSubcontext("java:/comp/env");
			ic.createSubcontext("java:/comp/env/jdbc");

			// TODO: generate a test database for this going forward
			PGPoolingDataSource source = new PGPoolingDataSource();
			source.setDataSourceName("AllUsers");
			source.setServerName("localhost");
			source.setDatabaseName("project_db");
			source.setUser("all_user");
			source.setPassword("postgres");
			source.setMaxConnections(10);

			PGPoolingDataSource bd_source = new PGPoolingDataSource();
			bd_source.setDataSourceName("BDUser");
			bd_source.setServerName("localhost");
			bd_source.setDatabaseName("project_bd_db");
			bd_source.setUser("bd_user");
			bd_source.setPassword("postgres");
			bd_source.setMaxConnections(10);

			ic.bind("java:/comp/env/jdbc/project_db", source);
			ic.bind("java:/comp/env/jdbc/project_bd_db", bd_source);
		} catch (NamingException ex) {
			Logger.getLogger(DBConnectorTest.class.getName()).log(Level.SEVERE,
					null, ex);
		}
	}

	/**
	 * Clear DB will clean out the database of existing records. Due to cascade
	 * constraints should only need to clear the projects and project summary
	 * tables
	 * 
	 * @param conn
	 * @throws SQLException
	 */
	public void clearDB(DBConnector connector) throws SQLException {
		Connection conn = connector.getConnection();

		Statement stat = conn.createStatement();
		stat.executeUpdate("Delete from project");
		stat.executeUpdate("Delete from project_summary");
		stat.close();

		conn = connector.getBdConnection();
		stat = conn.createStatement();
		stat.executeUpdate("Delete from project_quote");
		stat.executeUpdate("Delete from historical_proposals");
		stat.executeUpdate("Delete from contract");
		stat.close();
	}

	@Test
	public void testInitialize() {
		try {

			DBConnector connector = new DBConnector();

			connector.initialize();
			Connection conn = connector.getConnection();
			Connection bdConn = connector.getConnection();
			assertNotNull(conn);
			assertNotNull(bdConn);

			connector.closeConnections();

		} catch (Exception e) {
			fail("Exception while initializing connector");
		}
	}

	@Test
	public void testAddProject() {
		try {

			DBConnector connector = new DBConnector();

			connector.initialize();
			Connection conn = connector.getConnection();
			assertNotNull(conn);

			clearDB(connector);

			assertTrue(connector.addProject(PROJECT_NAME, new Date(),
					new Date(), "blah", PROJECT_NAME));

			Statement statement = conn.createStatement();
			ResultSet set = statement
					.executeQuery("Select project_name from project where project_name = 'Test Project'");

			assertTrue(set.next());
			set.close();
			statement.close();

			connector.closeConnections();
		} catch (Exception e) {
			fail("Exception while testing addProject");
		}
	}

	@Test
	public void testAddProjectSummaryToProject() {
		try {
			DBConnector connector = new DBConnector();

			connector.initialize();
			Connection conn = connector.getConnection();
			assertNotNull(conn);

			clearDB(connector);

			assertTrue(connector.addProject(PROJECT_NAME, new Date(),
					new Date(), "blah", null));
			assertTrue(connector.addProjectSummary(PROJECT_SUMMARY_NAME,
					"To find resolution for their issues", "Succeed",
					PROJECT_NAME));
			assertTrue(connector.addProjectSummaryToProject(PROJECT_NAME,
					PROJECT_SUMMARY_NAME));

			Statement statement = conn.createStatement();
			ResultSet set = statement
					.executeQuery("Select project_summary_name from project where project_summary_name = 'Test Project Summary'");

			assertTrue(set.next());
			set.close();
			statement.close();

			connector.closeConnections();
		} catch (Exception e) {
			fail("Exception while testing addProjectSummary");
		}
	}

	@Test
	public void testAddProjectSummary() {
		try {
			DBConnector connector = new DBConnector();

			connector.initialize();
			Connection conn = connector.getConnection();
			assertNotNull(conn);

			clearDB(connector);

			assertTrue(connector.addProject(PROJECT_NAME, new Date(),
					new Date(), "blah", null));
			assertTrue(connector.addProjectSummary(PROJECT_SUMMARY_NAME,
					"To find resolution for their issues", "Succeed",
					PROJECT_NAME));

			Statement statement = conn.createStatement();
			ResultSet set = statement
					.executeQuery("Select * from project_summary where project_summary_name = 'Test Project Summary'");

			assertTrue(set.next());
			set.close();
			statement.close();

			connector.closeConnections();
		} catch (Exception e) {
			fail("Exception while testing addProjectSummary");
		}
	}

	@Test
	public void testAddClientInfoToProject() {
		try {
			DBConnector connector = new DBConnector();

			connector.initialize();
			Connection conn = connector.getConnection();
			assertNotNull(conn);

			clearDB(connector);

			assertTrue(connector.addProject(PROJECT_NAME, new Date(),
					new Date(), "blah", null));

			assertTrue(connector.addClientInfoToProject(PROJECT_NAME, "Client",
					"Manager", "Tech", "Headquarters", "client@example.com",
					"5082658356"));

			Statement statement = conn.createStatement();
			ResultSet set = statement
					.executeQuery("Select * from client_info where project_name = 'Test Project'");

			assertTrue(set.next());
			set.close();
			statement.close();

			connector.closeConnections();
		} catch (Exception e) {
			fail("Exception while testing addClientInfoToProject");
		}
	}

	@Test
	public void testAddProjectLeadToProject() {
		try {
			DBConnector connector = new DBConnector();

			connector.initialize();
			Connection conn = connector.getConnection();
			assertNotNull(conn);

			clearDB(connector);

			assertTrue(connector.addProject(PROJECT_NAME, new Date(),
					new Date(), "blah", null));

			assertTrue(connector.addProjectLeadToProject(PROJECT_NAME,
					"Project Lead"));

			Statement statement = conn.createStatement();
			ResultSet set = statement
					.executeQuery("Select * from project_lead where project_name = 'Test Project'");

			assertTrue(set.next());
			set.close();
			statement.close();

			connector.closeConnections();
		} catch (Exception e) {
			fail("Exception while testing addClientInfoToProject");
		}
	}

	@Test
	public void testAddProjectProblemToProject() {
		try {
			DBConnector connector = new DBConnector();

			connector.initialize();
			Connection conn = connector.getConnection();
			assertNotNull(conn);

			clearDB(connector);

			assertTrue(connector.addProject(PROJECT_NAME, new Date(),
					new Date(), "blah", null));

			assertTrue(connector.addProjectProblemToProject(PROJECT_NAME,
					"A Problem"));

			Statement statement = conn.createStatement();
			ResultSet set = statement
					.executeQuery("Select * from project_problem where project_name = 'Test Project'");

			assertTrue(set.next());
			set.close();
			statement.close();

			connector.closeConnections();
		} catch (Exception e) {
			fail("Exception while testing addClientInfoToProject");
		}
	}

	@Test
	public void testAddProjectResultToProject() {

		// TODO: this needs to be updated once the concept of what a result is
		// is updated.
		// Likely going to be adding name, extension, file type kind of fields
		// to make sure
		// that the application will open correctly when retrieved.
		// Exemplar might also change to being a boolean

		try {
			DBConnector connector = new DBConnector();

			connector.initialize();
			Connection conn = connector.getConnection();
			assertNotNull(conn);

			clearDB(connector);

			assertTrue(connector.addProject(PROJECT_NAME, new Date(),
					new Date(), "blah", null));

			assertTrue(connector.addProjectResultToProject(PROJECT_NAME,
					"gngnerignerignierngeirn", "klrngkoerng"));

			Statement statement = conn.createStatement();
			ResultSet set = statement
					.executeQuery("Select * from project_results where project_name = 'Test Project'");

			assertTrue(set.next());
			set.close();
			statement.close();

			connector.closeConnections();
		} catch (Exception e) {
			fail("Exception while testing addClientInfoToProject");
		}
	}

	@Test
	public void testAddContractToProjectSummary() {
		try {
			DBConnector connector = new DBConnector();

			connector.initialize();
			Connection bdConn = connector.getBdConnection();
			assertNotNull(bdConn);

			clearDB(connector);

			assertTrue(connector.addProject(PROJECT_NAME, new Date(),
					new Date(), "blah", null));

			assertTrue(connector.addProjectSummary(PROJECT_SUMMARY_NAME,
					"To find resolution for their issues", "Succeed",
					PROJECT_NAME));

			assertTrue(connector.addContractToProjectSummary(
					PROJECT_SUMMARY_NAME, "We are the prime", "100000",
					new Date(), new Date(), "Some extra description"));

			Statement statement = bdConn.createStatement();
			ResultSet set = statement
					.executeQuery("Select * from contract where project_summary_name = 'Test Project Summary'");

			assertTrue(set.next());
			set.close();
			statement.close();

			connector.closeConnections();
		} catch (Exception e) {
			fail("Exception while testing addClientInfoToProject");
		}
	}

	@Test
	public void testAddQuoteToProjectSummary() {
		try {
			DBConnector connector = new DBConnector();

			connector.initialize();
			Connection bdConn = connector.getBdConnection();
			assertNotNull(bdConn);

			clearDB(connector);

			assertTrue(connector.addProject(PROJECT_NAME, new Date(),
					new Date(), "blah", null));

			assertTrue(connector.addProjectSummary(PROJECT_SUMMARY_NAME,
					"To find resolution for their issues", "Succeed",
					PROJECT_NAME));

			assertTrue(connector.addQuoteToProjectSummary(PROJECT_SUMMARY_NAME,
					"Some client", new Date(), "Headquarters",
					"They did good work"));

			Statement statement = bdConn.createStatement();
			ResultSet set = statement
					.executeQuery("Select * from project_quote where project_summary_name = 'Test Project Summary'");

			assertTrue(set.next());
			set.close();
			statement.close();

			connector.closeConnections();
		} catch (Exception e) {
			fail("Exception while testing addClientInfoToProject");
		}
	}

	@Test
	public void testAddProposalToProjectSummary() {
		try {
			DBConnector connector = new DBConnector();

			connector.initialize();
			Connection bdConn = connector.getBdConnection();
			assertNotNull(bdConn);

			clearDB(connector);

			assertTrue(connector.addProject(PROJECT_NAME, new Date(),
					new Date(), "blah", null));

			assertTrue(connector.addProjectSummary(PROJECT_SUMMARY_NAME,
					"To find resolution for their issues", "Succeed",
					PROJECT_NAME));

			//TODO Call will need to be updated at some point as their could be multiple proposals. 
			assertTrue(connector.addProposalToProjectSummary(PROJECT_SUMMARY_NAME, "proposal_name", new Date(), "Headquarters", "kgdegkjdenkjgne"));
			assertTrue(connector.addProposalToProjectSummary(PROJECT_SUMMARY_NAME, "proposal_name", new Date(), "Headquarters", "gdfgdfghdfhsghfg"));
			Statement statement = bdConn.createStatement();
			ResultSet set = statement
					.executeQuery("Select * from historical_proposals where project_summary_name = 'Test Project Summary' order by version asc");

			assertTrue("Neither proposals successfully added",set.next());
			
			int version = set.getInt(5);
			
			assertTrue("Second proposal was not added",set.next());
			
			int secondVersion = set.getInt(5);
			
			
			assertTrue("Expected version incremented by 1 expected " + (version+1) + " got " + secondVersion, version + 1 == secondVersion);
			
			
			set.close();
			statement.close();

			connector.closeConnections();
		} catch (Exception e) {
			fail("Exception while testing addClientInfoToProject");
		}
	}
}
