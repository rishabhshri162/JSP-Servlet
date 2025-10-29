package com.rays.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.rays.bean.ShoppingBean;
import com.rays.bean.UserBean;
import com.rays.model.ShoppingModel;
import com.rays.model.UserModel;
import com.rays.util.DataValidator;

@WebServlet("/AddShoppingCtl")
public class AddShoppingCtl extends HttpServlet {
	

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		ShoppingModel model = new ShoppingModel();
		ShoppingBean bean = new ShoppingBean();

		String id = request.getParameter("id");
		System.out.println("id >>>>-- " + id);

		if (id != null) {
			try {
				System.out.println(id);
				bean = model.findById(Integer.parseInt(id));
				System.out.println(id);
				request.setAttribute("bean", bean);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		RequestDispatcher rd = request.getRequestDispatcher("AddShoppingView.jsp");
		rd.forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String op = request.getParameter("operation");

		
		ShoppingModel model = new ShoppingModel();
		ShoppingBean bean = new ShoppingBean();

		String shopName = request.getParameter("shopName");
		String productName = request.getParameter("productName");
//		String login = request.getParameter("login");
//		String password = request.getParameter("password");
		Double price = Double.parseDouble(request.getParameter("price"));

		try {
			bean.setShopName(shopName);
			bean.setProductName(productName);
			bean.setPrice(price);


			if (op.equals("update")) {
				bean.setId(Integer.parseInt(request.getParameter("id")));
				model.update(bean);
				request.setAttribute("successMsg", "Data updated Successfully");
			} else {
				model.add(bean);
				request.setAttribute("successMsg", "Data added Successfully");
			}

		} catch (Exception e) {
			request.setAttribute("errorMsg", e.getMessage());
			e.printStackTrace();
		}

		RequestDispatcher rd = request.getRequestDispatcher("AddShoppingView.jsp");
		rd.forward(request, response);

	}

}