package com.revature.servicelogic;
//"TransfertoUserId" is the indicator of transfer request
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.revature.data.AccountDao;
import com.revature.data.AccountRepoable;
import com.revature.data.TransactionsDao;
import com.revature.items.Account;
import com.revature.items.Transaction;
import com.revature.items.User;
import com.revature.logging.AppLogger;

public class TransferServiceClass implements TransferServiceInterface {

	private AccountRepoable aa = new AccountDao();
	private TransactionsDao tt = new TransactionsDao();
	
	@Override
	public void transfer(Scanner scan, User u) {
		// TODO Auto-generated method stub
		
	}
	//given in approvetransfer the loggedinusers account id and use their user_id
	@Override
	public void approveTransfer(int account_id, int user_id) {
		
		
	}

	@Override
	public void requestTransfer(Scanner scan, User u) {
		List <Account> AllAccounts = aa.getAllAccounts(); 
		List <Integer> allAccountIds = new ArrayList<>();
		boolean rt = true;
		while (rt) {
			System.out.println("Here are all the account Ids. See one you know?");
			for (Account ac : AllAccounts) {
				int tempid = ac.getId();
					if (ac.getUser_id() != u.getUser_id()) {
						System.out.println("account Id = " + ac.getId() + " user_Id = " + ac.getUser_id());
						allAccountIds.add(tempid);
					}
			}
			rt = false;	
			}
			
			int Id;
			while (true) {
			try {
				System.out.println("\nChoose an accountId to send Money to. ");
				Id = Integer.parseInt(scan.nextLine());
				break;
			} catch( NumberFormatException e) {
				System.out.println("Incorrect Input. Please try again");
				
			}
			}
			boolean exists = false;
			for(int in : allAccountIds) {
				if (Id == in) {
					System.out.println("Account " + Id + " exists!");
				exists=true;
				break;
				} 
			}
			if (exists) {
				
				List<Account> yourAccounts = aa.getAccounts(u.getUser_id());
				System.out.println("Your accounts listed below!");
				for(Account a: yourAccounts) {
					System.out.println("Your " + a.getType() + " Account with a balance of " + a.getBalance() + " and the account Id is " + a.getId());
					
				}
				System.out.println("\nWhich account would you like to send money from? Enter an account Id from the list");
				int accountid = Integer.parseInt(scan.nextLine());
				
				boolean goodtogo = false;
				for (Account b: yourAccounts) {
					if (b.getId() == accountid) {
						goodtogo = true;
						break;
					}else {
						System.out.println("Incorrect Input. This account number doesn't exist.");
						break;
					}
					
				}
				while (goodtogo) {
				Account youraccount = aa.getAccount(accountid, u.getUser_id() );
				System.out.println("Your " + youraccount.getType() + " Account " + youraccount.getId() + " has a balance of " + youraccount.getBalance());
				System.out.println("\nPlease enter an amount to send to Account " + Id);
				
				double sendDinero = Double.parseDouble(scan.nextLine());
				
				if (youraccount.getBalance() >= sendDinero) { 
				AppLogger.logger.info("Transfer request from User: " + u.getUser_id());
				String type = "TransfertoUserAccount"; // + accountid;
				Transaction transfer = new Transaction(accountid, type, u.getFirstName(), u.getLastName(), sendDinero, Id);
				boolean win = tt.addTransaction(transfer);
				if (win) {
					System.out.println("Transfer request successful your account will update if they approve the request");
					
				}else {System.out.println("Transfer didn't upload correctly. Try again some other time"); }
				rt = false;
				goodtogo = false;
				} else { System.out.println("I'm sorry you don't have enough money. Please redo Transfer request");}
				
				}
			} else {System.out.println("No account exists here");
							}
			}
	
}
		
	

	




