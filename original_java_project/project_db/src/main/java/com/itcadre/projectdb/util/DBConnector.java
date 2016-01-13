package com.itcadre.projectdb.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import javax.naming.InitialContext;
import javax.sql.DataSource;

/**
 * @author andrewmanson
 *
 */
public class DBConnector {
	// TODO: make this something driven by Tomcat config or something
	private static String allAccessDatabaseName = "project_db";
	private static String bdAccessDatabaseName = "project_bd_db";

	private Connection conn = null;
	private Connection bdConn = null;
	private boolean bd = false;

	public void initialize() throws Exception {
		InitialContext cxt = new InitialContext();

		if (cxt == null) {
			throw new Exception("Uh oh -- no context!");
		}

		DataSource ds = (DataSource) cxt.lookup("java:/comp/env/jdbc/"
				+ allAccessDatabaseName);

		DataSource bdDs = (DataSource) cxt.lookup("java:/comp/env/jdbc/"
				+ bdAccessDatabaseName);

		if (ds == null || bdDs == null) {
			throw new Exception("Data source not found!");
		}

		try {
			conn = ds.getConnection();
			bdConn = bdDs.getConnection();
		} catch (SQLException e) {
			System.out.println("Could not get connection from datasource");
		}
	}

	public Connection getConnection() {
		return conn;
	}

	public Connection getBdConnection() {
		return bdConn;
	}

