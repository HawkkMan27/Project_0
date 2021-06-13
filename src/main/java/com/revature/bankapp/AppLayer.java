package com.revature.bankapp;

import java.util.Scanner;

import com.revature.items.User;
import com.revature.logging.AppLogger;
import com.revature.servicelogic.UserService;
import com.revature.servicelogic.UserServiceInterface;

public class AppLayer {
	public static Scanner scan = new Scanner(System.in);
	public static User LoggedinUser;
	private static UserServiceInterface us = new UserService();
	
	public static void logInfo(String message) {
		AppLogger.logger.info(message);
	}
	
	
	
	private static void login() {
		LoggedinUser = us.login(scan);
			System.out.println("Login complete. Welcome " + LoggedinUser.getFirstName() + " " +  LoggedinUser.getLastName());
			if (LoggedinUser.getType().equals("Customer")) {
				us.customerMenu(scan, LoggedinUser);
				AppLogger.logger.info("Logged in as a Customer");
			} else if (LoggedinUser.getType().equals("Employee")) {
				us.employeeMenu(scan, LoggedinUser);
				AppLogger.logger.info("Logged in as an Employee");
			}
		}
	
	public static  void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		boolean run = true;
		while (run) {
			System.out.println("Please enter a number from Options");
			System.out.println("1. Login\n2. Register\n3. Exit BankApp");
			String option = scan.nextLine();
			switch (option) {
				case "1": 	login();
							break;
				case "2": 	us.register(scan);
							break;
				case "3": run = false;
						System.out.println("Goodbye! We'll miss you!");
							break;
			}
		}
scan.close();
	}
}
