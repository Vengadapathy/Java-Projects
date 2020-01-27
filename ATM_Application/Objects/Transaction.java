package Objects;

import java.util.Date;

public class Transaction {

	Account account;
	double amount;
	Date date;
	String transactionType;
	ATM_Machine atm;

	public Transaction(Account account, double amount, String transactionType, ATM_Machine atm) {
		this.account = account;
		this.amount = amount;
		this.date = new Date();
		this.transactionType = transactionType;
		this.atm = atm;
	}

	public Account getAccount() {
		return account;
	}
	
	public double getAmount() {
		return amount;
	}

	public Date getDate() {
		return date;
	}

	public String getTransactionType() {
		return transactionType;
	}

	public ATM_Machine getAtm() {
		return atm;
	}

}
