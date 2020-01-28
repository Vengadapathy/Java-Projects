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

public class JDBC {

	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/ATM_Version_1";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	private static final String CREDIT = "CREDIT";
	public static Connection connection = null;
	public static PreparedStatement prep = null;
	public String query = null;

	Account account;
	Transaction transaction;
	ATM_Machine atm;
	User user;

	public void getConnection() {
		try {
			Class.forName(DRIVER );
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}

	public Account getAccount(Long acctNo, Integer pin) {
		query = "SELECT user.userid,user.username,account.accountNo,account.pin,user.phone_number,user.mailid,user.address,account.balance FROM userToAccount "+
							"inner JOIN account on userToAccount.accountNo = account.accountNo  "+
							"inner join user ON userToAccount.userid = user.userid  WHERE account.accountNo = ?  AND account.pin = ?";
		try {
			prep = connection.prepareStatement(query);
			prep.setLong(1, acctNo);
			prep.setInt(2, pin);
			ResultSet result = prep.executeQuery();
			result.next();
			int userid = result.getInt("userid");
			int accountNumber = result.getInt("accountNo");
			int PIN = result.getInt("pin");
			double balance = result.getDouble("balance");
			String username = result.getString("username");
			String mailid = result.getString("mailid");
			String add = result.getString("address");
			String phoneNumber = result.getString("phone_number");
			long phNo = Long.parseLong(phoneNumber);
			user = new User(userid,username, mailid, phNo, add);
			account = new Account(accountNumber, PIN, balance, user);
		} catch (SQLException e) {
			System.out.println("Login failed");
		}
		return account;
	}

	public void setTransaction(Account account2, double amount, String type, ATM_Machine atm) {
		int transactionTypeID = (CREDIT.equals(type)) ? 1 : 2 ;
			
		query = "insert into transaction (userid,accountNo,transactionTypeID, amount ,atmid, balance) " + 
							"( select userToAccount.userid ,userToAccount.accountNo , "+transactionTypeID+"  , "+amount+" ,  "+atm.getatmId()+"  , account.balance  from userToAccount \n" + 
							" inner join account on userToAccount.accountNo = account.accountNo \n" + 
							" where account.accountNo = ? ) ;";
		try {
			prep = connection.prepareStatement(query);
			prep.setLong(1, account2.getAccountNo());
			int result = prep.executeUpdate();
			if (result == 1)
				System.out.println("Transaction added to database");
			else
				System.out.println("Transaction is not added to database");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ATM_Machine getATM(int atmid) {
		query = "SELECT branchCode,amountRemaining,location,status,type FROM atm WHERE atmid = ?";
		try {
			prep = connection.prepareStatement(query);
			prep.setInt(1, atmid);
			ResultSet result = prep.executeQuery();
			result.next();
			String branchCode = result.getString("branchCode");
			double amountRemaining = result.getDouble("amountRemaining");
			String location = result.getString("location");
			String status = result.getString("status");
			boolean atmStatus = (status.equals("active") ? true : false);
			String type = result.getString("type");
			atm = new ATM_Machine(atmid, branchCode, new Date(), amountRemaining, location, atmStatus, type);
		} catch (SQLException e) {
			System.out.println("ATM failure !!! \nTemporarily out of service");
			System.exit(0);
		}
		return atm;
	}

	public void getTransactionList(long acctNo) {
		query = " SELECT 	user.username, transaction.transactionid , account.accountNo, transaction.amount,transaction.balance, transaction.transactionDate , transactionType.transactionType, transaction.atmid , atm.location  from transaction "+
						"	inner join account on transaction.accountNo = account.accountNo "+
						"  inner join user on transaction.userid = user.userid "+
						"  left join atm on transaction.atmid= atm.atmid  "+
						"  inner join transactionType on transaction.transactionTypeID = transactionType.transactionTypeId "+
						"  where account.accountNo = ? order by transaction.transactionid desc  ; ";
        
		try {
			prep = connection.prepareStatement(query);
			prep.setLong(1, acctNo);
			ResultSet result = prep.executeQuery();
			System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
			System.out.printf("%20s	%10s		%10s		%10s		%15s		%20s		%20s		%10s		%20s		\n","UserName","TransactionID","AccountNumber"," Amount " , "Balance "," TransactionDate " , " TransactionType " , " ATM_ID " , " Location \n");
			System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
			while (result.next()) {
					System.out.printf("%20s	%20s		%30s		%20s		%20s		%20s		%20s		%20s		%20s		\n" , result.getString("username") , result.getInt("transactionid") , result.getLong("accountNo") , result.getDouble("amount") , result.getDouble("balance") , result.getTimestamp("transactionDate") , result.getString("transactionType") , result.getInt("atmid") , result.getString("location") );
				}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void getBalance(long acctNo) {
		query = "SELECT balance FROM account WHERE accountNo = ?";
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
	
	public void setBalance(long acctNo, double balance) {
		query = "UPDATE account SET balance = ?  WHERE accountNo = ?";
		try {
			prep = connection.prepareStatement(query);
			prep.setDouble(1, balance);
			prep.setLong(2, acctNo);
			prep.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setNewPIN(long acctNo,int newPin) {
		query = "UPDATE account SET pin = ? WHERE accountNo = ? ;";
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
//		query = "UPDATE account SET amountwithdrawn = ? WHERE accountNo = ? ;";
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
		public void setAmountRemaining(double amountRemaining,int atmid) {
			query = "UPDATE atm SET amountRemaining = ?  WHERE atmid = ?";
			try {
				prep = connection.prepareStatement(query);
				prep.setDouble(1, amountRemaining);
				prep.setInt(2, atmid);
				int result = prep.executeUpdate();
				if (result==1)
					System.out.println("ATM Amount remaining is Updated");
				else
					System.out.println("ATM amount updation failed");	
				} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
}
