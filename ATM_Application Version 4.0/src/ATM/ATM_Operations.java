package ATM;

import java.sql.SQLException;
import java.util.Scanner;

import Objects.ATM_Machine;
import Objects.Account;
import Objects.Transaction;
import SQL.operations.DatabaseOperations;

public abstract class ATM_Operations implements ATM_OperationsInterface {
	
	public static final int DEPOSIT = 1;
	public static final int WITHDRAW = 2 ;
	public static final int FUNDTRANSFER = 3 ;
	
	ATM_Machine atm1=null  ; 
	DatabaseOperations databaseOperations = null ;
	Scanner input = null ; 
	
//	public ATM_Operations(){
//		databaseOperations = new DatabaseOperations();
//		databaseOperations.getConnection();
//		// get atm object from the atm table in the atm database
//		 atm1 = databaseOperations.getATM(101);
//	}
	
	void initialize(){
		databaseOperations = new DatabaseOperations();
		databaseOperations.getConnection();
		 atm1 = databaseOperations.getATM(101);
		 input = new Scanner(System.in);
	}
	public abstract void homeScreen(Account fromAccount) throws InsufficientAmountException;

	public void requestDeposit(Transaction transaction) throws SQLException, InsufficientAmountException {
		if(transaction.getAmount() <= depositLimit) {
			 deposit(transaction);
			 System.out.println("1");
		} else {
			System.out.println("Deposit Limit Exceeded !!! ");
			homeScreen(transaction.getFromAccount());
		}
	}

	public void deposit(Transaction transaction) throws SQLException {
		( transaction.getFromAccount() ).setBalance( transaction.getFromAccount().getBalance() + transaction.getAmount());
//		addTransaction(transaction);
		transaction.getAtm().setAmountRemaining( transaction.getAtm().getAmountRemaining() + transaction.getAmount()  );
//		try {
			DatabaseOperations.connection.setAutoCommit(false);
			databaseOperations.setBalance(transaction.getFromAccount().getAccountNo(), transaction.getFromAccount().getBalance());
			databaseOperations.setAmountRemaining(atm1.getAmountRemaining(),atm1.getatmId());
			databaseOperations.setTransaction(transaction.getFromAccount().getAccountNo() , transaction.getToAccount() ,transaction.getFromAccount().getUser().getUserID(), transaction.getTransactionProcess() ,transaction.getAmount() , transaction.getTransactionType(), transaction.getAtm() );
			DatabaseOperations.connection.commit();
			System.out.println("Cash Deposited successfully ");
//		} catch (SQLException ex) {
//			DatabaseOperations.connection.rollback();
//			System.out.println("Cash Deposit failed");
//		} finally {
//			DatabaseOperations.connection.setAutoCommit(false);
//		}
		}
	
	public void requestWithdraw(Transaction transaction) throws SQLException, InsufficientAmountException {
		if( (transaction.getFromAccount().getBalance() >= transaction.getAmount() ) && withdrawLimit >= transaction.getAmount() && ( transaction.getFromAccount().getAmountWithdrawn() + transaction.getAmount() ) <= withdrawLimit ) {
			withdraw(transaction);
		} else if( transaction.getFromAccount().getBalance() < transaction.getAmount()) {
			System.out.println("Insufficient Balance !!! ");
			homeScreen(transaction.getFromAccount());
		} else {
			System.out.println("Exceeding Maximum withdraw limit !!! ");
			homeScreen(transaction.getFromAccount());
		}
	}
	
	public void withdraw(Transaction transaction) throws SQLException {	
		( transaction.getFromAccount() ).setBalance(transaction.getFromAccount().getBalance() - transaction.getAmount() );
		updateAmountWithdrawn(transaction);
//		addTransaction(transaction);
		transaction.getAtm().setAmountRemaining( transaction.getAtm().getAmountRemaining() - transaction.getAmount()  );
//		System.out.println("Amount Withdrawn successfully");
//		try {
			DatabaseOperations.connection.setAutoCommit(false);
			databaseOperations.setBalance(transaction.getFromAccount().getAccountNo(), transaction.getFromAccount().getBalance());
			databaseOperations.setAmountRemaining(atm1.getAmountRemaining(),atm1.getatmId());
			databaseOperations.setTransaction(transaction.getFromAccount().getAccountNo() , transaction.getToAccount() ,transaction.getFromAccount().getUser().getUserID(), transaction.getTransactionProcess() ,transaction.getAmount() , transaction.getTransactionType(), transaction.getAtm() );
			DatabaseOperations.connection.commit();
			System.out.println("Cash Withdrawl success");
//			} catch (SQLException ex) {
//				DatabaseOperations.connection.rollback();
//				System.out.println("Cash Withdrawl failed");
//			} finally {
//				DatabaseOperations.connection.setAutoCommit(false);
//			}
	}

	public void initiateFundTransfer(Transaction transaction) throws SQLException, InsufficientAmountException {
		
//		try {
			requestWithdraw( transaction );
			DatabaseOperations.connection.setAutoCommit(false);
			databaseOperations.updateFundTransferedAccount(transaction.getToAccount(), transaction.getAmount());
			databaseOperations.setFundTransferTransaction(transaction.getFromAccount().getAccountNo(), transaction.getToAccount(), transaction.getFromAccount().getUser().getUserID(), transaction.getTransactionProcess() ,transaction.getAmount() , "CREDIT" , transaction.getAtm() );
			DatabaseOperations.connection.commit();
			System.out.println("Fund transfer Success");
//			}catch (SQLException ex) {
//				DatabaseOperations.connection.rollback();
//				System.out.println("Cash Withdrawl failed");
//			} finally {
//				DatabaseOperations.connection.setAutoCommit(false);
//			}
	}
	
	public void balanceEnquiry(Account account) {
		databaseOperations.getBalance(account.getAccountNo());
	}

	public void miniStatement(Account account) {
		//display the transaction list from the database 
		databaseOperations.getTransactionList(account.getAccountNo());
	}

	public abstract void pinChange(Account account);
	
//	public void addTransaction(Transaction transaction) {
//		String details = transaction.getDate()+" -------------  "+transaction.getTransactionType()+" ----------- "+transaction.getAmount()+"-----------"+transaction.getAtm().getBranchCode()+" ----------- "+transaction.getAtm().getLocation();
//		( transaction.getFromAccount().getTransactionList() ).add(details);
//		if(transaction.getFromAccount().getAccountNo() != transaction.getToAccount().getAccountNo())
//		{			( transaction.getToAccount().getTransactionList() ).add(details);	}
//	}
	
	public void updateAmountWithdrawn(Transaction transaction) {
		transaction.getFromAccount().setAmountWithdrawn( transaction.getFromAccount().getAmountWithdrawn() + transaction.getAmount() ) ;
//		jdbc.updateAmountWithdrawn(transaction.getAccount().getAccountNo(),transaction.getAmount());
	}
}

class InsufficientAmountException extends Throwable{
	public String toString() {
		return(" Withdraw failed !!! Insufficient Amount in ATM");
	}
}

