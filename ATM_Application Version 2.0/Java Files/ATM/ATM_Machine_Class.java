package ATM;

import java.util.InputMismatchException;
import java.util.Scanner;

import Objects.Transaction;
import otherOperations.PIN_Change;

public class ATM_Machine_Class extends ATM_Operations {

	public ATM_Machine_Class() {
		super();
	}
	
	public void start() {
		try {
			loginPage();
			homeScreen();
			choiceSelect();
		}catch(InsufficientAmountException ex) {
			System.out.println(ex);
		} catch(InputMismatchException ex ) {
			System.out.println("Invalid character entered");
		}	catch(NumberFormatException ex) {
				System.out.println("Invalid denomination !!! ");	
		}finally {
			start();
		}
	}
	
	public void loginPage() {
		input = new Scanner(System.in);
			if( ! (atm1.isUserAuthentication())) {
				System.out.println("--------------------------------------------------------------------------WELCOME------------------------------------------------------------------------\n");
				System.out.println("Enter your Account number");
				Long acctNo = input.nextLong();
				System.out.println("Enter your PIN");
				Integer pin = input.nextInt();
				//send the entered account number and pin to database to get the required account
				account = jdbc.getAccount(acctNo,pin);
				if(account == null ) {
					atm1.setUserAuthentication(false);
					System.out.println("Invalid Account number or PIN !!! ");
				} else {
					atm1.setUserAuthentication(true);
				}
		}
	}
	
	public void homeScreen() {
			if(atm1.isUserAuthentication()) {
			System.out.println("\n\nWelcome to our ATM services "+(account.getUser()).getUserName());
			System.out.println("--------------------------------------------------------------------------HOME PAGE------------------------------------------------------------------------\n");
			System.out.println("1. Deposit Amount");
			System.out.println("2. Withdraw Amount");
			System.out.println("3. Balance Enquiry");
			System.out.println("4. Mini Statement");
			System.out.println("5. PIN change");
			System.out.println("6. Logout\n");
			System.out.println("-------------------------------------------------------------------------------------------------------------------------------------------------------------------");
			} else {
					System.out.println("Thank you for your using our banking services");
					System.out.println("Have a good day\n\n");
					start();
			}
	}

	public void choiceSelect() throws InsufficientAmountException {
			System.out.println("Enter your choice");
			int choice = input.nextInt();
			switch(choice) {
			case 1 :
				transaction(CREDIT);
				break;
				
			case 2 :
				transaction(DEBIT);
				break;
		
			case 3 :
				balanceEnquiry();
				break;
				
			case 4 :
				miniStatement();
				break;
				
			case 5 :
				pinChange();
				
			case 6 :
				atm1.setUserAuthentication(false);
				System.out.println("Logged out of account");
				break;
				
			default :
				System.out.println("OOPS !!!  Invalid Choice !!! Try again");	
				start();
			}	
			start();
	}
	
	protected void transaction(String type) throws InsufficientAmountException {
		double amount = 0.00d;
		System.out.println("Enter the money for transaction in denominations of 100, 200, 500, 1000");
			amount = input.nextDouble();
			if(amount % 100 != 0  ||  amount < 100) 
				throw new NumberFormatException() ;
			if ( amount > atm1.getAmountRemaining() && DEBIT.equals(type) )
				throw new InsufficientAmountException();
		
		System.out.println("--------------------------------------------------------------------------TRANSACTION PAGE------------------------------------------------------------------------");
		this.transaction = new Transaction(account, amount, type, atm1);
		switch(type) {
		case CREDIT :
			requestDeposit();
			break;
		case DEBIT :
			requestWithdraw();
			break;
			}
		}

	public void pinChange() {
		PIN_Change pin = new PIN_Change(account.getUser().getMailID());
		if(pin.is_OTP_Matching()) {
			System.out.println("Enter new PIN ");
			Integer newPin = input.nextInt();
			if( newPin <= 9999 && newPin >= 1000 ) {
				jdbc.setNewPIN(account.getAccountNo(),newPin);
				//account.setPIN(newPin);
				//System.out.println(account.getPIN());
				//System.out.println("PIN Changed successfully");
			}
			else
				System.out.println("PIN entered is invalid !!! Try again later");
		}
		else
			System.out.println("OTP does not match ");		
	}
}