	/**
	 * Add project will add a new project to the projects table. If available
	 * the project summary can be given, if unavailable it can be added ata
	 * later time using the addProjectSummartToProjectMethod
	 * 
	 * @param projectName
	 * @param projectStartDate
	 * @param projectEndDate
	 * @param version
	 * @param projectSummaryName
	 * @return
	 */
	public boolean addProject(String projectName, Date projectStartDate,
			Date projectEndDate, String version, String projectSummaryName) {
		String query = null;

		if (projectSummaryName != null && !"".equals(projectSummaryName.trim())) {
			query = "insert into project(project_name, project_start_date, project_end_date,generic_version, project_summary_name) values('";
			query += projectName + "', '"
					+ new java.sql.Timestamp(projectStartDate.getTime())
					+ "', '" + new java.sql.Timestamp(projectEndDate.getTime())
					+ "', '" + version + "', '" + projectSummaryName + "')";
		} else {
			query = "insert into project(project_name, project_start_date, project_end_date,generic_version) values('";
			query += projectName + "', '"
					+ new java.sql.Timestamp(projectStartDate.getTime())
					+ "', '" + new java.sql.Timestamp(projectEndDate.getTime())
					+ "', '" + version + "')";
		}
		try {
			Statement stmt = conn.createStatement();
			int count = stmt.executeUpdate(query);
			stmt.close();
			if (count != 1) {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	/**
	 * AddProjectSummaryToProject allows for adding the project Summary key to
	 * an existing project once it has been created.
	 * 
	 * @param projectName
	 * @param projectSummaryName
	 * @return
	 */
	public boolean addProjectSummaryToProject(String projectName,
			String projectSummaryName) {
		String query = null;
		try {
			query = "update project set project_summary_name = '"
					+ projectSummaryName + "' where project_name = '"
					+ projectName + "'";

			Statement stmt = conn.createStatement();
			int count = stmt.executeUpdate(query);
			stmt.close();
			if (count != 1) {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	/**
	 * Add project will add a new project summary to the project summary table.
	 * If available the project name can be given, if unavailable it can be
	 * added at a later time using the addProjectSummaryToProjectMethod
	 * 
	 * @param projectName
	 * @param projectStartDate
	 * @param projectEndDate
	 * @param version
	 * @param projectSummaryName
	 * @return
	 */
	public boolean addProjectSummary(String projectSummaryName, String task,
			String goals, String projectName) {
		String query = null;

		query = "insert into project_summary(project_summary_name, project_name, task,goals) values('";
		query += projectSummaryName + "', '" + projectName + "', '" + task
				+ "', '" + goals + "')";

		try {
			Statement stmt = conn.createStatement();
			int count = stmt.executeUpdate(query);
			stmt.close();
			if (count != 1) {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	/**
	 * Project name is required all others are not necessarily. Will add a
	 * client info record associated to a project name to the database.
	 * 
	 * @param projectSummaryName
	 * @param clientName
	 * @param title
	 * @param industry
	 * @param organization
	 * @param clientEmail
	 * @param clientPhoneNumber
	 * @return
	 */
	public boolean addClientInfoToProject(String projectName,
			String clientName, String title, String industry,
			String organization, String clientEmail, String clientPhoneNumber) {
		String query = null;

		query = "insert into client_info(project_name, client_name, title,industry, organization, client_email, client_phone_number) values('";
		query += projectName + "', '" + clientName + "', '" + title + "', '"
				+ industry + "', '" + organization + "', '" + clientEmail
				+ "', '" + clientPhoneNumber + "')";
		try {
			Statement stmt = conn.createStatement();
			int count = stmt.executeUpdate(query);
			stmt.close();
			if (count != 1) {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	/**
	 * Adds a Project lead record to a project
	 * 
	 * @param projectName
	 * @param ProjectLeadName
	 * @return
	 */
	public boolean addProjectLeadToProject(String projectName,
			String projectLeadName) {
		String query = null;

		query = "insert into project_lead(project_name, project_lead_name) values('";
		query += projectName + "', '" + projectLeadName + "')";

		try {
			Statement stmt = conn.createStatement();
			int count = stmt.executeUpdate(query);
			stmt.close();
			if (count != 1) {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	/**
	 * Adds a project problem to a project
	 * 
	 * @param projectName
	 * @param projectProblemDescription
	 * @return
	 */
	public boolean addProjectProblemToProject(String projectName,
			String projectProblemDescription) {
		String query = null;

		query = "insert into project_problem(project_name, project_problem_description) values('";
		query += projectName + "', '" + projectProblemDescription + "')";

		try {
			Statement stmt = conn.createStatement();
			int count = stmt.executeUpdate(query);
			stmt.close();
			if (count != 1) {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	/**
	 * Adds a project result to a project
	 * 
	 * @param projectName
	 * @param textField
	 * @param exemplar
	 * @return
	 */
	public boolean addProjectResultToProject(String projectName,
			String textField, String exemplar) {
		String query = null;

		query = "insert into project_results(project_name, text_field, exemplar) values('";
		query += projectName + "', '" + textField + "', '" + exemplar + "')";

		try {
			Statement stmt = conn.createStatement();
			int count = stmt.executeUpdate(query);
			stmt.close();
			if (count != 1) {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	/**
	 * Adds a Contract record to a project summary record
	 * 
	 * @param projectSummaryName
	 * @param prime
	 * @param contractValue
	 * @param contractStartDate
	 * @param contractEndDate
	 * @param textField
	 * @return
	 */

	public boolean addContractToProjectSummary(String projectSummaryName,
			String prime, String contractValue, Date contractStartDate,
			Date contractEndDate, String textField) {
		String query = null;

		query = "insert into contract(project_summary_name, prime, contract_value, contract_start_date, contract_end_date,text_field) values('";
		query += projectSummaryName + "', '" + prime + "', '" + contractValue
				+ "', '" + new java.sql.Timestamp(contractStartDate.getTime())
				+ "', '" + new java.sql.Timestamp(contractEndDate.getTime())
				+ "', '" + textField + "')";

		try {
			Statement stmt = bdConn.createStatement();
			int count = stmt.executeUpdate(query);
			stmt.close();
			if (count != 1) {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	/**
	 * Adds a quote record to a project summary record
	 * 
	 * @param projectSummaryName
	 * @param quoteClientName
	 * @param quoteDate
	 * @param quoteOrganization
	 * @param quoteText
	 * @return
	 */
	public boolean addQuoteToProjectSummary(String projectSummaryName,
			String quoteClientName, Date quoteDate, String quoteOrganization,
			String quoteText) {
		String query = null;

		query = "insert into project_quote(project_summary_name, quote_client_name, quote_date, quote_organization, quote_text) values('";
		query += projectSummaryName + "', '" + quoteClientName
				+ "', '" + new java.sql.Timestamp(quoteDate.getTime())
				+ "', '" + quoteOrganization
				+ "', '" + quoteText + "')";

		try {
			Statement stmt = bdConn.createStatement();
			int count = stmt.executeUpdate(query);
			stmt.close();
			if (count != 1) {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	/**
	 * Adds a historic proposal record to a project summary
	 * 
	 * @param projectSummaryName
	 * @param proposalName
	 * @param dateSubmitted
	 * @param organizationSubmitted
	 * @param textField
	 * @return
	 */
	public boolean addProposalToProjectSummary(String projectSummaryName,
			String proposalName, Date dateSubmitted,
			String organizationSubmitted, String textField) {
		
		String existingItemQuery = "select version from historical_proposals where project_summary_name = '" + projectSummaryName + "' order by version desc limit 1";
		try {
			//Check to see if an existing version exists if it does increment the version number and 
			//use that to insert the new one.
			Statement versionStmt = bdConn.createStatement();
			ResultSet existingVersions = versionStmt.executeQuery(existingItemQuery);
			int currentVersion = 1;
			if(existingVersions.next()) {
				currentVersion = existingVersions.getInt(1) + 1;
			}
			existingVersions.close();
			versionStmt.close();
			
			String query = null;
	
			query = "insert into historical_proposals(project_summary_name, proposal_name, date_submitted, organization_submitted, version, text_field) values('";
			query += projectSummaryName + "', '" + proposalName
					+ "', '" + new java.sql.Timestamp(dateSubmitted.getTime())
					+ "', '" + organizationSubmitted
					+ "', " + currentVersion
					+ ", '" + textField + "')";

			Statement stmt = bdConn.createStatement();
			int count = stmt.executeUpdate(query);
			stmt.close();
			if (count != 1) {
				return false;
			}
		} catch (SQLException e) {
			return false;
		}

		return true;
	}

	public void closeConnections() {
		try {
			if (conn != null && conn.isClosed() == false) {
				conn.close();
			}

			if (bdConn != null && bdConn.isClosed() == false) {
				bdConn.close();
			}
		} catch (SQLException e) {
			System.out
					.println("Error attempting to close jdbc connection to databases");
		}
	}

	protected void finalize() {
		// avoid connection leaks in the event someone forgets to close
		// properly. Let the garbage
		// collector take a shot at it.

		closeConnections();
	}
}
