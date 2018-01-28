package br.edu.unicid.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class ConnectionFactory {

	public static Connection getConnection() throws Exception {
		try {
			// DRIVER I'M USING
			Class.forName("com.mysql.jdbc.Driver");
			// URL FOR LOCAL DB
			//return DriverManager.getConnection("jdbc:mysql://localhost:3306/wecti3", "root", "");
			// URL FOR REMOTE DB
			return DriverManager.getConnection(
					"jdbc:mysql://wecti.mysql.uhserver.com:3306/wecti", 
					"wecti", 
					"Unicid@2017");
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	// QUANDO UM OBJ CONN E FECHADO TODOS OS RECURSOS JDBC SAO FECHADOS AUTOMATICAMENTE
	public static void closeConnection(Connection conn, Statement stmt, ResultSet rs) throws Exception {
		close(conn, stmt, rs);
	}
	
	public static void closeConnection(Connection conn, Statement stmt) throws Exception {
		close(conn, stmt, null);
	}
	
	public static void closeConnection(Connection conn) throws Exception {
		close(conn, null, null);
	}
		
	public static void close(Connection conn, Statement stmt, ResultSet rs) throws Exception {
		try {
			if(rs != null) 
				rs.close();
			if(stmt != null) 
				stmt.close();
			if(conn != null) 
				conn.close();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}
}
