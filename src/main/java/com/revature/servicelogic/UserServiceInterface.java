package com.revature.servicelogic;

import java.util.Scanner;

import com.revature.items.User;

public interface UserServiceInterface {

	 
	 
	 
	 void employeeMenu(Scanner scan, User u);

	User login(Scanner scan);
	void customerMenu(Scanner scan, User u);
	void register(Scanner scan);

	
	
}
