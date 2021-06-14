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
		int uOption = Integer.parseInt(scan.nextLine());
		if(uOption == 1) {
			System.out.println("Welcome Customer!\n__________\nEnter your Username: ");
			String username = scan.nextLine();
			System.out.println("Enter your Password: ");
			String password = scan.nextLine();
			List<User> allusers = ua.getAllUsers();
			for(User user : allusers) {
				if(user.getPassword().equals(password) & user.getUsername().equals(username)) {
					return ua.getUser(username, password);
					
				}
			}
			
			
		}
		if(uOption == 2) {
		
		System.out.println("Welcome Employee!\n__________\nEnter your Username: ");
		String username = scan.nextLine();
		System.out.println("Enter your Password: ");
		String password = scan.nextLine();
		List<User> allusers = ua.getAllUsers();
		for(User user : allusers) {
			if(user.getPassword().equals(password) & user.getUsername().equals(username)) {
				return ua.getUser(username, password);
				
			}
		}
		
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
			if (transfer.getTransaction_type().equals("TransfertoUserAccount")) {
				possibleTransfers.add(transfer);
			}
		}
		
		for (Account a : yourAccounts) {
			
			for (Transaction trans : possibleTransfers) {
				if (a.getId() == trans.getAccount_id()) {
					acctobeinput = trans.getAccount_id();
					//acctobeinput is your account
//					int senderaccount = Integer.parseInt(trans.getTransaction_type());
					accountstobeapproved.add(aa.getAccount(acctobeinput, u.getUser_id()));
					Account accounttobeupdated = aa.getAccount(acctobeinput, u.getUser_id());
					System.out.println(trans.getFirstName() + " " + trans.getLastName() 
					+ "\n sent $" + trans.getTransaction_amount() + " to your " + accounttobeupdated.getType() + "Account with id of " + acctobeinput);
					System.out.println(" Would you like to accept the money? (y/n)");
					String answer = scan.nextLine();
						
					if (answer.equals("y")) {
						boolean checkyou = aa.accountUpdate(acctobeinput, (accounttobeupdated.getBalance() + trans.getTransaction_amount()));
							if (checkyou) {System.out.println("Your account is successfully updated");}else {System.out.println("Database update unsuccessful");}
						List<User> allUsers = ua.getAllUsers();
						AppLogger.logger.info("User Approved transfer and deposited sent money");
						for (User us: allUsers) {
							if (us.getFirstName().equals(trans.getFirstName()) & us.getLastName().equals(trans.getLastName())){
								User uss = us;
								int accid = trans.getUser_id();
								Account blah = aa.getAccount(accid, uss.getUser_id());
								boolean check = aa.accountUpdate(accid, (blah.getBalance() - trans.getTransaction_amount()));
								Transaction tp = new Transaction (uss.getUser_id(), "transferapprovedbyReceiver", uss.getFirstName(),uss.getLastName(), trans.getTransaction_amount(), accid);
								tt.addTransaction(tp);
								AppLogger.logger.info(" The money sent was taken out of senders account");
								if (check) {System.out.println("Senders account successfully updated");}else {System.out.println("Database update unsuccessful");}
								tt.deleteTransaction(trans.getTransaction_id());
							}
						}
					}else if (answer.equals("n")) {
						System.out.println("Transfer deposit denied");
						tt.deleteTransaction(trans.getTransaction_id());
						//tt.remove method for a transaction
					} else {System.out.println("Input incorrect. Transfer approval cancelled");}
					
					}}
				}
			
		
		
		boolean cmRun = true;
		while (cmRun) {
		 
		System.out.println("\nWelcome Customer!\nHow can we help? Input a number from Options below\n" 
				+ "1. Account Options\n2. Send Money\n3. Deposit/Withdrawal\n4. Logout to Main Menu");
		int uOption = Integer.parseInt(scan.nextLine());
		switch (uOption) {
		case 1:  {
			as.accountOptions(scan, u);
			
			break;
		}
		case 2: {
			ts.requestTransfer(scan, u);
			break;
		}
		case 3: {
			as.depositWithdrawal(scan, u);
			break;
		}
		case 4: {
			cmRun = false;
			break;
		}
			}
		}
		
	}

	@Override
	public void employeeMenu(Scanner scan, User u) {
	boolean emRun = true;
	System.out.println("Welcome Employee " + u.getFirstName() + " " + u.getLastName());
	while (emRun) {
		System.out.println("\nChoose an Option\n1. Approve or Deny pending Accounts\n2. View Customer Accounts and Transactions\n3. Logout to Main Menu ");
		int input = Integer.parseInt(scan.nextLine());
		switch (input) {
		
			case 1: {
				System.out.println("These are the new Accounts that require approval!");
				as.approvalOfAccount(scan, u);
				break;
		}
		
			case 2: {
				System.out.println("Would you like to View Transactions?\n1. View Customer's Accounts and Transactions.\n2. View all Transactions.");
				int newinput = Integer.parseInt(scan.nextLine());
				List<User> allUsers = ua.getAllUsers();
				List<Transaction> allTransactions = tt.getAllTransactions();
				List<Account> allAccounts = aa.getAllAccounts(); 
				switch(newinput) {
					case 1:{
						//display all customers names and id's
						System.out.println("Here are all the Customer Transactions");
				
						for (User us: allUsers) {
							if (us.getType()==null) {
								us.setType("N/A");
								
							}
							if (us.getType().equals("Customer")) {
									System.out.println("\nCustomer Name: " + us.getFirstName() + " " + us.getLastName() 
											+ " Customer Id: " + us.getUser_id() + " \n");
									for (Account ac : allAccounts) {
										if (ac.getUser_id() == us.getUser_id()) {
											System.out.println(ac.toString());
										}
									}
									for (Transaction at : allTransactions) {
										if (at.getUser_id() == us.getUser_id() & at.getFirstName().equals(us.getFirstName())) {
											System.out.println("Customer Transaction Id: " + at.getTransaction_id() + " Customer Id: " + at.getUser_id() 
											+ "\n Type of Transaction: " + at.getTransaction_type() + " Transaction Amount: " + at.getTransaction_amount() + " at time: " + at.getDateTime());
					}
				}
					}
				}
				
				AppLogger.logger.info("Employee checked all customer Transactions and Accounts");
						break;}
			
					case 2:{
							System.out.println("Here are all the accounts and transactions! Good Luck");
							for (User us: allUsers) {
					
								System.out.println(" User's Full Name: " + us.getFirstName() + " " + us.getLastName() 
											+ " Id: " + us.getUser_id() + " Time");
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
			break;
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
		int regOptionOne = Integer.parseInt(scan.nextLine());
			
		switch (regOptionOne) {
		//case 1 involves registration of Customers.
		case 1: {	newUser.setType("Customer");
			System.out.println("Welcome Customer! Please input informaton");
			boolean crun = true;
			
			boolean ucnamerun = true;
			boolean cpwrdrun = true;
			while (crun) {
				while (ucnamerun) {
			
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
					ucnamerun = false;
						} else if (yayornay.equals("n")) {
							System.out.println("Please re-ente information");
						} else { System.out.println("Input was neither y or n. Please Reenter information.");}
				}
					
				while (cpwrdrun) {
					System.out.println("Input a Password:");
					String pword = scan.nextLine();
					System.out.println("Confirm Password is correct\nRe-enter Password:");
					String pword2 = scan.nextLine();
				if (pword.equals(pword2)) {
					newUser.setPassword(pword2);
					cpwrdrun = false;
					crun = false;
					System.out.println("Password input Successful\n" );
					boolean success = ua.addUser(newUser);
					if (success) {
						System.out.println("Congrats " + newUser.getFirstName() + " " + newUser.getLastName() + "! You've successfully registered");
					AppLogger.logger.info("New Customer registered for a new Profile");
					
					}else { System.out.println("Information upload unsuccessful. Please Try again");} 
				}else {
					System.out.println("Passwords do not Match\nPlease try again!");
					}
								}
				 }
		break;}
				
				
		//Case 2 involves employee verification and registration
		case 2: {
				String employeeId = "employeeid";
				
				
				
				boolean erun = true;
				boolean employeevidrun = true;
				boolean unamerun = true;
				boolean pwrdrun = true;
			while (erun) {	
			while (employeevidrun) {
			
				System.out.println("Please enter employee verification Id:");
				String employeeverifyId = scan.nextLine();
				if (employeeverifyId.equals(employeeId)) {
					newUser.setType("Employee");
					System.out.println("This is correct Welcome to the System Employee");
					employeevidrun = false;
					//boolean pwrdrun = true;
					//run user info loop
				} else {
							System.out.println("You've inputed the Employee Verification code Incorrectly!\n" 
						+ "Please try the verification again!");	
						
										}
									}	
					
					
				while (unamerun) {	
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
						System.out.println("User info saved ");
						newUser.setFirstName(firstName);
						newUser.setLastName(lastName);
						newUser.setUsername(uName);
						unamerun = false;
					} else if (yayornay.equals("n")) {	
						System.out.println("Please re-enter info");
						
					} else {
						System.out.println("The information you inputted was incorrect please input info again!");
						
									}
					}
				
					
					//place line ui print
					
					
					//run user password loop
					while (pwrdrun) {
							System.out.println("Input a Password:");
							String pword = scan.nextLine();
							System.out.println("Confirm Password is correct\nRe-enter Password:");
							String pword2 = scan.nextLine();
						if (pword.equals(pword2)) {
							newUser.setPassword(pword2);
							System.out.println("Password input Successful\n" );
							boolean customersuccess = ua.addUser(newUser);
							if (customersuccess) {
								System.out.println("Information saved! Congrats " + newUser.getFirstName() + " " + newUser.getLastName() + "! You've registered as an employee!");
							pwrdrun = false;
							erun = false;
							AppLogger.logger.info("Employee registered for a Profile");
							break;
							} else if (customersuccess == false) {
								System.out.println("Information was not submitted properly, please try again");
							}
						} else {
							System.out.println("Passwords do not Match\nPlease try again!");
							}
					 
					
				}
				
			}				
break;	}
			
		
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
	
