package Objects;

public class AtmCard {
	long cardNo;
	int PIN;
	Account account;
	
	public AtmCard(long cardNo, int PIN ,Account account){
		this.cardNo = cardNo;
		this.PIN = PIN;
		this.account = account;
	}

	public long getCardNo() {
		return cardNo;
	}

	public void setCardNo(long cardNo) {
		this.cardNo = cardNo;
	}

	public int getPIN() {
		return PIN;
	}

	public void setPIN(int pIN) {
		PIN = pIN;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}
	
	
	
}
