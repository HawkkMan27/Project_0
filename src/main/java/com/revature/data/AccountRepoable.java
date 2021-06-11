package com.revature.data;

import java.util.List;

import com.revature.items.Account;


public interface AccountRepoable {
	boolean addAccount(Account a);
	public Account getAccount(int id, int user_id);
	List <Account> getAccounts( int user_id);
	boolean approveAccount(int id, int user_id, String approval );
	List <Account> getAllAccounts();
	boolean accountDeposit(int id, double balance);
	boolean accountUpdate(int id, double balance);
	
	boolean removeAccountById(int id, int user_id);
	
}
