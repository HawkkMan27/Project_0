package com.revature.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.items.User;
import com.revature.utils.JDBCConnection;

public class UserDao implements UserRepoable {
private Connection conn = JDBCConnection.getConnection();
	User daUser = new User(); 
	@Override
	public boolean addUser(User u) {
		
		String sql = "insert into users values (default, ? , ? , ? , ? , ? );";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			
			ps.setString(1, u.getFirstName());
			ps.setString(2, u.getLastName());
			ps.setString(3,u.getUsername());
			ps.setString(4,u.getPassword());
			ps.setString(5, u.getType());
			boolean succes= ps.execute();
		
		if (succes) {
			return true;
			} 
		
			
		} catch (SQLException e) {
		e.printStackTrace();
			}
		return true;
	}
	
	

	@Override
	public User getUser(int user_id) {
		String sql = "select * from Users where user_id = ?;";
		User u = new User();
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, user_id );
			ResultSet rs = ps.executeQuery();
			
				if (rs.next()) {
				u.setFirstName(rs.getString("firstName"));
				u.setLastName(rs.getString("lastName"));
				u.setUsername(rs.getString("username"));
				u.setType(rs.getString("type"));
				u.setUser_id(rs.getInt("user_id"));
									
				return u;
				} 
			} catch (SQLException e) {
			e.printStackTrace();
				}
		return null;
	}

	@Override
	public User getUser(String user, String pass) {
		String sql = "select * from Users where username = ? and password = ?";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, user);
			ps.setString(2, pass);
			ResultSet rs = ps.executeQuery();
			
				if  (rs.next()) {
				User u = new User();
				u.setFirstName(rs.getString("firstName"));
				u.setLastName(rs.getString("lastName"));
				u.setUsername(rs.getString("username"));
				u.setType(rs.getString("type"));
				u.setUser_id(rs.getInt("user_id"));
									
				return u;
				}
			} catch (SQLException e) {
			e.printStackTrace();
				}
		return null;
	}

	@Override
	public List<User> getAllUsers() {
		List<User> Users = new ArrayList<>();
		String sql = "select * from users;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				User u = new User();
				u.setFirstName(rs.getString("firstName"));
				u.setLastName(rs.getString("lastName"));
				u.setUsername(rs.getString("username"));
				u.setType(rs.getString("type"));
				u.setUser_id(rs.getInt("user_id"));
				
				Users.add(u);
									}
			return Users;
			} catch (SQLException e) {
			e.printStackTrace();
				}
			return null;
			}
	

	
	@Override
	public boolean removeUserById(int id) {
		String sql = "delete * from Users where user_id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
		    boolean success = ps.execute();
		    if (success) {
				return true;
		    }
			} catch (SQLException e) {
			e.printStackTrace();
				}
		
		return false;
	}

}
