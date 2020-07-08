package SQL.operations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;

import Objects.ATM_Machine;
import Objects.Account;
import Objects.AtmCard;
import Objects.Transaction;
import Objects.User;

public class DatabaseOperations {

	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/atm2.0";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "password";
	private static final String CREDIT = "CREDIT";
	public static Connection connection = null;
	public static PreparedStatement prep = null;

	Account account;
	Transaction transaction;
	AtmCard atmcard;
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
//	public int checkAccountType(long acctNo) {
//		String query = "select * from account where accountNo = ?" ;
//		try {
//			prep = connection.prepareStatement(query);
//			prep.setLong(1, acctNo);
//			ResultSet result = prep.executeQuery();
//			if(	result.next() == false ) {
//				System.out.println("No Account found");
//				return 0;
//			} else {
//				return result.getInt("accountTypeID");
//			}
//		} catch (SQLException e) {
//			System.out.println("Login failed1");
//		}
//		return 0;
//	}
	
	public AtmCard getAccount(Long cardNo, Integer pin) {
		AtmCard atmcard;
		String query = "SELECT * FROM usertoaccount "+
							"inner JOIN account on usertoaccount.accountNo = account.accountNo  "+ 
							"inner join accounttype on account.accountTypeID = accounttype.accountTypeID "+
							"inner join atmcard on atmcard.cardNo = usertoaccount.cardNo "+
							"inner join user ON usertoaccount.userid = user.userid  WHERE atmcard.cardNo = ?  AND atmcard.pin = ?";
		try {
			prep = connection.prepareStatement(query);
			prep.setLong(1, cardNo);
			prep.setInt(2, pin);
			ResultSet result = prep.executeQuery();
			result.next();
			int userid = result.getInt("userid");
			int accountNumber = result.getInt("accountNo");
			String accountType = result.getString("accountType");
			double balance = result.getDouble("balance");
			String username = result.getString("username");
			String mailid = result.getString("mailid");
			String add = result.getString("address");
			String phoneNumber = result.getString("phone_number");
			long phNo = Long.parseLong(phoneNumber);
			user = new User(userid,username, mailid, phNo, add);
			account = new Account(accountNumber,accountType, balance, user);
			atmcard = new AtmCard(cardNo,pin,account);
			System.out.println(atmcard);
		} catch (SQLException e) {
			System.out.println("Login failed");
			e.printStackTrace();
			System.out.println("Login failed");
			return null;
		}
		return atmcard;
	}

//	public Account getJointAccount(Long acctNo, int pin, int userID) {
//		String query = "SELECT * FROM userToAccount "+
//							"inner JOIN account on userToAccount.accountNo = account.accountNo  "+
//							"inner join accounttype on account.accountTypeID = accounttype.accountTypeID\n" + 
//							"inner join user ON userToAccount.userid = user.userid  WHERE account.accountNo = ?  AND account.pin = ? and user.userid = ?";
//		try {
//			prep = connection.prepareStatement(query);
//			prep.setLong(1, acctNo);
//			prep.setInt(2, pin);
//			prep.setInt(3, userID);
//			ResultSet result = prep.executeQuery();
//			result.next();
//			int userid = result.getInt("userid");
//			int accountNumber = result.getInt("accountNo");
//			String accountType = result.getString("accountType");
//			int PIN = result.getInt("pin");
//			double balance = result.getDouble("balance");
//			String username = result.getString("username");
//			String mailid = result.getString("mailid");
//			String add = result.getString("address");
//			String phoneNumber = result.getString("phone_number");
//			long phNo = Long.parseLong(phoneNumber);
//			user = new User(userid,username, mailid, phNo, add);
//			account = new Account(accountNumber,accountType, balance, user);
//		} catch (SQLException e) {
//			System.out.println("Login failed");
//		}
//		return account;
//	}


