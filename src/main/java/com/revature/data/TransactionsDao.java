package com.revature.data;

//public List<Transaction> getUserTransactions(int user_id ) {
//public List<Transaction> getAllTransactions(){
//public boolean addTransaction(Transaction t) 
//public Transaction getTransaction(int user_id )
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import com.revature.items.Transaction;
import com.revature.utils.JDBCConnection;


public class TransactionsDao  {
	private Connection conn = JDBCConnection.getConnection();
	
	public List<Transaction> getUserTransactions(int user_id ) {
		List<Transaction> userTransactions = new ArrayList<>();
		String sql = "select * from transactions where user_id = ?;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, user_id);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Transaction t = new Transaction();
				t.setFirstName(rs.getString("firstName"));
				t.setLastName(rs.getString("lastName"));
				t.setTransaction_amount(rs.getDouble("transaction_amount"));
				t.setTransaction_type(rs.getString("transaction_type"));
				t.setUser_id(rs.getInt("user_id"));
				
				userTransactions.add(t);
			}
			return userTransactions;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return null;	
	}
	
	public List<Transaction> getAllTransactions(){
		List<Transaction> allTransactions = new ArrayList<>();
		String sql = "select * from transactions;";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			while (rs.next()) {
				Transaction t = new Transaction();
				t.setTransaction_id(rs.getInt("transaction_id"));
				t.setFirstName(rs.getString("firstName"));
				t.setLastName(rs.getString("lastName"));
				t.setTransaction_amount(rs.getDouble("transaction_amount"));
				t.setTransaction_type(rs.getString("transaction_type"));
				t.setUser_id(rs.getInt("user_id"));
				t.setDateTime(rs.getString("timeoftransaction"));
				
				allTransactions.add(t);
			}
			return allTransactions;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return null;	
			}
	
	public boolean addTransaction(Transaction t) {
		String sql = "insert into transactions values (default, ? , ?, ?, ?, ?, ?, ? );";
		try {
			PreparedStatement ps = conn.prepareStatement(sql);
			boolean success = ps.execute();
			ps.setInt(1, t.getUser_id());
			ps.setString(2, t.getFirstName());
			ps.setString(3, t.getLastName());
			ps.setString(4, t.getTransaction_type());
			ps.setDouble(5, t.getTransaction_amount());
			ps.setString(6, t.getDateTime());
			ps.setInt(7, t.getAccount_id());
			if (success) { 
				return true;
			}
			
		} catch (SQLException e) {
		e.printStackTrace();
			}
		return false;
		}
	public Transaction getTransaction(int user_id ) {
		Transaction t = new Transaction();
		String sql = "select * from transactions where user_id = ?;";
		try {
			
		
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1, user_id);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				t.setTransaction_id(rs.getInt("transaction_id"));
				t.setFirstName(rs.getString("firstName"));
				t.setLastName(rs.getString("lastName"));
				t.setTransaction_amount(rs.getDouble("transaction_amount"));
				t.setTransaction_type(rs.getString("transaction_type"));
				t.setUser_id(rs.getInt("user_id"));
				t.setAccount_id(rs.getInt("transferaccount_id"));
			}
				
			return t;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	return null;	
	}
	}
	
	

	


