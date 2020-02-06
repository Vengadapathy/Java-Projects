package objects;

public class FloorObject {
		int floorNo ;
		String floorName;
		ParkingBuilding parkingBuilding ;
		String floorType;
		int slotCount ;
	
		public FloorObject(int floorNo, String floorName, ParkingBuilding parkingBuilding, String floorType,int slotCount) {
			this.floorNo = floorNo;
			this.floorName = floorName;
			this.parkingBuilding = parkingBuilding;
			this.floorType = floorType;
			this.slotCount = slotCount;
		}
		public int getSlotCount() {
			return slotCount;
		}
		public void setSlotCount(int slotCount) {
			this.slotCount = slotCount;
		}
		public int getFloorNo() {
			return floorNo;
		}
		public void setFloorNo(int floorNo) {
			this.floorNo = floorNo;
		}
		public String getFloorName() {
			return floorName;
		}
		public void setFloorName(String floorName) {
			this.floorName = floorName;
		}
		public ParkingBuilding getParkingBuilding() {
			return parkingBuilding;
		}
		public void setParkingBuilding(ParkingBuilding parkingBuilding) {
			this.parkingBuilding = parkingBuilding;
		}
		public String getFloorType() {
			return floorType;
		}
		public void setFloorType(String floorType) {
			this.floorType = floorType;
		}
}
