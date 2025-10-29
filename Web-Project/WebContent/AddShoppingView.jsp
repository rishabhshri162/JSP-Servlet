<%@page import="com.rays.bean.ShoppingBean"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="Header.jsp"%>

	<%
	String successMsg = (String) request.getAttribute("successMsg");
	String errorMsg = (String) request.getAttribute("errorMsg");
	ShoppingBean bean = (ShoppingBean) request.getAttribute("bean");
	%>

	<div align="center">
		<%
		if (bean != null && bean.getId() > 0) {
		%>
		<h3>Update SHOPPING</h3>
		<%
		} else {
		%>
		<h3>Add Shopping </h3>
		<%
		}
		%>

		<%
		if (successMsg != null) {
		%>
		<h3 style="color: green;"><%=successMsg%></h3>
		<%
		}
		%>

		<%
		if (errorMsg != null) {
		%>
		<h3 style="color: red;"><%=errorMsg%></h3>
		<%
		}
		%>

		<form action="AddShoppingCtl.do" method="post">
			<input type="hidden" name="id"
				value="<%=bean != null ? bean.getId() : ""%>">
			<table>
				<tr>
					<th>Shop Name</th>
					<td><input type="text" name="shopName"
						value="<%=bean != null ? bean.getShopName() : ""%>"
						placeholder="enter shop name"></td>
				</tr>
				<tr>
					<th>product Name</th>
					<td><input type="text" name="productName"
						value="<%=bean != null ? bean.getProductName() : ""%>"
						placeholder="enter product name"></td>
				</tr>
				<tr>
					<th>price</th>
					<td><input type="text" name="price"
						value="<%=bean != null ? bean.getPrice() : ""%>"
						placeholder="enter your price"></td>
				</tr>
				<tr>
					<th></th>
					<td><input type="submit" name="operation"
						value="<%=bean != null ? "update" : "save"%>"></td>
				</tr>
			</table>

		</form>

	</div>
	<%@ include file="Footer.jsp"%>
</body>
</html>