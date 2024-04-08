<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>ACF</title>
<link rel="stylesheet" type="text/css" href="style.css" />
</head>
<body>

	<div class="login">
		<h1>Login</h1>
		<%String msg = request.getParameter("msg");
if(msg!=null && !msg.equals(""))
{
	out.println("<h3>"+msg+"</h3>");
}
%>
		<form method="post" action="dologin.jsp">
			<input autocomplete="off" name="username" type="text" name="u"
				placeholder="Username" required="required" /> <input
				autocomplete="off" name="password" type="password" name="p"
				placeholder="Password" required="required" />
			<button type="submit" class="btn btn-primary btn-block btn-large">Let
				me in.</button>
		</form>
	</div>
</body>
</html>