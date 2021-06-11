package com.revature.items;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; 

public class Transaction {

	private int user_id;
	private String transaction_type;
	private String firstName;
	private String lastName;
	private double transaction_amount;
	public int account_id;
	private int transaction_id;
	private String datetime;
	
	
	
	public Transaction(int user_id, String transaction_type, String firstName, String lastName,
			double transaction_amount, int account_id) {
		super();
		this.user_id = user_id;
		this.transaction_type = transaction_type;
		this.firstName = firstName;
		this.lastName = lastName;
		this.transaction_amount = transaction_amount;
		this.account_id = account_id;
		
		
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDate = myDateObj.format(myFormatObj);
		datetime = formattedDate;
		
	}


	public Transaction() {
		super();
		LocalDateTime myDateObj = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		String formattedDate = myDateObj.format(myFormatObj);
		datetime = formattedDate;
		
	}


	public int getAccount_id() {
		return account_id;
	}


	public void setAccount_id(int account_id) {
		this.account_id = account_id;
	}


	public int getUser_id() {
		return user_id;
	}


	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}


	public String getTransaction_type() {
		return transaction_type;
	}


	public void setTransaction_type(String transaction_type) {
		this.transaction_type = transaction_type;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public double getTransaction_amount() {
		return transaction_amount;
	}


	public void setTransaction_amount(double transaction_amount) {
		this.transaction_amount = transaction_amount;
	}


	public int getTransaction_id() {
		return transaction_id;
	}


	public void setTransaction_id(int transaction_id) {
		this.transaction_id = transaction_id;
	}


	public String getDateTime() {
		return datetime;
	}


	public void setDateTime(String datetime) {
		this.datetime = datetime;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((firstName == null) ? 0 : firstName.hashCode());
		result = prime * result + ((lastName == null) ? 0 : lastName.hashCode());
		result = prime * result + ((datetime == null) ? 0 : datetime.hashCode());
		long temp;
		temp = Double.doubleToLongBits(transaction_amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + transaction_id;
		result = prime * result + ((transaction_type == null) ? 0 : transaction_type.hashCode());
		result = prime * result + user_id;
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
		Transaction other = (Transaction) obj;
		if (firstName == null) {
			if (other.firstName != null)
				return false;
		} else if (!firstName.equals(other.firstName))
			return false;
		if (lastName == null) {
			if (other.lastName != null)
				return false;
		} else if (!lastName.equals(other.lastName))
			return false;
		if (datetime == null) {
			if (other.datetime != null)
				return false;
		} else if (!datetime.equals(other.datetime))
			return false;
		if (Double.doubleToLongBits(transaction_amount) != Double.doubleToLongBits(other.transaction_amount))
			return false;
		if (transaction_id != other.transaction_id)
			return false;
		if (transaction_type == null) {
			if (other.transaction_type != null)
				return false;
		} else if (!transaction_type.equals(other.transaction_type))
			return false;
		if (user_id != other.user_id)
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Transaction [ user_id= " + user_id + ", transaction_type= " + transaction_type + ", firstName= " + firstName
				+ ", lastName= " + lastName + "\ntransaction_amount= " + transaction_amount + ", transaction_id= "
				+ transaction_id + ", timestamp= " + datetime + " ]";
	}
	
	
	
}
