package ATM;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import Objects.Account;
import Objects.AtmCard;
import Objects.Transaction;

public interface ATM_OperationsInterface {
	public static final String CREDIT = "CREDIT";
	public static final String DEBIT = "DEBIT";
	public static final double withdrawLimit = 25000.00d ;
	public static final double depositLimit = 25000.00d ;
	
	boolean deposit(Transaction transaction) throws SQLException;
	boolean withdraw(Transaction transaction) throws SQLException;
	void balanceEnquiry(Account account) ;
	ArrayList<LinkedHashMap<String, String>> miniStatement(Account account);
	boolean pinChange(AtmCard atmcard);

}
