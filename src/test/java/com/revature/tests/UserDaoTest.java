package com.revature.tests;

import org.junit.Test;

import com.revature.data.UserDao;
import com.revature.items.User;

import junit.framework.Assert;

public class UserDaoTest {
	
	private UserDao udao = new UserDao();
	
	@SuppressWarnings("deprecation")
	@Test
	public void getUserTest() {
		User u = udao.getUser("test3", "test");
		Assert.assertEquals(u, udao.getUser("test3", "test"));
	}
	
	@SuppressWarnings("deprecation")
	@Test 
	public void addUserTest() {
		User u = new User("test","test1", "test2", "test3");
		
		Assert.assertEquals(true, udao.addUser(u));
	}
}
