package com.rays.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class DataValidator {

	public static boolean signUpValidation(HttpServletRequest request) {
		boolean isValid = true;

		if (request.getParameter("firstName") == "") {
			request.setAttribute("firstName", "  *First Name is required");

			isValid = false;
		}
		if (request.getParameter("lastName") == "") {
			request.setAttribute("lastName", "  *Last Name is required");

			isValid = false;
		}
		if (request.getParameter("login") == "") {
			request.setAttribute("login", "  *Login is required");

			isValid = false;
		} else if (!request.getParameter("login").endsWith("@gmail.com")) {
			request.setAttribute("login", "  Invalid login format");
			isValid = false;
		}


		if (request.getParameter("password") == "") {
			request.setAttribute("password", "  *Password is required");

			isValid = false;

		} else if (!(request.getParameter("password").length() >= 8
				&& request.getParameter("password").length() <= 12)) {
			request.setAttribute("password", "  *Your password must be between 8 and 12 characters long.");

			isValid = false;
		}

		else if (!request.getParameter("password").matches(".*[A-Z].*")) {
			request.setAttribute("password", "*Password must contain at least one uppercase letter.");
			isValid = false;
		}

		else if (!request.getParameter("password").matches(".*[a-z].*")) {
			request.setAttribute("password", "*Password must contain at least one lowercase letter.");
			isValid = false;

		}
		if (request.getParameter("dob") == "") {
			request.setAttribute("dob", "  *Dob is required");

			isValid = false;
		} else if (!(request.getParameter("dob") == "")) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			try {
				Date dob = sdf.parse(request.getParameter("dob"));
				Date now = new Date();
				int age = now.getYear() - dob.getYear();
				if (!(age >= 18 && age <= 60)) {
					request.setAttribute("dob", "  *You are not eligible for this website");
					isValid = false;
				}
			} catch (Exception e) {
			
				e.printStackTrace();
			}

		}

		return isValid;

	}

}
