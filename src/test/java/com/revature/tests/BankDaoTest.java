package com.revature.tests;

import org.junit.Assert;
import org.junit.Test;

import com.revature.data.TransactionsDao;
import com.revature.data.UserDao;
import com.revature.items.Transaction;
import com.revature.items.User;



public class BankDaoTest {
	
	private UserDao udao = new UserDao();
	private TransactionsDao tdao = new TransactionsDao(); 
	
	
// @Test
//	public void getUserTest() {
//		User u = udao.getUser("test3", "test");
//		Assert.assertEquals(u, udao.getUser("test3", "test"));
//	}
//	
//	@SuppressWarnings("deprecation")
//	@Test 
//	public void addUserTest() {
//		User u = new User("test","test1", "test2", "test3");
//		
//		Assert.assertEquals(true, udao.addUser(u));
//	}

//	public Transaction(int user_id, String transaction_type, String firstName, String lastName,
//			double transaction_amount, int account_id) {
	
	@Test
	public void getTransactionTest() {
		Transaction test = new Transaction(99, "JustaTest", "test", "test", 430.05, 99 );
		
		Assert.assertEquals(true, tdao.addTransaction(test));
		
	}
	
}
