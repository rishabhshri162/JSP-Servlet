<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Change Password</title>
</head>
<body>
	<%
		String errorMsg = (String) request.getAttribute("errorMsg");
		String successMsg = (String) request.getAttribute("successMsg");
	%>

	<%@ include file="Header.jsp"%>

	<h2 align="center">Change Password</h2>

	<%
		if (successMsg != null) {
	%>
	<p align="center" style="color: green;"><%=successMsg%></p>
	<%
		}

		if (errorMsg != null) {
	%>
	<p align="center" style="color: red;"><%=errorMsg%></p>
	<%
		}
	%>


	<form action="ChangePasswordCtl" method="post" align="center">
		<table align="center">
			<tr>
				<td>Old Password:</td>
				<td><input type="password" name="oldPassword" required></td>
			</tr>
			<tr>
				<td>New Password:</td>
				<td><input type="password" name="newPassword" required></td>
			</tr>
			<tr>
				<td>Confirm Password:</td>
				<td><input type="password" name="confirmPassword" required></td>
			</tr>
			<tr>
			<th></th>
				<td align="center"><input type="submit" value="Change Password"></td>
			</tr>
		</table>

	</form>

	<%@ include file="Footer.jsp"%>

</body>
</html>