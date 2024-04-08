<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ page import="com.rec.*" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%

String user = request.getParameter("username");
String password = request.getParameter("password");

if(user==null || user.equals("") || password==null || password.equals("")){
	response.sendRedirect("/ACFAdmin/index.jsp?msg=Invalid Username Or password");
}
if(user.equals(CreateReceipt.p.get("u")) && password.equals(CreateReceipt.p.get("p"))){
	session.setAttribute("user", user);
	response.sendRedirect("/ACFAdmin/createReceipt.jsp");
}
else{
	response.sendRedirect("/ACFAdmin/index.jsp?msg=invalid credentials");
}
%>