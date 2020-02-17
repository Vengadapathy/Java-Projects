package ATM;

import java.sql.SQLException;

import Objects.Account;
import Objects.Transaction;

public interface ATM_OperationsInterface {
	public static final String CREDIT = "CREDIT";
	public static final String DEBIT = "DEBIT";
	public static final double withdrawLimit = 25000.00d ;
	public static final double depositLimit = 25000.00d ;
	
	void deposit(Transaction transaction) throws SQLException;
	void withdraw(Transaction transaction) throws SQLException;
	void balanceEnquiry(Account account) ;
	void miniStatement(Account account);
	void pinChange(Account account);

}
