package com.revature.data;



import java.util.List;

import com.revature.items.User;

public interface UserRepoable { 

	boolean addUser(User u);
	User getUser(int user_id);
	User getUser(String user, String pass);
	List <User> getAllUsers();
	
	boolean removeUserById(int id);
	
}
