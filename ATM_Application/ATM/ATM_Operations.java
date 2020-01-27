package ATM;

import java.util.Scanner;

import Objects.ATM_Machine;
import Objects.Account;
import Objects.Transaction;
import otherOperations.AccountAuthentication;

public abstract class ATM_Operations implements ATM_OperationsInterface {
	
	ATM_Machine atm1 ; 
	Account account ;
	AccountAuthentication authentication ;
	Transaction transaction ;
	Scanner input ;
	
	public ATM_Operations(){
		atm1 = new ATM_Machine("Chennai guduvancherry", "BANK00123" , "Hybrid" , 5000.00d , true , false);
		authentication = new AccountAuthentication();
		AccountAuthentication.initializeAccounts();
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
	}

	public void balanceEnquiry() {
		System.out.print("Available balance in your account is "+account.getBalance()+"\n");
	}

	public void miniStatement() {
		int count = 0 , size = ( account.getTransactionList() ).size() ;
		for(String statement : account.getTransactionList() ) {
			System.out.println((account.getTransactionList()).get(size-1-count));
			count++;
		}
	}

	public abstract void pinChange();
	
	public void addTransaction() {
		String details = transaction.getDate()+" -------------  "+transaction.getTransactionType()+" ----------- "+transaction.getAmount()+"-----------"+transaction.getAtm().getBranchCode()+" ----------- "+transaction.getAtm().getLocation();
		( transaction.getAccount().getTransactionList() ).add(details);
	}
	
	public void updateAmountWithdrawn() {
		transaction.getAccount().setAmountWithdrawn( transaction.getAccount().getAmountWithdrawn() + transaction.getAmount() ) ;
	}
	
}

class InsufficientAmountException extends Throwable{
	public String toString() {	
		return(" Withdraw failed !!! Insufficient Amount in ATM");
	}
}
