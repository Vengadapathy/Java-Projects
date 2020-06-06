package Objects;

import java.util.ArrayList;

public class Account {

	long AccountNo;
	String accountType;
	Double balance;
	Double amountWithdrawn ;	
	User user;
	ArrayList<String> transactionList = null;

	public Account(long accountNo,String accountType, double balance, User user) {
		this.AccountNo = accountNo;
		this.accountType = accountType;
		this.balance = balance;
		this.user = user;
		this.amountWithdrawn = 0.0d ;
	}

	public long getAccountNo() {
		return AccountNo;
	}

	public void setAccountNo(long accountNo) {
		AccountNo = accountNo;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setAmountWithdrawn(Double amountWithdrawn) {
		this.amountWithdrawn = amountWithdrawn;
	}

	public Double getAmountWithdrawn() {
		return amountWithdrawn;
	}

}
