package com.rays.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.rays.bean.UserBean;
import com.rays.exception.DuplicateRecordException;
import com.rays.exception.RecordNotFoundException;
import com.rays.util.JDBCDataSource;

public class UserModel {
    
	//Generate next primary key

	public int nextpk() throws Exception {
		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select max(id) from st_user");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);

		}
		conn.close();
		return pk + 1;
	}

	// ADD QUERY

	public void add(UserBean bean) throws Exception {

		UserBean existsBean = findByLogin(bean.getLogin());

		if (existsBean != null) {
			throw new Exception("login id already exist");
		}
		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("insert into st_user values(?,?,?,?,?,?)");
		int pk = nextpk();

		pstmt.setInt(1, pk);
		pstmt.setString(2, bean.getFirstname());
		pstmt.setString(3, bean.getLastname());
		pstmt.setString(4, bean.getLogin());
		pstmt.setString(5, bean.getPassword());
		pstmt.setDate(6, new java.sql.Date(bean.getDob().getTime()));

		int i = pstmt.executeUpdate();
		System.out.println("data inserted successfully: " + i);
		conn.close();

	}

	// delete query

	public void delete(UserBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("delete from st_user where id = ?");

		pstmt.setInt(1, bean.getId());

		int i = pstmt.executeUpdate();

		System.out.println("data deleted succesfully: " + i);

		conn.close();

	}

	// update query

	public void update(UserBean bean) throws Exception {
		
		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement(
				"update st_user set firstName = ?, lastName = ?, login = ?, password = ?, dob = ? where id = ?");

		pstmt.setString(1, bean.getFirstname());
		pstmt.setString(2, bean.getLastname());
		pstmt.setString(3, bean.getLogin());
		pstmt.setString(4, bean.getPassword());
		pstmt.setDate(5, new java.sql.Date(bean.getDob().getTime()));
		pstmt.setInt(6, bean.getId());

		int i = pstmt.executeUpdate();
		System.out.println("Data updated successfully");
		conn.close();

	}

	// FIND BY LOGIN QUERY

	public UserBean findByLogin(String login) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select * from st_user where login = ?");

		pstmt.setString(1, login);

		ResultSet rs = pstmt.executeQuery();

		UserBean bean = null;
		while (rs.next()) {
			bean = new UserBean();

			bean = new UserBean();
			bean.setId(rs.getInt(1));
			bean.setFirstname(rs.getString(2));
			bean.setLastname(rs.getString(3));
			bean.setLogin(rs.getString(4));
			bean.setPassword(rs.getString(5));
			bean.setDob(rs.getDate(6));

		}
		conn.close();
		return bean;

	}

	// AUTHENTICATE QUERY

	public UserBean authenticate(String login, String password) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select * from st_user where login = ? and password = ?");

		pstmt.setString(1, login);
		pstmt.setString(2, password);

		ResultSet rs = pstmt.executeQuery();

		UserBean bean = null;

		while (rs.next()) {

			bean = new UserBean();
			bean.setId(rs.getInt(1));
			bean.setFirstname(rs.getString(2));
			bean.setLastname(rs.getString(3));
			bean.setLogin(rs.getString(4));
			bean.setPassword(rs.getString(5));
			bean.setDob(rs.getDate(6));

		}
		conn.close();
		return bean;

	}

	// Change password

	public void changePassword(String login, String password, String newPassword) throws Exception {

		UserBean bean = authenticate(login, password);

		if (bean != null) {

			Connection conn = JDBCDataSource.getConnection();
			PreparedStatement pstmt = conn.prepareStatement("update st_user set password = ? where id =?");

			pstmt.setString(1, newPassword);
			pstmt.setInt(2, bean.getId());
			pstmt.executeUpdate();

			System.out.println("Password changed successfully");
		} else {
			throw new RuntimeException("Wrong Username or Password");
		}

	}

	// Find by id

	public UserBean findById(int id) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select * from st_user where id = ?");

		pstmt.setInt(1, id);

		ResultSet rs = pstmt.executeQuery();

		UserBean bean = null;
		while (rs.next()) {
			bean = new UserBean();
			bean.setId(rs.getInt(1));
			bean.setFirstname(rs.getString(2));
			bean.setFirstname(rs.getString(3));
			bean.setLogin(rs.getString(4));
			bean.setPassword(rs.getString(5));
			bean.setDob(rs.getDate(6));

		}

		conn.close();
		return bean;

	}

	// find by search

	public List search(UserBean bean) throws Exception {

		List list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from st_user where 1=1");

		if (bean != null) {
			if (bean.getFirstname() != null && bean.getFirstname().length() > 0) {
				sql.append(" and firstname like '%" + bean.getFirstname() + "%'");

			}
			if (bean.getLastname() != null && bean.getLastname().length() > 0) {
				sql.append(" and lastname like '%" + bean.getLastname() + "%'");

			}

			if (bean.getId() > 0 && bean.getId() < nextpk()) {
				sql.append(" and id like '%" + bean.getId() + "%'");

			}

			if (bean.getLogin() != null && bean.getLogin().length() > 0) {
				sql.append(" and lastname like '%" + bean.getLogin() + "%'");
			}

			if (bean.getDob() != null) {
				sql.append(" and dob = ' " + new java.sql.Date(bean.getDob().getTime()) + " ' ");

			}
		}

		Connection conn = JDBCDataSource.getConnection();

		System.out.println("sql ----> " + sql.toString());
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			bean = new UserBean();
			bean.setId(rs.getInt(1));
			bean.setFirstname(rs.getString(2));
			bean.setLastname(rs.getString(3));
			bean.setLogin(rs.getString(4));
			bean.setPassword(rs.getString(5));
			bean.setDob(rs.getDate(6));
			list.add(bean);

		}

		return list;

	}

}
