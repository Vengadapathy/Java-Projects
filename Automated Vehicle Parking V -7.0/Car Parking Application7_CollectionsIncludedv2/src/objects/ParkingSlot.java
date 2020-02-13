package objects;

public class ParkingSlot {
			int parkingSlotID ;
			FloorObject floor; 
			Vehicle vehicle;
			
			public ParkingSlot(int parkingSlotID, FloorObject floor, Vehicle vehicle) {
				this.parkingSlotID = parkingSlotID;
				this.floor = floor;
				this.vehicle = vehicle ;
			}
			public FloorObject getFloor() {
				return floor;
			}
			public void setFloor(FloorObject floor) {
				this.floor = floor;
			}
			public Vehicle getVehicle() {
				return vehicle;
			}
			public void setVehicle(Vehicle vehicle) {
				this.vehicle = vehicle;
			}
			public void setParkingSlotID(int parkingSlotID) {
				this.parkingSlotID = parkingSlotID;
			}
			public int getParkingSlotID() {
				return parkingSlotID;
			}
			@Override
			public String toString() {
				return "ParkingSlot [parkingSlotID=" + parkingSlotID + ", floor=" + floor + ", vehicle=" + vehicle
						+ "]";
			}
			
			
	
}
