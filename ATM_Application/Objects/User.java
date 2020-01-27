package Objects;

public class User {
	String userName;
	String mailID;
	long phoneNo;
	String address;
	
	public User(String userName, String mailID, long phoneNo, String address) {
		this.userName = userName;
		this.mailID = mailID;
		this.phoneNo = phoneNo;
		this.address = address;
	}
	
	public String getUserName() {
		return userName;
	}

	public String getMailID() {
		return mailID;
	}

	public long getPhoneNo() {
		return phoneNo;
	}

	public String getAddress() {
		return address;
	}

}
