package com.revature.repositories;
import java.util.List;

import com.revature.items.User;
public interface GenericRepository {

	User addUser(User u);
	User getUser(Integer i);
	User getUser(String username, String password);

	List<User> getUser();
	User updateUser(User u);
	boolean removeUser(User u);
}
