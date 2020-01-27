package Transaction;

import Objects.Transaction;

public abstract class TransactionAbstract {
	Transaction transaction ;
	public static final double withdrawLimit = 25000.00d ;
	
	TransactionAbstract(Transaction transaction){
		this.transaction = transaction ;		
	}
	
	public abstract void requestTransaction();
	
	public void addTransaction() {
		addTransaction(transaction);
	}
	
	public abstract void atmCashUpdate();
	public void addTransaction(Transaction transaction) {
		String details = transaction.getDate()+" -------------  "+transaction.getTransactionType()+" ----------- "+transaction.getAmount()+"-----------"+transaction.getAtm().getBranchCode()+" ----------- "+transaction.getAtm().getLocation();
		( transaction.getAccount().getTransactionList() ).add(details);
	}
}
