package objects;

public class ParkingSlot {
			int parkingSlotID ;
			FloorObject floor; 
			Employee employee;
			
			public ParkingSlot(int parkingSlotID, FloorObject floor, Employee employee) {
				this.parkingSlotID = parkingSlotID;
				this.floor = floor;
				this.employee = employee;
			}
			public FloorObject getFloor() {
				return floor;
			}
			public void setFloor(FloorObject floor) {
				this.floor = floor;
			}
			public Employee getEmployee() {
				return employee;
			}
			public void setEmployee(Employee employee) {
				this.employee = employee;
			}
			public void setParkingSlotID(int parkingSlotID) {
				this.parkingSlotID = parkingSlotID;
			}
			public int getParkingSlotID() {
				return parkingSlotID;
			}
	
}
