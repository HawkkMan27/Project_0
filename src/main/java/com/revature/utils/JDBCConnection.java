package com.revature.utils;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
public class JDBCConnection {

	
	
	private static Connection conn = null;
	//a method to get a connection
	public static Connection getConnection() {
		// if no connection make a connection
		try {
		if (conn == null) {
			//To ensure that the driver loads correctly when the application starts
			Class.forName("org.postgresql.Driver");
		
			Properties props = new Properties();
			
			InputStream input = JDBCConnection.class.getClassLoader().getResourceAsStream("connection.properties");
			
			props.load(input);
			
			String url = props.getProperty("url");
			String username = props.getProperty("username");
			String password = props.getProperty("password");
			
			conn = DriverManager.getConnection(url, username, password);
		return conn;
		} else {
			return conn;
		}
		
		} catch (SQLException | IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
