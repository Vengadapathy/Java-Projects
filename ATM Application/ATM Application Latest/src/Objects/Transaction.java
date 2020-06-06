package Objects;

import java.util.Date;

public class Transaction {

	Account fromAccount;
	long toAccount ;
	double amount;
	Date date ;
	int transactionProcess ;
	String transactionType;
	ATM_Machine atm;

	public Transaction(Account fromAccount,long toAccount, double amount,int transactionProcess,String transactionType, ATM_Machine atm) {
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.amount = amount;
		this.transactionProcess = transactionProcess ;
		this.date = new Date();
		this.transactionType = transactionType;
		this.atm = atm;
	}

	public Account getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(Account fromAccount) {
		this.fromAccount = fromAccount;
	}

	public long getToAccount() {
		return toAccount;
	}

	public void setToAccount(long toAccount) {
		this.toAccount = toAccount;
	}

	public int getTransactionProcess() {
		return transactionProcess;
	}

	public void setTransactionProcess(int transactionProcess) {
		this.transactionProcess = transactionProcess;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}

	public void setAtm(ATM_Machine atm) {
		this.atm = atm;
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
