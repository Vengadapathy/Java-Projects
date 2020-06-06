package ATM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Random;

import Objects.Account;
import Objects.AtmCard;
import Objects.Transaction;
import SQL.operations.DatabaseOperations;
import otherOperations.PIN_Change;

public class ATM_Machine_Class extends ATM_Operations {
	
	public void start() {
		try {
			initialize();
			loginPage();
		} catch(InputMismatchException ex ) {
			System.out.println("Invalid character entered");
		}	catch(NumberFormatException ex) {
				ex.printStackTrace();
				System.out.println("Invalid denomination !!! ");	
		} catch (InsufficientAmountException e) {
			System.out.println(e);
		} catch(Exception e){
			System.out.println("Exception");
			e.printStackTrace();
		} finally {
			closeConnection();
			start();
		}
	}
		
	public void loginPage() throws InsufficientAmountException {
			if( ! (atm1.isUserAuthentication())) {
				AtmCard atmcard = null ;
				System.out.println("--------------------------------------------------------------------------WELCOME------------------------------------------------------------------------\n");
				System.out.println("Enter your ATM Card number");
				Long cardNo = input.nextLong();
				System.out.println("Enter your PIN");
				int pin = input.nextInt();
				atmcard = userAccount(cardNo,pin);
				if( authentication(atmcard) ) {
					homeScreen(atmcard);
				} else {
					loginPage();
				}
		}
	}
	
	public AtmCard userAccount(long cardNo,int pin) {
		AtmCard card = databaseOperations.getAccount(cardNo,pin);
		return card;
	}
	
	public boolean authentication(AtmCard atmcard) {
		if(atmcard == null || atmcard.getAccount() == null ) {
			atm1.setUserAuthentication(false);
			System.out.println("Invalid Account number or PIN !!! ");
			return false;
		} else {
			atm1.setUserAuthentication(true);
			return true;
		}
	}
	
	public void homeScreen(AtmCard atmcard) throws InsufficientAmountException  {
			if(atm1.isUserAuthentication()) {
				System.out.println("\n\nWelcome to our ATM services "+(atmcard.getAccount().getUser()).getUserName());
				System.out.println("--------------------------------------------------------------------------HOME PAGE------------------------------------------------------------------------\n");
				System.out.println("1. Deposit Amount");
				System.out.println("2. Withdraw Amount");
				System.out.println("3. Fund Transfer");
				System.out.println("4. Balance Enquiry");
				System.out.println("5. Mini Statement");
				System.out.println("6. PIN change");
				System.out.println("7. Logout\n");
				System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------");
				choiceSelect(atmcard);
			} else {
					System.out.println("Thank you for your using our banking services");
					System.out.println("Have a good day\n\n");
					loginPage();
			}
	}

	public void choiceSelect(AtmCard atmcard) throws InsufficientAmountException{
			System.out.println("Enter your choice");
			int choice = input.nextInt();
			switch(choice) {
			case 1 :
				transaction(atmcard.getAccount(),atmcard.getAccount().getAccountNo(),DEPOSIT,CREDIT,getAmount());
				break;
				
			case 2 :
				transaction(atmcard.getAccount(),atmcard.getAccount().getAccountNo(),WITHDRAW,DEBIT,getAmount());
				break;
			
			case 3:
				fundTransfer(atmcard.getAccount());
				break;
				
			case 4 :
				balanceEnquiry(atmcard.getAccount());
				break;
				
			case 5 :
				printStatement(atmcard.getAccount());
				break;
				
			case 6 :
				pinChange(atmcard);
				
			case 7 :
				atm1.setUserAuthentication(false);
				System.out.println("Logged out of account");
				break;

			default :
				System.out.println("OOPS !!!  Invalid Choice !!! Try again");	
				loginPage();
			}	
			homeScreen(atmcard);
	}
	
	public void fundTransfer(Account account) throws InsufficientAmountException {
		System.out.println("Enter the accountNumber to which fund is to be transfered");
		long recipientAccountNo = input.nextLong();
		boolean  confirmation = getFundTransferAccountNo(recipientAccountNo);
		if(confirmation) {
			transaction(account,recipientAccountNo,FUNDTRANSFER, DEBIT,getAmount());
		}
	}
	
	public boolean getFundTransferAccountNo(long recipientAccountNo) {
		return databaseOperations.getFundTransferAccount(recipientAccountNo);
	}
	
