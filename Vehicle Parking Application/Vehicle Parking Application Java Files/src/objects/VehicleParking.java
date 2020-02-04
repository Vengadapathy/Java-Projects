package objects;

import java.util.Date;

public class VehicleParking {
	int parkingid ;
	ParkingSlot parkingSlot ;
	Date inTime ;
	Date outTime ;
	Vehicle vehicle ;
	
	public VehicleParking(int parkingid,ParkingSlot parkingSlot, Vehicle vehicle) {
		this.parkingid = parkingid ;
		this.parkingSlot = parkingSlot;
		this.inTime = new Date();
		this.outTime = null ;
		this.vehicle = vehicle;
	}
	public int getParkingid() {
		return parkingid;
	}
	public void setParkingid(int parkingid) {
		this.parkingid = parkingid;
	}
	public ParkingSlot getParkingSlot() {
		return parkingSlot;
	}
	public void setParkingSlot(ParkingSlot parkingSlot) {
		this.parkingSlot = parkingSlot;
	}
	public Date getInTime() {
		return inTime;
	}
	public void setInTime(Date inTime) {
		this.inTime = inTime;
	}
	public Date getOutTime() {
		return outTime;
	}
	public void setOutTime(Date outTime) {
		this.outTime = outTime;
	}
	public Vehicle getVehicle() {
		return vehicle;
	}
	public void setVehicle(Vehicle vehicle) {
		this.vehicle = vehicle;
	}
	
}
