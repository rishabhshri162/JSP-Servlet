<%@page import="com.rays.bean.UserBean"%>
<%@page import="org.apache.jasper.compiler.Node.UseBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
		UserBean user = (UserBean) session.getAttribute("user");
	%>

	<%
		if (user != null) {
	%>
	<h3><%="Hi, " + user.getFirstName()+ " " + user.getLastName()%></h3>
	<%
		} else {
	%>
	<h3>Hi, Guest</h3>
	<%
		}
	%>

	<%
	if (user != null) {
	%>
	<a href="MyProfileCtl">My Profile</a> |
	<a href="ShoppingListCtl.do">Shopping List</a> |
	<a href="UserCtl.do">Add User</a> |
	<a href="AddShoppingCtl">Add shopping</a> |
	<a href="UserListCtl.do">User List</a> |
	<a href="ChangePasswordCtl">Change Password</a> |
	<a href="LoginCtl?operation=logout">Logout</a>
	<%
	} else {
	%>
	<a href="LoginCtl">Login</a> |
	<a href="UserRegistrationCtl">SignUp</a>
	<%
	}
	%>



	<hr>

</body>
</html>