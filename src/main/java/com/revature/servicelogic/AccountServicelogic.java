package com.revature.servicelogic;

import java.util.Scanner;

import com.revature.items.User;

public interface AccountServicelogic {

	
	
	void accountOptions(Scanner scan, User loggedUser);
	
	void depositWithdrawal(Scanner scan, User loggedUser);
	
	void approvalOfAccount(Scanner scan, User loggedUser);
	
	
	
	

	
	

}
