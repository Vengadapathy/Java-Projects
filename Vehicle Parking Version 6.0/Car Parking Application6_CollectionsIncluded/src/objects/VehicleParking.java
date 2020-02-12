package objects;

import java.util.Date;

public class VehicleParking {
	ParkingSlot parkingSlot ;
	Date inTime ;
	Date outTime ;
	Vehicle vehicle ;
	Employee employee ;

	public VehicleParking( ParkingSlot parkingSlot, Date inTime  , Vehicle vehicle, 	Employee employee) {
		this.parkingSlot = parkingSlot;
		this.inTime = inTime;
		this.outTime = null ;
		this.vehicle = vehicle;
		this.employee = employee;
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
	public Employee getEmployee() {
		return employee;
	}
	public void setEmployee(Employee employee) {
		this.employee = employee;
	}
	@Override
	public String toString() {
		return "VehicleParking [parkingSlot=" + parkingSlot + ", inTime=" + inTime + ", outTime=" + outTime
				+ ", vehicle=" + vehicle + ", employee=" + employee + "]";
	}
	
	
}