	public double getAmount() {
		double amount = 0.00d;
		System.out.println("Enter the money for transaction in denominations of 100, 200, 500, 1000");
			amount = input.nextDouble();
			if(amount % 100 != 0  ||  amount < 100) {
				throw new NumberFormatException() ;
			}
		return amount ;
	}
	
	
	public boolean transaction(Account fromAccount,long toAccount,int process, String type, double amount) throws InsufficientAmountException {
		
		boolean status = false ;
			if ( amount > atm1.getAmountRemaining() && DEBIT.equals(type) )
				throw new InsufficientAmountException();
		System.out.println("--------------------------------------------------------------------------TRANSACTION PAGE------------------------------------------------------------------------");
		Transaction transaction = new Transaction(fromAccount,toAccount, amount,process, type, atm1);
		try {
			DatabaseOperations.connection.setAutoCommit(false);
			switch(process) {
			case DEPOSIT :
				status = requestDeposit(transaction);
				break;
			case WITHDRAW :
				status = requestWithdraw(transaction);
				break;
			case FUNDTRANSFER :
				status = initiateFundTransfer(transaction);
				break;
				}
			DatabaseOperations.connection.commit();
		} catch (SQLException e) {
			status = false ;
			try {
				DatabaseOperations.connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			System.out.println("Transaction Failed");
			e.printStackTrace();
		} finally {
				try {
					DatabaseOperations.connection.setAutoCommit(true);
				} catch (final SQLException e) {
					e.printStackTrace();
				}
			}
		return status;
		}
	
	public void printStatement(Account account) {
		
		ArrayList<HashMap<String,String>> list = miniStatement(account);
		if(list == null) {
			System.out.println("Mini Statement Exception Occured");
		} else {
			System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
			System.out.printf("%20s		%10s		%20s		%20s		%20s		%20s		%30s		%20s		%25s		%30s	\n","UserName","TransactionID","AccountNumber","Recipient AccountNo"," Amount " , "Balance "," TransactionDate " , " TransactionType " , "TransactionMode" , " TransactionModeInfo \n");
			System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
	
			for(int index=0;index<list.size();index++) {
				HashMap<String,String> map = list.get(index);
				System.out.printf("%20s		%10s		%20s		%20s		%20s		%20s		%30s		%20s		%30s		%30s	\n" ,map.get("username") , map.get("transactionid") , map.get("accountno") ,map.get("recipientaccountno") , map.get("amount") , map.get("balance") , map.get("transactiondate") , map.get("transactiontype") ,map.get("transactionmode"), map.get("transactionmodeinfo")  );
			}
		}
		
	}
		
	public boolean otpVerification(AtmCard atmcard) {
		int otp = generateOtp();
		PIN_Change pin = new PIN_Change(atmcard.getAccount().getUser().getMailID());
		pin.Mailer(otp);
		int userOtp = getUserOtp();
		return checkOtp(otp,userOtp);
	}
	
	public int getUserOtp() {
		System.out.println("Enter the OTP generated");
		return input.nextInt();
	}
	
	public boolean pinChange(AtmCard atmcard) {
		boolean status = false ;
		if(otpVerification(atmcard)) {
			int newPin = getNewPin();
			boolean pinStatus = validatePin(atmcard.getPIN() , newPin);
			if( pinStatus ) {
				setNewPin(atmcard.getCardNo(),newPin);
				status = true ;
			} else {
				System.out.println("PIN entered is invalid !!! Try again later");
			}
		} else {
			System.out.println("OTP does not match ");
		}
		
		return status ;
	}
	
	
	public int generateOtp() {
		Integer val = new Random().nextInt(1000000);
		return val;
	}
	
	public boolean checkOtp(int otp, int generatedOtp) {
		return (otp == generatedOtp ? true : false);
	}
	
	public int getNewPin() {
		System.out.println("Enter new PIN ");
		return input.nextInt();
	}
	
	public boolean validatePin(int oldPin,int newPin) {
		if( newPin <= 9999 && newPin >= 1000 && oldPin != newPin ) {
			return true ;
		} else {
			return false ;
		}
	}
	
	public boolean setNewPin(long cardNo,int newPin) {
		return databaseOperations.setNewPIN(cardNo,newPin);
	}

	public HashMap<String, Long> getAccountMap() {
		return databaseOperations.getAccountMap() ;
	}
}
