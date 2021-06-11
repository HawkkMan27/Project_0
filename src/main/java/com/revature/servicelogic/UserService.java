package com.revature.servicelogic;

import java.util.ArrayList;
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


public class UserService implements UserServiceInterface{
	
	private UserRepoable ua = new UserDao();
	private AccountRepoable aa = new AccountDao();
	private static TransferServiceInterface ts = new TransferServiceClass();
	private static AccountServicelogic as = new AccServicesClass();
	private TransactionsDao tt = new TransactionsDao();
	public static void logInfo(String message) {
		AppLogger.logger.info(message);
	}
	
	
	@Override
		public User login(Scanner scan) {
		System.out.println("Choose an Option\n1. Login as a Customer\n2. Login as an Employee");
		int uOption = scan.nextInt();
		scan.nextLine();
		if(uOption == 1) {
			System.out.println("Welcome Customer!\n__________\nEnter your Username: ");
			String username = scan.nextLine();
			System.out.println("Enter your Password: ");
			String password = scan.nextLine();
			return ua.getUser(username, password);
			
		}
		if(uOption == 2) {
		
		System.out.println("Welcome Employee!\n__________\nEnter your Username: ");
		String username = scan.nextLine();
		System.out.println("Enter your Password: ");
		String password = scan.nextLine();
		return ua.getUser(username, password);
		
											}
		AppLogger.logger.info("User Logged in");
		return null;
	}
	

	
	@Override
	public void customerMenu(Scanner scan, User u) {
		List<Transaction> allTransactions = tt.getAllTransactions();
		List<Transaction> possibleTransfers = new ArrayList<>();
		List<Account> yourAccounts = aa.getAccounts(u.getUser_id());
		List<Account> accountstobeapproved = aa.getAccounts(u.getUser_id());
		int acctobeinput;
		for (Transaction transfer : allTransactions) {
			if (transfer.getTransaction_type().equals("TransfertoUserId")) {
				possibleTransfers.add(transfer);
			}
		}
		
		for (Account a : yourAccounts) {
			int checkid = a.getId();
			for (Transaction trans : possibleTransfers) {
				if (checkid == trans.getUser_id()) {
					acctobeinput = trans.getUser_id();
					
					
					accountstobeapproved.add(aa.getAccount(acctobeinput, u.getUser_id()));
					Account accounttobeupdated = aa.getAccount(acctobeinput, u.getUser_id());
					System.out.println(trans.getFirstName() + " " + trans.getLastName() 
					+ "\n sent $" + trans.getTransaction_amount() + " to your " + accounttobeupdated.getType() + "Account with id of " + trans.getUser_id());
					System.out.println(" Would you like to accept the money? (y/n)");
					String answer = scan.nextLine();
						scan.nextInt();
					if (answer == "y") {
						boolean checkyou = aa.accountUpdate(acctobeinput, (accounttobeupdated.getBalance() + trans.getTransaction_amount()));
						if (checkyou) {System.out.println("Your account is successfully updated");}else {System.out.println("Database update unsuccessful");}
						List<User> allUsers = ua.getAllUsers();
						AppLogger.logger.info("User Approved transfer and deposited sent money");
						for (User us: allUsers) {
							if (us.getFirstName().equals(trans.getFirstName()) & us.getLastName().equals(trans.getLastName())){
								User uss = us;
								int accid = trans.getAccount_id();
								Account blah = aa.getAccount(accid, uss.getUser_id());
								boolean check = aa.accountUpdate(accid, (blah.getBalance() - trans.getTransaction_amount()));
								Transaction tp = new Transaction (uss.getUser_id(), "transferapproveddecrease", uss.getFirstName(),uss.getLastName(), trans.getTransaction_amount(), accid);
								tt.addTransaction(tp);
								AppLogger.logger.info(" The money sent was taken out of senders account");
								if (check) {System.out.println("Senders account successfully updated");}else {System.out.println("Database update unsuccessful");}
							}
						}
					}
					
					}}
				}
			
		
		
		boolean cmRun = true;
		while (cmRun) {
		System.out.println("Welcome Customer!\nHow can we help? Input a number from Options below\n" 
				+ "1.Account Options\n2. Send Money\n 3. Deposit/Withdrawal\n4. Logout to Main Menu");
		Integer uOption = scan.nextInt();
		switch (uOption) {
		case 1:  {
			as.accountOptions(scan, u);
			
			break;
		}
		case 2: {
			ts.transfer(scan, u);
			break;
		}
		case 3: {
			as.depositWithdrawal(scan, u);
			break;
		}
		case 4: {
			cmRun = false;
		}
			}
		}
		
	}

