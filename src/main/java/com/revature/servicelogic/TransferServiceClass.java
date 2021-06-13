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
				
			}
			System.out.println("Choose an accountId to send Money to. ");
			int Id = Integer.parseInt(scan.nextLine());
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
				System.out.println("Which account would you like to send money from? Enter an account Id from the list");
				int accountid = Integer.parseInt(scan.nextLine());
				Account youraccount = aa.getAccount(accountid, u.getUser_id() );
				System.out.println("Your " + youraccount.getType() + " Account " + youraccount.getId() + " has a balance of " + youraccount.getBalance());
				System.out.println("Please enter an amount to send to Account " + Id);
				double sendDinero = Double.parseDouble(scan.nextLine());
				if (youraccount.getBalance() >= sendDinero) { 
					
				String type = "TransfertoUserId";
				Transaction transfer = new Transaction(Id, type, u.getFirstName(), u.getLastName(), sendDinero, accountid);
				tt.addTransaction(transfer);
				rt = false;
				} else { System.out.println("I'm sorry you don't have enough money. Please redo Transfer request");}
				
				
			}
		}
		
	}

	




