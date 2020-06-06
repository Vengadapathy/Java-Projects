package ATM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

import Objects.ATM_Machine;
import Objects.Account;
import Objects.AtmCard;
import Objects.Transaction;
import SQL.operations.DatabaseOperations;

public abstract class ATM_Operations implements ATM_OperationsInterface {
	
	public static final int DEPOSIT = 1;
	public static final int WITHDRAW = 2 ;
	public static final int FUNDTRANSFER = 3 ;
	
	ATM_Machine atm1=null  ; 
	DatabaseOperations databaseOperations = null ;
	Scanner input = null ; 
	
	public void initialize(){
		databaseOperations = new DatabaseOperations();
		databaseOperations.getConnection();
		 atm1 = databaseOperations.getATM(100);
		 input = new Scanner(System.in);
	}
	
	public void closeConnection() {
		try {
			DatabaseOperations.connection.close();
			databaseOperations = null ;
			input=null;
		} catch (SQLException e) {
			System.out.println("Connection closed");
			e.printStackTrace();
		}
	}
	
	public boolean requestDeposit(Transaction transaction) throws SQLException {
		if(transaction.getAmount() <= depositLimit) {
			return deposit(transaction);
		} else {
			System.out.println("Deposit Limit Exceeded !!! ");
			return false ;
		}
	}

	public boolean deposit(Transaction transaction) throws SQLException {
		
			databaseOperations.setTransaction(transaction.getFromAccount().getAccountNo() , transaction.getToAccount() ,transaction.getFromAccount().getUser().getUserID(), transaction.getTransactionProcess() ,transaction.getAmount() , transaction.getTransactionType(), transaction.getAtm() );
			databaseOperations.setBalance(transaction.getFromAccount().getAccountNo(), transaction.getAmount(), CREDIT);
			databaseOperations.setAmountRemaining(atm1.getAmountRemaining() - transaction.getAmount() ,atm1.getatmId());
	
			( transaction.getFromAccount() ).setBalance( transaction.getFromAccount().getBalance() + transaction.getAmount());
			transaction.getAtm().setAmountRemaining( transaction.getAtm().getAmountRemaining() + transaction.getAmount()  );
			System.out.println("Cash Deposited successfully ");
			return true ;
	}
	
	public boolean requestWithdraw(Transaction transaction) throws SQLException {
		boolean status = false ;
		if( (transaction.getFromAccount().getBalance() >= transaction.getAmount() ) && withdrawLimit >= transaction.getAmount() && ( transaction.getFromAccount().getAmountWithdrawn() + transaction.getAmount() ) <= withdrawLimit ) {
			status =  withdraw(transaction);
		} else if( transaction.getFromAccount().getBalance() < transaction.getAmount()) {
			System.out.println("Insufficient Balance !!! ");
		} else {
			System.out.println("Exceeding Maximum withdraw limit !!! ");
		}
		return status ;
	}
	
	public boolean withdraw(Transaction transaction) throws SQLException {	

			databaseOperations.setTransaction(transaction.getFromAccount().getAccountNo() , transaction.getToAccount() ,transaction.getFromAccount().getUser().getUserID(), transaction.getTransactionProcess() ,transaction.getAmount() , transaction.getTransactionType(), transaction.getAtm() );
			databaseOperations.setBalance(transaction.getFromAccount().getAccountNo(), transaction.getAmount(), DEBIT);
			databaseOperations.setAmountRemaining(atm1.getAmountRemaining(),atm1.getatmId());
		
			( transaction.getFromAccount() ).setBalance(transaction.getFromAccount().getBalance() - transaction.getAmount() );
			updateAmountWithdrawn(transaction);
//			addTransaction(transaction);
			transaction.getAtm().setAmountRemaining( transaction.getAtm().getAmountRemaining() - transaction.getAmount()  );
			System.out.println("Amount Withdrawn successfully");
			return true ;
	}

	public boolean initiateFundTransfer(Transaction transaction) throws SQLException {
		boolean status = requestWithdraw(transaction);
		databaseOperations.setBalance(transaction.getToAccount(), transaction.getAmount(), CREDIT);
		databaseOperations.setFundTransferTransaction(transaction.getToAccount() , transaction.getFromAccount().getAccountNo(), transaction.getFromAccount().getUser().getUserID(), transaction.getTransactionProcess() ,transaction.getAmount() , "CREDIT" , transaction.getAtm() );
		System.out.println("Fund transfer Success");
		return status ;
	}
	
	public void balanceEnquiry(Account account) {
		System.out.println("Available balance in your account is : " + databaseOperations.getBalance(account.getAccountNo()));
	}

	public ArrayList<HashMap<String,String>> miniStatement(Account account) {
		//display the transaction list from the database 
		return databaseOperations.getTransactionList(account.getAccountNo());
	}

	public abstract boolean pinChange(AtmCard atmcard);
	
	public void updateAmountWithdrawn(Transaction transaction) {
		transaction.getFromAccount().setAmountWithdrawn( transaction.getFromAccount().getAmountWithdrawn() + transaction.getAmount() ) ;
//		jdbc.updateAmountWithdrawn(transaction.getAccount().getAccountNo(),transaction.getAmount());
	}
}

class InsufficientAmountException extends Throwable{
	
	private static final long serialVersionUID = 1L;

	public String toString() {	
		return(" Withdraw failed !!! Insufficient Amount in ATM");
	}
}
