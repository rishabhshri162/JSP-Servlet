package com.rays.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.rays.bean.ShoppingBean;
import com.rays.bean.UserBean;
import com.rays.util.JDBCDataSource;

public class ShoppingModel {

	public int nextpk() throws Exception {
		int pk = 0;

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select max(id) from shopping");

		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			pk = rs.getInt(1);

		}
		conn.close();
		return pk + 1;
	}

	// ADD QUERY

	public void add(ShoppingBean bean) throws Exception {

		ShoppingBean existsBean = findByLogin(bean.getLogin());

		if (existsBean != null) {
			throw new Exception("login id already exist");
		}
		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("insert into shopping values(?,?,?,?,?,?)");
		int pk = nextpk();

		pstmt.setInt(1, pk);
		pstmt.setString(2, bean.getShopName());
		pstmt.setString(3, bean.getProductName());
		pstmt.setDouble(4, bean.getPrice());
		pstmt.setString(5, bean.getLogin());
		pstmt.setString(6, bean.getPassword());

		int i = pstmt.executeUpdate();
		System.out.println("data inserted successfully: " + i);
		conn.close();

	}

	// delete query

	public void delete(int id) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("delete from shopping where id = ?");

		pstmt.setInt(1, id);

		int i = pstmt.executeUpdate();

		System.out.println("data deleted succesfully: " + i);

		conn.close();

	}

	// update query

	public void update(ShoppingBean bean) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement(
				"update shopping set shopName = ?, productName = ?, price = ? where id = ?");

		pstmt.setString(1, bean.getShopName());
		pstmt.setString(2, bean.getProductName());
		pstmt.setDouble(3, bean.getPrice());
		pstmt.setInt(4, bean.getId());

		int i = pstmt.executeUpdate();
		System.out.println("Data updated successfully");
		conn.close();

	}

	// Find by id

	public ShoppingBean findById(int id) throws Exception {

		Connection conn = JDBCDataSource.getConnection();

		PreparedStatement pstmt = conn.prepareStatement("select * from shopping where id = ?");

		pstmt.setInt(1, id);

		ResultSet rs = pstmt.executeQuery();

		ShoppingBean bean = null;
		while (rs.next()) {
			bean = new ShoppingBean();
			bean.setId(rs.getInt(1));
			bean.setShopName(rs.getString(2));
			bean.setProductName(rs.getString(3));
			bean.setPrice(rs.getDouble(4));
			

		}

		conn.close();
		return bean;

	}
	
	
	// FIND BY LOGIN QUERY

		public ShoppingBean findByLogin(String login) throws Exception {

			Connection conn = JDBCDataSource.getConnection();

			PreparedStatement pstmt = conn.prepareStatement("select * from shopping where login = ?");

			pstmt.setString(1, login);

			ResultSet rs = pstmt.executeQuery();

			ShoppingBean bean = null;
			while (rs.next()) {
				bean = new ShoppingBean();

				bean = new ShoppingBean();
				bean.setId(rs.getInt(1));
				bean.setShopName(rs.getString(2));
				bean.setProductName(rs.getString(3));
				bean.setLogin(rs.getString(4));
				bean.setPassword(rs.getString(5));
				bean.setPrice(rs.getDouble(6));

			}
			conn.close();
			return bean;

		}

	// find by search

	public List search(ShoppingBean bean, int pageNo, int pageSize) throws Exception {

		List list = new ArrayList();

		StringBuffer sql = new StringBuffer("select * from shopping where 1=1");

		if (bean != null) {
			if (bean.getShopName() != null && bean.getShopName().length() > 0) {

				sql.append(" and shopname like '" + bean.getShopName() + "%'"); // for only first letter search

			}
			if (bean.getProductName() != null && bean.getProductName().length() > 0) {
				sql.append(" and productname like '" + bean.getProductName() + "%'");

			}

			if (bean.getId() > 0 && bean.getId() < nextpk()) {
				sql.append(" and id like '%" + bean.getId() + "%'");

			}

		}

		if (pageSize > 0) {
			pageNo = (pageNo - 1) * pageSize;
			sql.append(" limit " + pageNo + " ," + pageSize);
		}

		Connection conn = JDBCDataSource.getConnection();

		System.out.println("sql ----> " + sql.toString());
		PreparedStatement pstmt = conn.prepareStatement(sql.toString());
		ResultSet rs = pstmt.executeQuery();

		while (rs.next()) {
			bean = new ShoppingBean();
			bean.setId(rs.getInt(1));
			bean.setShopName(rs.getString(2));
			bean.setProductName(rs.getString(3));
			bean.setLogin(rs.getString(4));
			bean.setPassword(rs.getString(5));
			list.add(bean);

		}
		conn.close();
		return list;

	}

}
