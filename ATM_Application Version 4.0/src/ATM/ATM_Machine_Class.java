package ATM;

import java.sql.SQLException;
import java.util.InputMismatchException;

import Objects.Account;
import Objects.Transaction;
import SQL.operations.DatabaseOperations;
import otherOperations.PIN_Change;

public class ATM_Machine_Class extends ATM_Operations {
	
//	public ATM_Machine_Class() {
//			super();
//			
//	}
	
	public void start() {
		try {
			initialize();
			loginPage();
		} catch(InputMismatchException ex ) {
			System.out.println("Invalid character entered");
		}	catch (InsufficientAmountException e) {
			System.out.println(e);
		}finally {
						try {
							DatabaseOperations.connection.close();
							databaseOperations = null ;
							input=null;
						} catch (SQLException e) {
							System.out.println("Connection closed");
							e.printStackTrace();
						}
			start();
		}
	}
		
	public void loginPage() throws InsufficientAmountException {
			if( ! (atm1.isUserAuthentication()) ) {
				Account account = null ;
				System.out.println("--------------------------------------------------------------------------WELCOME------------------------------------------------------------------------\n");
				System.out.println("Enter your Account number");
				Long acctNo = input.nextLong();
				int accountTypeid = databaseOperations.checkAccountType(acctNo);
				switch(accountTypeid) {
				case 1:
				case 3:
					System.out.println("Enter your PIN");
					Integer pin = input.nextInt();
					account = databaseOperations.getAccount(acctNo,pin);
					break;
				case 2:
					System.out.println("Enter your userID");
					int userid = input.nextInt();
					System.out.println("Enter your PIN");
					Integer pin1 = input.nextInt();
					account = databaseOperations.getJointAccount(acctNo,pin1,userid);
					break;
				default :
					loginPage();
				}
				if(account == null ) {
					atm1.setUserAuthentication(false);
					System.out.println("Invalid Account number or PIN !!! ");
					loginPage();
				} else {
					atm1.setUserAuthentication(true);
					homeScreen(account);
				}
		}
	}
	
	public void homeScreen(Account account) throws InsufficientAmountException  {
			if(atm1.isUserAuthentication()) {
			System.out.println("\n\nWelcome to our ATM services "+(account.getUser()).getUserName());
			System.out.println("--------------------------------------------------------------------------HOME PAGE------------------------------------------------------------------------\n");
			System.out.println("1. Deposit Amount");
			System.out.println("2. Withdraw Amount");
			System.out.println("3. Fund Transfer");
			System.out.println("4. Balance Enquiry");
			System.out.println("5. Mini Statement");
			System.out.println("6. PIN change");
			System.out.println("7. Logout\n");
			System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			choiceSelect(account);
			} else {
					System.out.println("Thank you for your using our banking services");
					System.out.println("Have a good day\n\n");
					loginPage();
			}
	}

	public void choiceSelect(Account account) throws InsufficientAmountException{
			System.out.println("Enter your choice");
			int choice = input.nextInt();
			switch(choice) {
			case 1 :
				transaction(account,account.getAccountNo(),DEPOSIT,CREDIT);
				break;
				
			case 2 :
				transaction(account,account.getAccountNo(),WITHDRAW,DEBIT);
				break;
			
			case 3:
				fundTransfer(account);
				break;
				
			case 4 :
				balanceEnquiry(account);
				break;
				
			case 5 :
				miniStatement(account);
				break;
				
			case 6 :
				pinChange(account);
				
			case 7 :
				atm1.setUserAuthentication(false);
				System.out.println("Logged out of account");
				break;
				
			default :
				System.out.println("OOPS !!!  Invalid Choice !!! Try again");	
				loginPage();
			}	
			homeScreen(account);
	}
	
	public void fundTransfer(Account account) throws InsufficientAmountException {
		System.out.println("Enter the accountNumber to which fund is to be transfered");
		long recipientAccountNo = input.nextLong();
		boolean  confirmation = databaseOperations.getFundTransferAccount(recipientAccountNo);
		if(confirmation) {
			transaction(account,recipientAccountNo,FUNDTRANSFER, DEBIT);
		}
	}

	protected void transaction(Account fromAccount,long toAccount,int process, String type) throws InsufficientAmountException {
		double amount = 0.00d;
		System.out.println("Enter the money for transaction in denominations of 100, 200, 500, 1000");
			amount = input.nextDouble();
			if(amount % 100 != 0  ||  amount < 100) {
				System.out.println("Please enter a valid denomination of money for transaction");
				transaction(fromAccount,toAccount,process,type);	
			}
			if ( amount > atm1.getAmountRemaining() && DEBIT.equals(type) ) {
								throw new InsufficientAmountException();
			}
		
		System.out.println("--------------------------------------------------------------------------TRANSACTION PAGE------------------------------------------------------------------------");
		Transaction transaction = new Transaction(fromAccount,toAccount, amount,process, type, atm1);
		try {
		DatabaseOperations.connection.setAutoCommit(false);
		switch(process) {
		case DEPOSIT :
			requestDeposit(transaction);
			break;
		case WITHDRAW :
			requestWithdraw(transaction);
			break;
		case FUNDTRANSFER :
			initiateFundTransfer(transaction);
			break;
			}
		DatabaseOperations.connection.commit();
		} catch (SQLException e) {
			try {
				DatabaseOperations.connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("Transaction Failed");
			e.printStackTrace();
		}	finally 	{
			try {
				DatabaseOperations.connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		}


	public void pinChange(Account account) {
		PIN_Change pin = new PIN_Change(account.getUser().getMailID());
		if(pin.is_OTP_Matching()) {
			System.out.println("Enter new PIN ");
			Integer newPin = input.nextInt();
			if( newPin <= 9999 && newPin >= 1000 ) {
				databaseOperations.setNewPIN(account.getAccountNo(),newPin);
			}
			else
				System.out.println("PIN entered is invalid !!! Try again later");
		}
		else
			System.out.println("OTP does not match ");		
	}
}
