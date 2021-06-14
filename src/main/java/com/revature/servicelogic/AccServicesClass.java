package com.revature.servicelogic;


import java.util.List;
import java.util.Scanner;

import com.revature.data.AccountDao;
import com.revature.data.AccountRepoable;
import com.revature.data.TransactionsDao;
import com.revature.data.UserDao;
import com.revature.data.UserRepoable;
import com.revature.items.Account;
import com.revature.items.Transaction;
import com.revature.items.User;
import com.revature.logging.AppLogger;


public class AccServicesClass implements AccountServicelogic {

	private AccountRepoable aa = new AccountDao();
	private TransactionsDao tt = new TransactionsDao();
	private UserRepoable ua = new UserDao();
	public static void logInfo(String message) {
		AppLogger.logger.info(message);
	}

	@Override
	public void accountOptions(Scanner scan, User loggedUser) {
		boolean accRun = true;
		AppLogger.logger.info("User choosing account options");
	while (accRun) {
	System.out.println("Choose a number from the Account Options!");
	System.out.println("1. View your Current Accounts\n" + "2. Apply for a new Account\n" + "3. Return to User Menu");
	int choice = Integer.parseInt(scan.nextLine());
	switch(choice) {
	case 1: {
		List<Account> yourAccounts = aa.getAccounts(loggedUser.getUser_id());
		//get all accounts with the users id and store it into a list
		//trans viewed accounts
		System.out.println("Accounts\n---------------");
		for(Account aA : yourAccounts) {
		System.out.println(" " + aA + "\n"); 
											}
		AppLogger.logger.info("User viewed their accounts");
		break;
	}
	case 2: { 
		boolean caRun = true;
		while (caRun) {
			System.out.println("What kind of Account\n1. Savings Account\n2. Checkings Account\nEnter \"3\" to return to Account Options");
			int choiceCrA = Integer.parseInt(scan.nextLine());
			switch(choiceCrA) {
				case 1: {
							
							System.out.println("Please Enter your initial deposit");
							double firstDepo = Double.parseDouble(scan.nextLine());
							//input into database aa accdao
							System.out.println("You've input: " + firstDepo + " As your Initial Deposit. Is this correct (y/n)?");
							String ans = scan.nextLine();
							if (ans.equals("y")) {
								//input new account into database with amount and account type Checking
								
								
								//Check if its a positive deposit and if saved into database
									if (firstDepo > 0) {
										Account acc = new Account();
										acc.setType("Savings");
										acc.setApproval("pending");
										acc.setBalance(firstDepo);
										acc.setUser_id(loggedUser.getUser_id());
										boolean saved = aa.addAccount(acc);
										//trans add accountsavings
										if (saved) {
												Transaction Tt = new Transaction(acc.getUser_id(), "SavingsAccountMade", loggedUser.getFirstName(), loggedUser.getLastName(), firstDepo, 0 );
												tt.addTransaction(Tt);
												System.out.println("Congrats you've created a Savings Account. Awaiting approval of account from our Staff");
												caRun = false;	
												AppLogger.logger.info("User created a Savings account");
										} else {
												System.out.println("Something happened in our database. please try and input account again");
												caRun = true;
										}
										
									} else { 
											System.out.println("Wrong amount entered please enter a positive amount with two decimal digits");  
											caRun = true;
											}
												
							}
							else if (ans.equals("n")) {
								System.out.println("Lets try this again!");
								caRun = true;	
													}
							else {
								System.out.println("Incorrect response. Please input an Initial Deposit again");
								caRun = true;
									} 
								break;
	
				}
							
				case 2:{
							System.out.println("Please Enter your initial deposit");
							double firstDepo = Double.parseDouble(scan.nextLine());
							//input into database aa accdao
							System.out.println("You've input: " + firstDepo + " as your Initial Deposit. Is this correct (y/n)?");
							String ans = scan.nextLine();
							if (ans.equals("y")) {
								//input new account into database with amount and account type Checking
								caRun = false;
								if (firstDepo > 0) {
									Account acc = new Account();
									acc.setType("Checkings");
									acc.setApproval("pending");
									acc.setBalance(firstDepo);
									acc.setUser_id(loggedUser.getUser_id());
									boolean saved2 = aa.addAccount(acc);
									
									//trans addaccountCheckings
								 if (saved2) {
									 Transaction Tt = new Transaction(acc.getUser_id(), "CheckingsAccountMade", loggedUser.getFirstName(), loggedUser.getLastName(), firstDepo, 0 );
									 tt.addTransaction(Tt);
									 	System.out.println("Congrats you've created a Checking Account. Awaiting approval of account from our Staff");
									 	caRun = false;	
									 	AppLogger.logger.info("User created a checkings account");
							} else {
										System.out.println("Something happened in our database. please try and input account again");
										caRun = true;
							}
											}
							} else if (ans.equals("n")) {
								System.out.println("Lets try this again!");
								
												 }
							else {
								System.out.println("Incorrect response. Please choose an account type and enter an Initial deposit again");
								
									} break;
							}
				case 3:{ caRun = false; System.out.println("Returning back to Account Menu"); break; }
			}
	
			}
		break;}
	case 3: {
		System.out.println("Returning back to User Menu");
		accRun=false;
		break;
			}
		}
	}
	}

