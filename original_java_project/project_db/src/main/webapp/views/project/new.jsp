<html>
<head>
<title>New Project</title>
</head>
<body>
	<h1>Project Information</h1>
		<div>
		<p>Enter all the required (*) fields below.</p>			
		<% if( (!"".equals(request.getAttribute("message"))) && request.getAttribute("message") != null){ %>
			<div name="error_message">
				<%= request.getAttribute("message") %>
			</div>
		<% } %>
		<form action="./project" method="post">
		<label for="project_name">Project Name</label> <input type="text"
				name="project_name"><br />
			<input type="submit" name="submit" value="CreateProject"/>
			<br/>
		</form>
	</div>
</body>
</html>
	