package com.revature.servicelogic;

import java.util.Scanner;

import com.revature.items.User;

public interface TransferServiceInterface {

	void transfer(Scanner scan, User u);
	void approveTransfer(int account_id, int user_id);
	void requestTransfer(Scanner scan, User u);
	}
