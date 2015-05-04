package com.reports;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MyReports {

	public static void main(String[] args) {
		
	     try {
			Connection conn = getConnection();
			Statement stmt = conn.createStatement();
			StringBuffer sql = new StringBuffer("select name, id, freetext, subject from student");
			ResultSet rs = stmt.executeQuery(sql.toString());
			while (rs.next()) {
				String name = rs.getString(1);
				System.out.println("name "+ name);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static Connection getConnection() throws ClassNotFoundException,
			SQLException {
		Class.forName("com.mysql.jdbc.Driver");
		Connection conn = DriverManager.getConnection("jdbc:mysql://localhost/college?user=root&password=pass@123");
		return conn;
	}

}
