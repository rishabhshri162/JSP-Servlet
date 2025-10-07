package com.rays.controller;

import java.io.IOException;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import com.rays.bean.UserBean;
import com.rays.model.UserModel;

@WebServlet("/ChangePasswordCtl")
public class ChangePasswordCtl extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {


		response.sendRedirect("ChangePasswordView.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String oldPassword = request.getParameter("oldPassword");
		String newPassword = request.getParameter("newPassword");
		String confirmPassword = request.getParameter("confirmPassword");

		HttpSession session = request.getSession();
		UserBean user = (UserBean) session.getAttribute("user");

		if (user == null) {
			request.setAttribute("errorMsg", "Session expired. Please login again.");
			RequestDispatcher rd = request.getRequestDispatcher("LoginView.jsp");
			rd.forward(request, response);
		}

		if (!newPassword.equals(confirmPassword)) {
			request.setAttribute("errorMsg", "New and confirm password do not match.");
		} else {
			try {
				UserModel model = new UserModel();

			
				model.changePassword(user.getLogin(), oldPassword, newPassword);

				request.setAttribute("successMsg", "Password changed successfully!");

//			} catch (RuntimeException e) {
//				request.setAttribute("errorMsg", e.getMessage());
			} catch (Exception e) {
				request.setAttribute("errorMsg", "Something went wrong. Please try again.");
				e.printStackTrace();
			}
		}

		RequestDispatcher rd = request.getRequestDispatcher("ChangePasswordView.jsp");
		rd.forward(request, response);
	}
}
