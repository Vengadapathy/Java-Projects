package objects;

public class ParkingSlot {
			int parkingSlotID ;
			int parkingSlotFloor ;
			String slotType;
			ParkingBuilding parkingBuilding ;
			boolean isSlotBooked ;
			Employee employee;
			
			public ParkingSlot(int parkingSlotID, int parkingSlotFloor, String slotType, ParkingBuilding parkingBuilding,Employee employee) {
				this.parkingSlotID = parkingSlotID;
				this.parkingSlotFloor = parkingSlotFloor;
				this.slotType = slotType;
				this.parkingBuilding = parkingBuilding;
				this.isSlotBooked = false ;
				this.employee = employee ;
			}
			

			public Employee getEmployee() {
				return employee;
			}


			public void setEmployee(Employee employee) {
				this.employee = employee;
			}


			public String getSlotType() {
				return slotType;
			}

			public void setSlotType(String slotType) {
				this.slotType = slotType;
			}

			public void setParkingSlotID(int parkingSlotID) {
				this.parkingSlotID = parkingSlotID;
			}

			public int getParkingSlotID() {
				return parkingSlotID;
			}

			public int getParkingSlotFloor() {
				return parkingSlotFloor;
			}
			public void setParkingSlotFloor(int parkingSlotFloor) {
				this.parkingSlotFloor = parkingSlotFloor;
			}
			public ParkingBuilding getParkingBuilding() {
				return parkingBuilding;
			}
			public void setParkingBuilding(ParkingBuilding parkingBuilding) {
				this.parkingBuilding = parkingBuilding;
			}
			public boolean isSlotBooked() {
				return isSlotBooked;
			}
			public void setSlotBooked(boolean isSlotBooked) {
				this.isSlotBooked = isSlotBooked;
			}
}
