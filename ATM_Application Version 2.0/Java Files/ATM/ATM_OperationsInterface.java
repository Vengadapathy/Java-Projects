package ATM;

public interface ATM_OperationsInterface {
	public static final String CREDIT = "CREDIT";
	public static final String DEBIT = "DEBIT";
	public static final double withdrawLimit = 25000.00d ;
	public static final double depositLimit = 25000.00d ;
	
	void deposit();
	void withdraw();
	void balanceEnquiry();
	void miniStatement();
	void pinChange();

}
