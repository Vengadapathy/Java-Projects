package otherOperations;

import java.util.ArrayList;

import Objects.Account;
import Objects.User;

public class AccountAuthentication {
		private static ArrayList<Account> accountsList ;
		
		public static void initializeAccounts() {
			accountsList = new ArrayList<Account>();
			User user1 = new User("Vengat1", "vengatpy@gmail.com", 12345678, "Pondy" );
			User user2 = new User("Vengat2" , "vengatpy@gmail.com" , 12345679, "Pondy");
			Account account1 = new Account(1234500, 1000 , 1000.00d , user1 );
			Account account2 = new Account(1234501 , 1001 , 1000.00d , user2);
			
			accountsList.add(account1);
			accountsList.add(account2);
		}
		
		public Account authenticateAccount(Long AcctNo , Integer PIN) {
			for(Account account : accountsList) {
				if( AcctNo == ( account.getAccountNo() )  && PIN == ( account.getPIN() ))
					return account;
			}	
			return null;
		}
}
