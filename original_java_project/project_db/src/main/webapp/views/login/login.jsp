<html>
<head>
<title>Login To Projects Database</title>
</head>
<body>
	<h1>Login</h1>
	<div>
		<p>To login to the projects database please enter your windows
			login username and password</p>			
		<% if( (!"".equals(request.getAttribute("message"))) && request.getAttribute("message") != null){ %>
			<div name="error_message">
				<%= request.getAttribute("message") %>
			</div>
		<% } %>
		<form action="./login" method="post">
			<label for="username">Username</label> <input type="text"
				name="username"><br /> <br /> <label for="password">Password</label>
			<input type="password" name="password"><br/>
			<input type="submit" name="submit" value="Login"/>
			<br/>
		</form>
	</div>
</body>
</html>