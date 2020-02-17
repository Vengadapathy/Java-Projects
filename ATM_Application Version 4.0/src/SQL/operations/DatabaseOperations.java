package SQL.operations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import Objects.ATM_Machine;
import Objects.Account;
import Objects.Transaction;
import Objects.User;

public class DatabaseOperations {

	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/ATM_Version_3";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	private static final String CREDIT = "CREDIT";
	public static Connection connection = null;
	public static PreparedStatement prep = null;

	Account account;
	Transaction transaction;
	
	ATM_Machine atm;
	User user;
	public void getConnection() {
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	public int checkAccountType(long acctNo) {
		String query = "select * from account where accountNo = ?" ;
		try {
			prep = connection.prepareStatement(query);
			prep.setLong(1, acctNo);
			ResultSet result = prep.executeQuery();
			if(	result.next() == false ) {
				System.out.println("No Account found");
				return 0;
			} else {
				return result.getInt("accountTypeID");
			}
		} catch (SQLException e) {
			System.out.println("Login failed1");
		}
		return 0;
	}
	
	public Account getAccount(Long acctNo, Integer pin) {
		String query = "SELECT * FROM userToAccount "+
							"inner JOIN account on userToAccount.accountNo = account.accountNo  "+ 
							"inner join accountType on account.accountTypeID = accountType.accountTypeID "+
							"inner join user ON userToAccount.userid = user.userid  WHERE account.accountNo = ?  AND account.pin = ?";
		try {
			prep = connection.prepareStatement(query);
			prep.setLong(1, acctNo);
			prep.setInt(2, pin);
			ResultSet result = prep.executeQuery();
			result.next();
			int userid = result.getInt("userid");
			int accountNumber = result.getInt("accountNo");
			String accountType = result.getString("accountType");
			int PIN = result.getInt("pin");
			double balance = result.getDouble("balance");
			String username = result.getString("username");
			String mailid = result.getString("mailid");
			String add = result.getString("address");
			String phoneNumber = result.getString("phone_number");
			long phNo = Long.parseLong(phoneNumber);
			user = new User(userid,username, mailid, phNo, add);
			account = new Account(accountNumber,accountType, PIN, balance, user);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Login failed");
		}
		return account;
	}

	public Account getJointAccount(Long acctNo, int pin, int userID) {
		String query = "SELECT * FROM userToAccount "+
							"inner JOIN account on userToAccount.accountNo = account.accountNo  "+
							"inner join accountType on account.accountTypeID = accountType.accountTypeid\n" + 
							"inner join user ON userToAccount.userid = user.userid  WHERE account.accountNo = ?  AND account.pin = ? and user.userid = ?";
		try {
			prep = connection.prepareStatement(query);
			prep.setLong(1, acctNo);
			prep.setInt(2, pin);
			prep.setInt(3, userID);
			ResultSet result = prep.executeQuery();
			result.next();
			int userid = result.getInt("userid");
			int accountNumber = result.getInt("accountNo");
			String accountType = result.getString("accountType");
			int PIN = result.getInt("pin");
			double balance = result.getDouble("balance");
			String username = result.getString("username");
			String mailid = result.getString("mailid");
			String add = result.getString("address");
			String phoneNumber = result.getString("phone_number");
			long phNo = Long.parseLong(phoneNumber);
			user = new User(userid,username, mailid, phNo, add);
			account = new Account(accountNumber,accountType, PIN, balance, user);
		} catch (SQLException e) {
			System.out.println("Login failed");
		}
		return account;
	}


	public ATM_Machine getATM(int atmid) {
		String query = "SELECT branchCode,amountRemaining,location,status,type FROM atm WHERE atmid = ?";
		try {
			prep = connection.prepareStatement(query);
			prep.setInt(1, atmid);
			ResultSet result = prep.executeQuery();
			result.next();
			String branchCode = result.getString("branchCode");
			double amountRemaining = result.getDouble("amountRemaining");
			String location = result.getString("location");
			String status = result.getString("status");
			boolean atmStatus = ( ("active").equalsIgnoreCase(status) ? true : false);
			String type = result.getString("type");
			atm = new ATM_Machine(atmid, branchCode, new Date(), amountRemaining, location, atmStatus, type);
		} catch (SQLException e) {
			System.out.println("ATM failure !!! \nTemporarily out of service");
			System.exit(0);
		}
		return atm;
	}

	public void getTransactionList(long acctNo) {
		String query = 	" SELECT user.username, transaction.transactionid , transaction.accountNo,transaction.recipientAccountNo, transaction.amount,transaction.balance, transaction.transactionDate ,transactionprocess.transactionProcess, transactionType.transactionType, transactionModes.transactionMode , CASE WHEN atm.atmid IS NOT NULL THEN atm.atmid ELSE mobile.mobileID END AS TransactionModeID, IFNULL(atm.location , 'N/A') as location from transaction  "+
										"left join transactiontoatm on transaction.transactionid=transactiontoatm.transactionid   "+ 
										"left join transactiontomobile on transaction.transactionid=transactiontomobile.transactionid  "+
									    "inner join transactionType on transaction.transactionTypeID = transactionType.transactionTypeID  "+
									    "inner join user on transaction.userid= user.userid   "+
									    "left join atm on atm.atmid = transactiontoatm.atmid   "+
									    "left join mobile on mobile.mobileID = transactiontomobile.mobileid   "+
									    "inner join transactionModes on transactionModes.transactionModeID = transaction.transactionModeID   "+
									    "inner join transactionprocess on transaction.transactionProcessID = transactionprocess.transactionProcessID\n" + 
									    "where ( transaction.accountNo =  ? and transaction.transactionTypeID = 2 )  or(  transaction.recipientAccountNo = ? and  transaction.transactionTypeID = 1 ) order by transaction.transactionDate desc;  ";
			   
		try {
			prep = connection.prepareStatement(query);
			prep.setLong(1, acctNo);
			prep.setLong(2, acctNo);
			System.out.println(query);
			ResultSet result = prep.executeQuery();
			System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
			System.out.printf("%20s		%10s		%20s		%20s		%10s		%20s		%30s		%20s		%20s		%20s		%20s\n","UserName","TransactionID", "From AccountNumber" , " To AccountNumber "," Amount " , "Balance "," TransactionDate " , " TransactionProcess " ," TransactionType " , "TransactionMode" , " TransactionModeID " , " Location \n");
			System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
			while (result.next()) {
					System.out.printf("%20s		%20s		%30s		%20s		%20s		%30s		%30s		%20s		%30s		%30s	%30s\n" , result.getString("username") , result.getInt("transactionid") , result.getLong("accountNo") , result.getLong("recipientAccountNo") , result.getDouble("amount") , result.getDouble("balance") , result.getTimestamp("transactionDate") , result.getString("transactionProcess") ,result.getString("transactionType") ,result.getString("transactionMode"), result.getInt("TransactionModeID") , result.getString("location") );
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void getBalance(long acctNo) {
		String query = "SELECT balance FROM account WHERE accountNo = ?";
		try {
			prep = connection.prepareStatement(query);
			prep.setLong(1, acctNo);
			ResultSet result = prep.executeQuery();
			result.next();
			System.out.println("Available balance in your account is " + result.getDouble("balance") + "\n");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void setBalance(long acctNo, double balance) throws SQLException {
		String query = "UPDATE account SET balance = ?  WHERE accountNo = ?";
			prep = connection.prepareStatement(query);
			prep.setDouble(1, balance);
			prep.setLong(2, acctNo);
			prep.executeUpdate();
	}

	public void setTransaction(long fromAccountNo,long toAccountNo,int userid,int process,double amount, String type, ATM_Machine  atm) throws SQLException {
		int transactionTypeID = (CREDIT.equals(type)) ? 1 : 2 ;
			
		String query = "insert into transaction (userid , accountNo ,transactionProcessID ,transactionTypeID, transactionModeID,recipientAccountNo, amount , balance) " + 
							"( select userToAccount.userid ,userToAccount.accountNo , "+process+" , "+transactionTypeID+"  , 1 , "+toAccountNo+" , "+amount+"   , account.balance  from userToAccount \n" + 
							" inner join account on userToAccount.accountNo = account.accountNo \n" + 
							" inner join user on userToAccount.userid= user.userid\n" + 
							" where account.accountNo = ? and user.userid = ? ) ;";
		
		String query1 = " insert into transactiontoatm (transactionid,atmid)  (SELECT transactionid  ,"+ atm.getatmId() +" FROM transaction ORDER BY transactionDate DESC  LIMIT 1 ) "  ;
			prep = connection.prepareStatement(query);
			prep.setLong(1, fromAccountNo);
			prep.setInt(2, userid);
			System.out.println(query);
			int result = prep.executeUpdate();
			System.out.println(result);
			if (result == 1) {
				System.out.println("Transaction added to database");
			} else	{
				System.out.println("Transaction is not added to database");	}
			prep = connection.prepareStatement(query1);
			prep.executeUpdate();
			if (result == 1) {
				System.out.println("Transaction added to transactiontoatm");
			} else	{
				System.out.println("Transaction is not added to transactiontoatm");	}
		}
	
	public void setFundTransferTransaction(long fromAccountNo,long toAccountNo,int userid,int process,double amount, String type, ATM_Machine  atm) throws SQLException {
		int transactionTypeID = (CREDIT.equals(type)) ? 1 : 2 ;
			
		String query = " insert into transaction (userid , accountNo ,transactionProcessID ,transactionTypeID, transactionModeID,recipientAccountNo, amount , balance) "
				+ "( select "+userid+" ,account.accountNo , "+process+" , "+transactionTypeID+"  , 1 , "+toAccountNo+" , "+amount+"   , (select balance from account where accountNo = ? )  from account \n" + 
				" where account.accountNo = ?  ) ;";
		
		String query1 = " insert into transactiontoatm (transactionid,atmid)  (SELECT transactionid  ,"+ atm.getatmId() +" FROM transaction ORDER BY transactionid DESC  LIMIT 1 ) "  ;
			prep = connection.prepareStatement(query);
			prep.setLong(1, toAccountNo);
			prep.setLong(2, fromAccountNo);
			System.out.println(query);
			int result = prep.executeUpdate();
			System.out.println(result);
			if (result == 1) {
				System.out.println("Transaction added to database");
			} else	{
				System.out.println("Transaction is not added to database");	}
			prep = connection.prepareStatement(query1);
			prep.executeUpdate();
			if (result == 1) {
				System.out.println("Transaction added to transactiontoatm");
			} else	{
				System.out.println("Transaction is not added to transactiontoatm");	}
		} 
	
	public void setAmountRemaining(double amountRemaining,int atmid) throws SQLException {
		String query = "UPDATE atm SET amountRemaining = ?  WHERE atmid = ?";
			prep = connection.prepareStatement(query);
			prep.setDouble(1, amountRemaining);
			prep.setInt(2, atmid);
			int result = prep.executeUpdate();
			if (result==1)
				System.out.println("ATM Amount remaining is Updated");
			else
				System.out.println("ATM amount updation failed");	
	}
		
	public void setNewPIN(long acctNo,int newPin) {
		String query = "UPDATE account SET pin = ? WHERE accountNo = ? ;";
		try {
			prep = connection.prepareStatement(query);
			prep.setInt(1, newPin);
			prep.setLong(2, acctNo);
			int result = prep.executeUpdate();
			if (result==1)
				System.out.println("New PIN for this Account is Updated");
			else
				System.out.println("New Pin Updation failed");		
			} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
//	public void updateAmountWithdrawn(long acctNo,double amount) {
//		String query = "UPDATE account SET amountwithdrawn = ? WHERE accountNo = ? ;";
//		try {
//			prep = connection.prepareStatement(query);
//			prep.setDouble(1, amount);
//			prep.setLong(2, acctNo);
//			int result = prep.executeUpdate();
//			if (result==1)
//				System.out.println("New PIN for this Account is Updated");
//			else
//				System.out.println("New Pin Updation failed");		
//			} catch (SQLException e) {
//			e.printStackTrace();
//		}	
//	}

	public boolean getFundTransferAccount(long recipientAccountNo) {
		String query = " select * from account	inner join userToAccount using (accountNo) inner join user using (userid)  where accountNo = ? " ;
		try {
				prep = connection.prepareStatement(query);
				prep.setLong(1, recipientAccountNo);
				ResultSet result = prep.executeQuery();
				if(result.next() == false ) {
					System.out.println("Account not found");
					return false ;
				}	else	{
						System.out.println("The Account "+recipientAccountNo+"  is connected with : ");
						do {
								System.out.println("\tAccount holder's Name : "+result.getString("username")+" \tPhone number linked : "+result.getString("phone_number"));
						}while( result.next() );
					return true ;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}	
		return false ;
	}

	public void updateFundTransferedAccount(long toAccount, double amount) {
		String query = "UPDATE account SET balance = balance+"+amount+" WHERE accountNo = ? ;";
		try {
			prep = connection.prepareStatement(query);
			prep.setLong(1, toAccount);
			int result = prep.executeUpdate();
			if (result==1)
				System.out.println("FundTransfered Account balance updated");
			else
				System.out.println("FundTransfered Account balance updation failed");		
			} catch (SQLException e) {
			e.printStackTrace();
		}	
	}
		
}
