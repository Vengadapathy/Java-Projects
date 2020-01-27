package Transaction;

import Objects.Transaction;

public class CashWithdrawl extends TransactionAbstract {
	
		public CashWithdrawl(Transaction transaction) {
			super( transaction ) ;
		}

		public void requestTransaction() {	
			( transaction.getAccount() ).setBalance(transaction.getAccount().getBalance() - transaction.getAmount() );
			updateWithdrawLimit();
			addTransaction();
			atmCashUpdate();
			System.out.println("Amount Withdrawn successfully");
		}
		
		public void checkBalance() {
			if( (transaction.getAccount().getBalance() >= transaction.getAmount() ) && withdrawLimit >= transaction.getAmount() && transaction.getAccount().getAmountWithdrawn() <= transaction.getAmount() )
				requestTransaction();
			else if( transaction.getAccount().getBalance() < transaction.getAmount())
				System.out.println("Insufficient Balance !!! ");
			else
				System.out.println("Maximum withdraw limit reached !!! ");
		}
		
		public void atmCashUpdate() {
				transaction.getAtm().setAmountRemaining( transaction.getAtm().getAmountRemaining() - transaction.getAmount()  );	
		}
		
		public void updateWithdrawLimit() {
			transaction.getAccount().setAmountWithdrawn( transaction.getAccount().getAmountWithdrawn() + transaction.getAmount() ) ;
		}
		
}