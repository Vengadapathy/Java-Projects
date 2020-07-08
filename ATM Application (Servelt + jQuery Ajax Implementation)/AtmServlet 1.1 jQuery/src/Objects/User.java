package Objects;

public class User {
	int userID ;
	String userName;
	String mailID;
	long phoneNo;
	String address;
	
	public User(int userID,String userName, String mailID, long phoneNo, String address) {
		this.userID = userID ;
		this.userName = userName;
		this.mailID = mailID;
		this.phoneNo = phoneNo;
		this.address = address;
	}
	
	public int getUserID() {
		return userID;
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
