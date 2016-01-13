<html>
<%@ page import="com.itcadre.projectdb.util.Constants" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<% String userRights = (String) request.getSession().getAttribute(Constants.USERRIGHTS);
boolean isBd = false;
if( Constants.ALLMEMBER.equals(userRights) ||
		Constants.BDMEMBER.equals(userRights)) {
	isBd = true;
}%>
<head>
<title>Main Menu</title>

<script src='<c:url value="/javascript/jquery-2.1.3.min.js"/>' type="text/javascript" ></script>
<script src='<c:url value="/javascript/root/root.js"/>' type="text/javascript"></script>
</head>
<body>
	<div id="main_menu">
		<h1>Past Performance DB</h1>
		<div>
			<p>Please select from the menu below.</p>
			<a href="./search" id="search_projects_link">Search Projects</a><br/>
			<a href="./search?all=true" id="view_projects_link">View Projects</a><br/>
			<a href="./project?action=new" id="new_project_link">New Project</a><br/>
			<%if(isBd){ %>
				<a href="javascript:void(0)" id="documents_link">Add Documents to Projects</a>
			<% } %>
		</div>
	</div>
	<%if(isBd){ %>
		<h1>Add Documents to Existing Projects</h1>
		<div id="add_documents_to_project" style="display:none">
			<a href="./proposal?action=new" id="new_proposal_link">New Proposal</a><br/>
			<a href="./quote?action=new"id="new_quote_link">New Quote</a><br/>
			<a href="./contract?action=new" id="new_contract_info_link">New Contract</a><br/>
			<a href="javascript:void(0)" id="add_document_to_main_link">Back To Main Menu</a><br/>
		</div>
	<% } %>
	<div id="logout_footer">
		<a href="./login?action=destroy" id="logout_link">Logout</a><br/>
	</div>
</body>
</html>