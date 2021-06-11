package com.revature.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.revature.items.Account;
import com.revature.utils.JDBCConnection;

public class AccountDao implements AccountRepoable{

	private Connection conn = JDBCConnection.getConnection();
	@Override
	public boolean addAccount(Account a) {
		String sql = "insert into accounts values (defuault, ? , ?, ?, ?)";
				try {
					PreparedStatement ps = conn.prepareStatement(sql);
					boolean success = ps.execute();
					
					ps.setInt(1, a.getUser_id());
					ps.setString(2, a.getType());
					ps.setDouble(3, a.getBalance());
					ps.setString(4, a.getApproval());
					
					if (success) {
						return true;
					}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
		return false;
	}

	@Override
	public List<Account> getAccounts(int user_id) {
		
		List<Account> userAccounts = new ArrayList<>();
		String sql = "select * from accounts where user_id = ?;";
		try {
		PreparedStatement ps = conn.prepareStatement(sql);
		ps.setInt(1, user_id);
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			Account a = new Account();
			a.setId(rs.getInt("id"));
			a.setUser_id(rs.getInt("user_id"));
			a.setType(rs.getString("type"));
			a.setUser_id(rs.getInt("user_id"));
			a.setBalance(rs.getDouble("balance"));
			
			userAccounts.add(a);
			}
		return userAccounts;
		
		}catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	public Account getAccount(int id, int user_id) {
		String sql = "select * from accounts where account_id = ? and user_id = ?;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, user_id);
			ResultSet rs = ps.executeQuery();
			
			if  (rs.next()) {
				Account a = new Account();
				a.setId(rs.getInt("id"));
				a.setUser_id(rs.getInt("user_id"));
				a.setType(rs.getString("type"));
				a.setUser_id(rs.getInt("user_id"));
				a.setBalance(rs.getDouble("balance"));
				
				return a;
			}
			} catch (SQLException e) {
					e.printStackTrace();
				}
		return null;
	}


	@Override
	public boolean accountDeposit(int id , double balance) {
		String sql = "update accounts set balance = ?, where account_id = ?;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setDouble(1, balance);		
			ps.setInt(2, id);
			boolean success = ps.execute();
			
			if (success) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
									} 		
		return false;
	}

	@Override
	public boolean removeAccountById(int id, int user_id) {
		String sql = "delete * from Users where account_id = ? and user_id = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, id);
			ps.setInt(2, user_id);
		    boolean success = ps.execute();
		    if (success) {
				System.out.println("Account " + id + " for user with ID = " + user_id + " successfully removed" );
				return true; 
							}
			} catch (SQLException e) {
			e.printStackTrace();
				}
		
		return false;
	}


	@Override
	public boolean approveAccount(int id, int user_id, String approval) {
		String sql = "update accounts set approval = ? where account_id = ? and user_id = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setString(1, approval);
			ps.setInt(2, id);
			ps.setInt(3, user_id);
			
			boolean success = ps.execute();
			if (success) {
				System.out.println("Account " + id +  "for user " + user_id + " is " + approval);
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;
	}

	@Override
	public List<Account> getAllAccounts() {
		List<Account> allAccounts = new ArrayList<>();
		String sql = "select * from accounts;";
		
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			while (rs.next()) {
				Account a = new Account();
				a.setId(rs.getInt("id"));
				a.setUser_id(rs.getInt("user_id"));
				a.setType(rs.getString("type"));
				a.setUser_id(rs.getInt("user_id"));
				a.setBalance(rs.getDouble("balance"));
				
				allAccounts.add(a);
			}
			return allAccounts;
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return null;
	}

	

	@Override
	public boolean accountUpdate(int id, double balance) {
		String sql = "update accounts set balance = ?, where account_id = ?;";
		
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			
			ps.setDouble(1, balance);		
			ps.setInt(2, id);
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
