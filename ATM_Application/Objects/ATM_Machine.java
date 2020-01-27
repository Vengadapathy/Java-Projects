package Objects;

import java.util.Date;

public class ATM_Machine {

	String location;
	String branchCode;
	String machineType;
	double amountRemaining;
	boolean liveStatus;
	boolean userAuthentication;
	Date date ;

	public ATM_Machine(String location, String branchCode, String machineType, double amountRemaining,boolean liveStatus, boolean userAuthentication) {
		this.location = location;
		this.branchCode = branchCode;
		this.machineType = machineType;
		this.amountRemaining = amountRemaining;
		this.liveStatus = liveStatus;
		this.userAuthentication = userAuthentication;
		this.date = new Date();
	}

	public String getLocation() {
		return location;
	}

	public String getBranchCode() {
		return branchCode;
	}

	public String getMachineType() {
		return machineType;
	}

	public double getAmountRemaining() {
		return amountRemaining;
	}

	public void setAmountRemaining(double amountRemaining) {
		this.amountRemaining = amountRemaining;
	}

	public boolean isLiveStatus() {
		return liveStatus;
	}

	public void setLiveStatus(boolean liveStatus) {
		this.liveStatus = liveStatus;
	}

	public boolean isUserAuthentication() {
		return userAuthentication;
	}

	public void setUserAuthentication(boolean userAuthentication) {
		this.userAuthentication = userAuthentication;
	}

	public Date getDate() {
		return date;
	}

}
