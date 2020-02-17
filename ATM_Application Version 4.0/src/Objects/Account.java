package Objects;

import java.util.ArrayList;

public class Account {

	long AccountNo;
	String accountType;
	Integer PIN;
	Double balance;
	Double amountWithdrawn ;	
	User user;
	ArrayList<String> transactionList = null;

	public Account(long accountNo,String accountType, int pin , double balance, User user) {
		this.AccountNo = accountNo;
		this.accountType = accountType;
		this.PIN = pin;
		this.balance = balance;
		this.user = user;
		this.transactionList = new ArrayList<String>();
		this.amountWithdrawn = 0.0d ;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public void setPIN(Integer pIN) {
		PIN = pIN;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public void setAmountWithdrawn(Double amountWithdrawn) {
		this.amountWithdrawn = amountWithdrawn;
	}

	public long getAccountNo() {
		return AccountNo;
	}

	public void setBalance(double balance) {
		this.balance = balance;
	}

	public int getPIN() {
		return PIN;
	}

	public void setAccountNo(long accountNo) {
		AccountNo = accountNo;
	}

	public void setPIN(int pin) {
		this.PIN = pin;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setTransactionList(ArrayList<String> transactionList) {
		this.transactionList = transactionList;
	}

	public double getBalance() {
		return balance;
	}

	public User getUser() {
		return user;
	}

	public ArrayList<String> getTransactionList() {
		return transactionList;
	}

	public void setAmountWithdrawn(double amount) {
		this.amountWithdrawn = amount ;
	}
	
	public double getAmountWithdrawn() {
		return amountWithdrawn;
	}
}