	public ATM_Machine getATM(int atmid) {
		String query = "SELECT bankbranches.branchCode,amountRemaining,location,status,type FROM atm inner join bankbranches on bankbranches.branchNo = atm.branchNo WHERE atmid = ?";
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

	public ArrayList<LinkedHashMap<String, String>> getTransactionList(long acctNo) {
		ArrayList<LinkedHashMap<String,String>> list = new ArrayList<>();
		String query = 	" SELECT user.username, transaction.transactionid , transaction.accountNo,transaction.recipientAccountNo, transaction.amount,transaction.balance, transaction.transactionDate , transactiontype.transactionType, transactionmodes.transactionMode,CONCAT_WS(\"/\",transactiontoatm.atmid,tp1.transactionprocess,transactiontoe_banking.e_bankingid,tp2.transactionprocess) as transactionModeInfo  from transaction  \n" + 
						"							left join transactiontoatm on transaction.transactionid=transactiontoatm.transactionid   \n" + 
						"							left join transactiontoe_banking on transaction.transactionid=transactiontoe_banking.transactionid  \n" + 
						"						    left join user on transaction.userid= user.userid   \n" + 
						"                            left join atm on atm.atmid = transactiontoatm.atmid   \n" + 
						"						    left join e_banking on e_banking.e_bankingid = transactiontoe_banking.e_bankingid   \n" + 
						"							left join transactiontype on transaction.transactionTypeID = transactiontype.transactionTypeID  \n" + 
						"                            left join transactionprocess tp1 on transactiontoatm.transactionProcessId = tp1.transactionprocessID\n" + 
						"						    left join transactionprocess tp2 on transactiontoe_banking.transactionProcessId = tp2.transactionprocessID\n" + 
						"                            left join transactionmodes on transactionmodes.transactionModeID = transaction.transactionModeID   \n" + 
						"						    where transaction.accountNo =  ?  order by transaction.transactionDate desc limit 20 ;  ";
   
		try {
			prep = connection.prepareStatement(query);
			prep.setLong(1, acctNo);
			System.out.println(query);
			
			ResultSet result = prep.executeQuery();
//			System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
//			System.out.printf("%20s		%10s		%20s		%20s		%20s		%20s		%30s		%20s		%25s		%30s	\n","UserName","TransactionID","AccountNumber","Recipient AccountNo"," Amount " , "Balance "," TransactionDate " , " TransactionType " , "TransactionMode" , " TransactionModeInfo \n");
//			System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
			while (result.next()) {
					LinkedHashMap<String,String> map = new LinkedHashMap<String,String>();
					map.put("username",result.getString("username"));
					map.put("transactionid",Integer.toString(result.getInt("transactionid")) );
					map.put("accountno",Long.toString(result.getLong("accountNo")) );
					map.put("recipientacountno",Long.toString(result.getLong("recipientAccountNo")) );
					map.put("amount",Double.toString(result.getInt("amount")) );
					map.put("balance",Double.toString(result.getDouble("balance")) );
					map.put("transactiondate",(result.getTimestamp("transactionDate")).toString() );
					map.put("transactiontype",result.getString("transactionType") );
					map.put("transactionmode",result.getString("transactionMode") );
					map.put("transactionmodeinfo",result.getString("TransactionmodeInfo") );
					
					list.add(map);
					
					System.out.println(map);
					
//					System.out.printf("%20s		%10s		%20s		%20s		%20s		%20s		%30s		%20s		%30s		%30s	\n" , result.getString("username") , result.getInt("transactionid") , result.getLong("accountNo") ,result.getLong("recipientAccountNo") , result.getDouble("amount") , result.getDouble("balance") , result.getTimestamp("transactionDate") , result.getString("transactionType") ,result.getString("transactionMode"), result.getString("TransactionModeInfo")  );
				}
			System.out.println(list);
			System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		} catch (SQLException e) {
			System.out.println("Mini Statement Exception");
			e.printStackTrace();
		}
		return list;
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
	
	public void setBalance(long acctNo, double amount, String type) throws SQLException {
		String query=null;
		if("CREDIT".equalsIgnoreCase(type)) {
			query = "UPDATE account SET balance = balance+"+amount+"  WHERE accountNo = ?";
		} else if("DEBIT".equalsIgnoreCase(type)) {
			query = "UPDATE account SET balance = balance-"+amount+"  WHERE accountNo = ?";
		}
			prep = connection.prepareStatement(query);
			prep.setLong(1, acctNo);
			prep.executeUpdate();
	}

	public void setTransaction(long fromAccountNo,long toAccountNo,int userid,int process,double amount, String type, ATM_Machine  atm) throws SQLException {
		try {
		int transactionTypeID = (CREDIT.equals(type)) ? 1 : 2 ;
			
		String query = "insert into transaction (userid , accountNo ,transactionTypeID, transactionModeID,recipientAccountNo, amount , balance) " + 
							"( select usertoAccount.userid ,usertoAccount.accountNo , "+transactionTypeID+"  , 1 , "+toAccountNo+" , "+amount+"   , account.balance  from usertoaccount \n" + 
							" inner join account on usertoaccount.accountNo = account.accountNo \n" + 
							" inner join user on usertoaccount.userid= user.userid\n" + 
							" where account.accountNo = ? and user.userid = ? ) ;";
		
		String query1 = " insert into transactiontoatm (transactionid,atmid,transactionprocessId)  (SELECT transactionid  ,"+ atm.getatmId() +", "+process+"  FROM transaction ORDER BY transactionDate DESC  LIMIT 1 ) "  ;
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
				System.out.println("Transaction added to transactiontoATM");
			} else	{
				System.out.println("Transaction is not added to transactiontoATM");	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		}
	
	public void setFundTransferTransaction(long fromAccountNo,long toAccountNo,int userid,int process,double amount, String type, ATM_Machine  atm) throws SQLException {
		try {
		int transactionTypeID = (CREDIT.equals(type)) ? 1 : 2 ;
			
		String query = " insert into transaction (userid , accountNo ,transactionTypeID, transactionModeID,recipientAccountNo, amount , balance) "
				+ "( select "+userid+" ,account.accountNo , "+transactionTypeID+"  , 1 , "+toAccountNo+" , "+amount+"   , account.balance  from account \n" + 
				" where account.accountNo = ?  ) ;";
		
		String query1 = " insert into transactiontoatm (transactionid,atmid,transactionProcessId)  (SELECT transactionid  ,"+ atm.getatmId() +", "+process+"  FROM transaction ORDER BY transactionid DESC  LIMIT 1 ) "  ;
			prep = connection.prepareStatement(query);
			prep.setLong(1, fromAccountNo);
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
				System.out.println("Transaction added to transactiontoATM");
			} else	{
				System.out.println("Transaction is not added to transactiontoATM");	}
		} catch (Exception e) {
			e.printStackTrace();
		}
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
		
	public boolean setNewPIN(long cardNo,int newPin) {
		String query = "UPDATE atmcard SET pin = ? WHERE cardNo = ? ;";
		try {
			prep = connection.prepareStatement(query);
			prep.setInt(1, newPin);
			prep.setLong(2, cardNo);
			int result = prep.executeUpdate();
			if (result == 1) {
				System.out.println("New PIN for your ATM Card is Updated");
				return true;
			} else {
				System.out.println("New Pin Updation failed");	
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}	
		return false ;
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
		String query = " select * from account	inner join usertoaccount using (accountNo) inner join user using (userid)  where accountNo = ? " ;
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

	public HashMap<String, Long> getAccountMap() {
		
		HashMap<String,Long> map = new HashMap<String,Long>();
		String query = "Select accountNo from account";
		try {
			prep = connection.prepareStatement(query);
			ResultSet result = prep.executeQuery();
			while(result.next()) {
				map.put(Integer.toString(result.getInt("accountNo")), Long.valueOf(result.getInt("accountNo")));
			} 
		for(String key : map.keySet()) {
			System.out.println(" = "+key+" - "+map.get(key));
		}
		return map;
		}catch (SQLException e) {
				e.printStackTrace();
				return null;
		}
		
		
	}

//	public void updateFundTransferedAccount(long toAccount, double amount) {
//		String query = "UPDATE account SET balance = balance+"+amount+" WHERE accountNo = ? ;";
//		try {
//			prep = connection.prepareStatement(query);
//			prep.setLong(1, toAccount);
//			int result = prep.executeUpdate();
//			if (result==1)
//				System.out.println("FundTransfered Account balance updated");
//			else
//				System.out.println("FundTransfered Account balance updation failed");		
//			} catch (SQLException e) {
//			e.printStackTrace();
//		}	
//	}
		
}