	@Override
	public void depositWithdrawal(Scanner scan, User loggedUser) {
		// int AccountId = loggedUser.getAccountId(); input accounts into a list
		boolean dWRun = true;
		List<Account> userAccounts = aa.getAccounts(loggedUser.getUser_id());
		while (dWRun) {
			
			System.out.println("Here are your accounts and balances");
			for(Account ac : userAccounts) {
				
				System.out.println("Account Id:  " +  ac.getId() + " Account User Id: " +  ac.getUser_id() + " Account Type: " + ac.getType());
				System.out.println(" Account Status: " + ac.getApproval() + "\n");
			}
			System.out.println("Choose an Option\n" + "1. Withdrawal\n2. Deposit\n3: Return to Customer Menu");
			int choice = Integer.parseInt(scan.nextLine());
			switch (choice) {
			case 1: {
				System.out.println("Enter an ID number for the Account you wish to withdraw from");
				int input = Integer.parseInt(scan.nextLine());
				Account withdrawAcc = aa.getAccount(input, loggedUser.getUser_id());
				if (withdrawAcc == null) {
					System.out.println("That account doesn't exist, Please try Again.");
					dWRun = true;
					} else if (withdrawAcc.getApproval().equals("pending")){
						System.out.println("Account is waiting to be Approved. Cannot Deposit at the Moment");
						break;
					}else {
						
						System.out.println("The Account: " + withdrawAcc.getId() + " has a balance of " + withdrawAcc.getBalance());
							boolean amountcorrect = true;
					while (amountcorrect) { 
						System.out.println("How much would you like to withdraw?");
					
						double amountWithdrawn = Double.parseDouble(scan.nextLine());
						if (amountWithdrawn > 0 & amountWithdrawn <= withdrawAcc.getBalance()) {
							System.out.println( "Amount to Withdraw: " + amountWithdrawn + ". Is this the correct amount to withdraw (y/n)?");
							String yayornay = scan.nextLine();
							if (yayornay.equals("y")) {
						// trans set balance to get balance plus amount specified to withdraw
								withdrawAcc.setBalance(withdrawAcc.getBalance() - amountWithdrawn);
								boolean success = aa.accountDeposit(input, withdrawAcc.getBalance());
								if (success) {
									Transaction tw = new Transaction(loggedUser.getUser_id(), "Withdrawal", loggedUser.getFirstName(), loggedUser.getLastName(), withdrawAcc.getBalance(), 0);
									tt.addTransaction(tw);
									System.out.println("You've officially withdrawn " + amountWithdrawn + ". The new Balance is " + withdrawAcc.getBalance());
						// display new balance
									dWRun = false;
									amountcorrect = false;
									AppLogger.logger.info("User Withdrawed from account");
								} else {System.out.println("Not properly saved withdrawal please try again");
									dWRun = true;
								}
						} else if (yayornay.equals("n")){
							System.out.println("No? Ok please enter the correct amount to withdraw");
								} else {
							System.out.println("Please enter the \"y\" or \"n\"");
							
								}}}
				}
				break;
			}
			case 2: {
				System.out.println("Enter an ID number for the Account you wish to deposit from");
				int input = Integer.parseInt(scan.nextLine());
				
				Account depositAcc = aa.getAccount(input, loggedUser.getUser_id());
				if (depositAcc == null) {
					System.out.println("That account doesn't exist, Please try Again.");
					dWRun = true;
					} else if (depositAcc.getApproval().equals("pending")){
						System.out.println("Account is waiting to be Approved. Cannot Deposit at the Moment");
						break;
					}else {
						
						System.out.println("The Account Id is " + depositAcc.getId() + " and has a balance of " + depositAcc.getBalance());
							boolean amountcorrect = true;
					while (amountcorrect) { 
						System.out.println("How much would you like to deposit?");
					
						double amountDeposited = Double.parseDouble(scan.nextLine());
						if (amountDeposited > 0 ) {
							System.out.println( "Amount to deposit: " + amountDeposited + ". Is this the correct amount to deposit (y/n)?");
							String yayornay = scan.nextLine();
							if (yayornay.equals("y")) {
						// trans set balance to get balance plus amount specified to deposit
								depositAcc.setBalance(depositAcc.getBalance() + amountDeposited);
								boolean success = aa.accountDeposit(input, depositAcc.getBalance());
								if  (success) {
									Transaction td = new Transaction(loggedUser.getUser_id(), "depositamount", loggedUser.getFirstName(), loggedUser.getLastName(), amountDeposited, 0 );
									 tt.addTransaction(td);
									System.out.println("You've officially deposited " + amountDeposited + ". The new Balance is " + depositAcc.getBalance());
						// display new balance
									dWRun = false;
									amountcorrect = false;
									AppLogger.logger.info("User deposited into account");
								}else {System.out.println("Not properly saved deposit please try again");
									dWRun = true;
										}
						} else if (yayornay.equals("n")){
							System.out.println("No? Ok please enter the correct amount to deposit");
								} else {
							System.out.println("Please enter the \"y\" or \"n\" next time");
							
								} }else {System.out.println("Incorrect amount. Please enter an amount greater than 0.");}
						}
				}
				break;
			}
			case 3: {
				dWRun = false; System.out.println("Returning back to Customer Menu");
				break;
			}
			}
			
			
		}

	}

