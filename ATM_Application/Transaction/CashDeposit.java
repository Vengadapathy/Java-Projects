package Transaction;

import Objects.Transaction;


public class CashDeposit extends TransactionAbstract {
	
	public CashDeposit(Transaction transaction) {
		super( transaction );
	}
	
	public void requestTransaction() {
	
		( transaction.getAccount() ).setBalance( transaction.getAccount().getBalance() + transaction.getAmount());
		addTransaction();
		atmCashUpdate();
		System.out.println("Cash Deposited successfully ");
			
	}
	
	public void atmCashUpdate() {
		
		transaction.getAtm().setAmountRemaining( transaction.getAtm().getAmountRemaining() + transaction.getAmount()  );
		
	}
}