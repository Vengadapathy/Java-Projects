package ATM;

import java.util.Scanner;

import Objects.ATM_Machine;
import Objects.Account;
import Objects.Transaction;
import SQL.operations.JDBC;

public abstract class ATM_Operations implements ATM_OperationsInterface {
	
	ATM_Machine atm1 ; 
	Account account ;
	Transaction transaction ;
	JDBC jdbc ;
	Scanner input ;
	
	public ATM_Operations(){
		jdbc = new JDBC();
		jdbc.getConnection();
		// get atm object from the atm table in the atm database
		 atm1 = jdbc.getATM(100);
		input = new Scanner(System.in);
	}
				
	public void requestDeposit() {
		if(transaction.getAmount() <= depositLimit) {
			 deposit();
		} else {
			System.out.println("Deposit Limit Exceeded !!! ");
		}
	}

	public void deposit() {
		( transaction.getAccount() ).setBalance( transaction.getAccount().getBalance() + transaction.getAmount());
		addTransaction();
		transaction.getAtm().setAmountRemaining( transaction.getAtm().getAmountRemaining() + transaction.getAmount()  );
		System.out.println("Cash Deposited successfully ");
		
		jdbc.setBalance(transaction.getAccount().getAccountNo(), transaction.getAccount().getBalance());

		jdbc.setTransaction(transaction.getAccount(),transaction.getAmount(),transaction.getTransactionType(),transaction.getAtm());
	}
	
	public void requestWithdraw() {
		if( (transaction.getAccount().getBalance() >= transaction.getAmount() ) && withdrawLimit >= transaction.getAmount() && ( transaction.getAccount().getAmountWithdrawn() + transaction.getAmount() ) <= withdrawLimit ) {
			withdraw();
		} else if( transaction.getAccount().getBalance() < transaction.getAmount()) {
			System.out.println("Insufficient Balance !!! ");
		} else {
			System.out.println("Exceeding Maximum withdraw limit !!! ");
			}
	}
	
	public void withdraw() {	
		( transaction.getAccount() ).setBalance(transaction.getAccount().getBalance() - transaction.getAmount() );
		updateAmountWithdrawn();
		System.out.println(transaction.getAccount().getAmountWithdrawn());
		addTransaction();
		transaction.getAtm().setAmountRemaining( transaction.getAtm().getAmountRemaining() - transaction.getAmount()  );
		System.out.println("Amount Withdrawn successfully");
		
		jdbc.setBalance(transaction.getAccount().getAccountNo(), transaction.getAccount().getBalance());
		jdbc.setAmountRemaining(atm1.getAmountRemaining(),atm1.getatmId());
		jdbc.setTransaction(transaction.getAccount(),transaction.getAmount(),transaction.getTransactionType(),transaction.getAtm());

	}

	public void balanceEnquiry() {
		jdbc.getBalance(account.getAccountNo());
	}

	public void miniStatement() {
		//display the transaction list from the database 
		jdbc.getTransactionList(account.getAccountNo());
	}

	public abstract void pinChange();
	
	public void addTransaction() {
		String details = transaction.getDate()+" -------------  "+transaction.getTransactionType()+" ----------- "+transaction.getAmount()+"-----------"+transaction.getAtm().getBranchCode()+" ----------- "+transaction.getAtm().getLocation();
		( transaction.getAccount().getTransactionList() ).add(details);
	}
	
	public void updateAmountWithdrawn() {
		transaction.getAccount().setAmountWithdrawn( transaction.getAccount().getAmountWithdrawn() + transaction.getAmount() ) ;
		//jdbc.updateAmountWithdrawn(transaction.getAccount().getAccountNo(),transaction.getAmount());
	}
}

class InsufficientAmountException extends Throwable{
	public String toString() {	
		return(" Withdraw failed !!! Insufficient Amount in ATM");
	}
}