	@Override
	public void approvalOfAccount(Scanner scan, User loggedUser) {
		System.out.println("Here are the accounts that require approval or denial");
		
		List<Account> tobeapprovedaccounts = aa.getpendingAccounts();
				
		for(Account cp : tobeapprovedaccounts) {
			User upending = ua.getUser(cp.getUser_id());
			System.out.println("User: " + upending.getFirstName() + " " + upending.getLastName());
			System.out.println("[ Account: " + cp.getId() + " Balance: " + cp.getBalance() + " Type: " + cp.getType() + " This account is " + cp.getApproval() + ". User Id: " + cp.getUser_id() + " ]");
			
		}
		boolean aoA = true;
		if (tobeapprovedaccounts.isEmpty()) {
			System.out.println("\nNo accounts to be approved!\n");
			aoA = false;
		}
		while (aoA) {
		
			
			System.out.println("Please enter the Id number of the account you wish to approve or deny");
				int accountId = Integer.parseInt(scan.nextLine()); 
			System.out.println("Please input the User_Id");
				int userId = Integer.parseInt(scan.nextLine());
				
			System.out.println("Do you wish to approve account " + accountId + " for User ID [ " + userId + " ], if not the account will be denied? (y/n)");
			String approved = scan.nextLine();
			if (approved.equals("y")) {
				Account aapproved = aa.getAccount(accountId, userId);
				if (aapproved == null) {
					System.out.println("Couldn't find the account! Please Enter a correct account Id and User_Id from the list of pending accounts");
				}else {
					boolean success = aa.approveAccount(accountId, userId, "Approved");
					if (success) {
						String translog = "Approved Account [ " + accountId + " ]!";
						Transaction Ta = new Transaction(userId, translog, loggedUser.getFirstName(), loggedUser.getLastName(), 0, 0 );
						 tt.addTransaction(Ta);
						 AppLogger.logger.info("Logged in Employee approved an account");
						System.out.println("Account " + accountId + " approved!");
						aoA = false;
					} else {
						System.out.println("Account changes did not upload correctly. Please try again");
					}
			} 
			
		}else if (approved.equals("n")) {
			boolean denied = aa.removeAccountById(accountId, userId);
			if (denied) {
				System.out.println("Account [ " + accountId + " ] successfully Deleted");
				aoA = false;
				}else {
				System.out.println("Something went wrong in deleting account. Please try again at a different time.");	
				}
		} else {
				System.out.println("Input has an incorrect format, Please choose a different Account");
			}
	}

}}
