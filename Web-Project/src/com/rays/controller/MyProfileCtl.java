package com.rays.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rays.bean.UserBean;
import com.rays.model.UserModel;

@WebServlet("/MyProfileCtl")
public class MyProfileCtl extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");

		if (user == null) {
			request.setAttribute("errorMsg", "Session expired. Please login again.");
			RequestDispatcher rd = request.getRequestDispatcher("LoginView.jsp");
			rd.forward(request, response);
		}

		request.setAttribute("bean", user);
		RequestDispatcher rd = request.getRequestDispatcher("MyProfile.jsp");
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		HttpSession session = request.getSession();

		UserBean bean = new UserBean();
		UserModel model = new UserModel();

		try {
			bean.setFirstName(request.getParameter("firstName"));
			bean.setLastName(request.getParameter("lastName"));
			bean.setLogin(request.getParameter("login"));
			bean.setPassword(request.getParameter("password"));
			bean.setDob(sdf.parse(request.getParameter("dob")));
			bean.setId(Integer.parseInt(request.getParameter("id")));

			model.update(bean);
			session.setAttribute("user", bean);

			request.setAttribute("successMsg", "User updated successfully!");

		} catch (Exception e) {

			request.setAttribute("errorMsg", e.getMessage());

			e.printStackTrace();
		}
		request.setAttribute("bean", bean);
		RequestDispatcher rd = request.getRequestDispatcher("MyProfile.jsp");
		rd.forward(request, response);
	}

}
