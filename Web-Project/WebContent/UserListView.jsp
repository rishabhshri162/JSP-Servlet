<%@page import="java.util.List"%>
<%@page import="com.rays.bean.UserBean"%>
<%@page import="java.util.Iterator"%>
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
	List list = (List) request.getAttribute("list");
	%>

	<%@ include file="Header.jsp"%>
	<div align="center">

		<h3>User List</h3>

		<form action="UserListCtl" method="post">

			<table border="1px" width="100%">

				<tr style="background-color: skyblue">
					<th>Id</th>
					<th>First Name</th>
					<th>Last Name</th>
					<th>Login</th>
					<th>DOB</th>
				</tr>

				<%
				Iterator<UserBean> it = list.iterator();
				%>

				<%
				while (it.hasNext()) {
					UserBean bean = it.next();
				%>
				<tr align="center" style="background-color: #D3D3D3">
					<td><%=bean.getId()%></td>
					<td><%=bean.getFirstname()%></td>
					<td><%=bean.getLastname()%></td>
					<td><%=bean.getLogin()%></td>
					<td><%=bean.getDob()%></td>

				</tr>
				<%
				}
				%>



			</table>

		</form>
	</div>
	<%@ include file="Footer.jsp"%>
</body>
</html>