	@Override
	public void employeeMenu(Scanner scan, User u) {
	boolean emRun = true;
	System.out.println("Welcome Employee " + u.getFirstName() + " " + u.getLastName());
	while (emRun) {
		System.out.println("Choose an Option\n1. Approve or Deny pending Accounts\n2. View Customer Accounts and Transactions\n3. Logout to Main Menu ");
		int input = scan.nextInt();
		switch (input) {
		
		case 1: {
			System.out.println("These are the new Accounts that require approval!");
			as.approvalOfAccount(scan, u);
			break;
		}
		
		case 2: {
			System.out.println("Would you like to View Transactions?\n1. View Customer Transactions.\n2. View all Transactions.");
			int newinput = scan.nextInt();
			List<User> allUsers = ua.getAllUsers();
			List<Transaction> allTransactions = tt.getAllTransactions();
			switch(newinput) {
			case 1:{
				//display all customers names and id's
				System.out.println("Here are all the Customer Transactions");
				
				for (User us: allUsers) {
					if (us.getType().equals("Customer")) {
						System.out.println("Customer Name: " + us.getFirstName() + " " + us.getLastName() 
											+ "Customer Id: " + us.getUser_id());
						for (Transaction at : allTransactions) {
					if (at.getUser_id() == us.getUser_id()) {
						System.out.println("Customer Transaction Id: " + at.getTransaction_id() + " Customer Id: " + at.getUser_id() 
						+ "\n Type of Transaction: " + at.getTransaction_type() + " Transaction Amount: " + at.getTransaction_amount() + " at time: " + at.getDateTime());
					}
				}
					}
				}
				
				AppLogger.logger.info("Employee checked all customer Transactions and Accounts");
						}
			
			case 2:{
				System.out.println("Here are all the accounts and transactions! Good Luck");
				for (User us: allUsers) {
					
						System.out.println(" User's Full Name: " + us.getFirstName() + " " + us.getLastName() 
											+ " Id: " + us.getUser_id());
						for (Transaction at : allTransactions) {
					
						System.out.println("Transaction Id: " + at.getTransaction_id() + " User Id: " + at.getUser_id() 
						+ "\n Type of Transaction: " + at.getTransaction_type() + " Transaction Amount: " + at.getTransaction_amount() + " at time: " + at.getDateTime());
					
				
					}
				}
				AppLogger.logger.info("Employee checked all of the Transactions and Accounts");
				break;
					}
			default: {System.out.println("Input doesn't exist. Please input an option from the table.");}
								
									}
			break;
						}
		case 3: {
			emRun = false;
		}
		default: {System.out.println("That option doesn't exist");
		
						}
					}
				}
		
								}


	
	@Override
	public void register(Scanner scan) {
		User newUser = new User();
		
		System.out.println("Are you Registering as: \n1. A Customer or 2. An Employee?");
		int regOptionOne = scan.nextInt();
		scan.nextLine();
			
		switch (regOptionOne) {
		
		case 1: {
			
			
			newUser.setType("Customer");
			
				System.out.println("Input your FirstName:");
				String firstName = scan.nextLine();
				System.out.println("Input your Last Name:");
				String lastName = scan.nextLine();
				System.out.println("Input a Username:");
				String uName = scan.nextLine();
				System.out.println("Is this Correct? (y/n)\n" + "You inputted: "    
						+ "Full Name: " + firstName + " " + lastName + "\nYour Username: " + uName  );
				String yayornay = scan.nextLine();
				
				if 	(yayornay.equals("y")) {
					
					newUser.setFirstName(firstName);
					newUser.setLastName(lastName);
					newUser.setUsername(uName);
					ua.addUser(newUser);	}
				
				else if (yayornay.equals("n")) {	
					
					
				} else { 
			
					System.out.println("Input was neither y or n. Please Reenter information.");			}
			
		
		
		AppLogger.logger.info("New Customer registered for a new Profile");
		break;
		
		}
		
			case 2: {
				String employeeId = "employeeid";
				
				User ue = new User();
				
				boolean erun = true;
				
				
				System.out.println("Please enter employee verification Id:");
				String employeeverifyId = scan.nextLine();
				if (employeeverifyId=="employeeid" ) {
					
					//boolean pwrdrun = true;
					//run user info loop
					
					
					ue.setType("Employee");
				while (erun) {	
					
					System.out.println("Input your FirstName:");
					String firstName = scan.nextLine();
					System.out.println("Input your Last Name:");
					String lastName = scan.nextLine();
					System.out.println("Input a Username:");
					String uName = scan.nextLine();
					System.out.println("Is this Correct? (y/n)\n" + "You inputted: "    
							+ "Full Name: " + firstName + " " + lastName + "\nYour Username: " + uName  );
					String yayornay = scan.nextLine();
					
					if 	(yayornay==("y")) {
						System.out.println(" It worked");
						erun = false;
						newUser.setFirstName(firstName);
						newUser.setLastName(lastName);
						newUser.setUsername(uName);
					}
					
					else if (yayornay.equals("n")) {	
						
						System.out.println("Please re-enter info");
								} 
					else
								{
						System.out.println("The information you inputted was incorrect");
						
									}
					}
				
					
					
					boolean pwrdrun = true;
					
					//run user password loop
					
							System.out.println("Input a Password:");
							String pword = scan.nextLine();
							System.out.println("Confirm Password is correct\nRe-enter Password:");
							String pword2 = scan.nextLine();
						if (pword.equals(pword2)) {
							ue.setPassword(pword2);
							pwrdrun = false;
							System.out.println("Password input Successful\n" );
						} else {
							pwrdrun = true;
							System.out.println("Passwords do not Match\nPlease try again!");
							
							}
					 
					
				} else {
					System.out.println("You've inputed the Employee Verification code Incorrectly!\n" 
				+ "Try to Register again!");	
					
						}	
				ua.addUser(ue);
				
				AppLogger.logger.info("Employee registered for a Profile");
				break;
			
	}
			
		default: {System.out.println("Incorrect input please enter a Number");}
		}
	}	

		

	


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((aa == null) ? 0 : aa.hashCode());
		result = prime * result + ((tt == null) ? 0 : tt.hashCode());
		result = prime * result + ((ua == null) ? 0 : ua.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserService other = (UserService) obj;
		if (aa == null) {
			if (other.aa != null)
				return false;
		} else if (!aa.equals(other.aa))
			return false;
		if (tt == null) {
			if (other.tt != null)
				return false;
		} else if (!tt.equals(other.tt))
			return false;
		if (ua == null) {
			if (other.ua != null)
				return false;
		} else if (!ua.equals(other.ua))
			return false;
		return true;
	}



	
	}
	
