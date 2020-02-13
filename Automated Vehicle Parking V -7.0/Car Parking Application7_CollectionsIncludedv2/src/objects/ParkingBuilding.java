package objects;

public class ParkingBuilding {
		int blockID ;
		String blockName ;
		int totalFloors ;
		String location ;
		int twoWheelerSlots ;
		int fourWheelerSlots ;
		
		public ParkingBuilding(int blockID, String blockName, int totalFloors, String location, int twoWheelerSlots, int fourWheelerSlots) {
			this.blockID = blockID;
			this.blockName = blockName;
			this.totalFloors = totalFloors;
			this.location = location;
			this.twoWheelerSlots = twoWheelerSlots;
			this.fourWheelerSlots = fourWheelerSlots;
		}
		public int getTwoWheelerSlots() {
			return twoWheelerSlots;
		}
		public void setTwoWheelerSlots(int twoWheelerSlots) {
			this.twoWheelerSlots = twoWheelerSlots;
		}
		public int getFourWheelerSlots() {
			return fourWheelerSlots;
		}
		public void setFourWheelerSlots(int fourWheelerSlots) {
			this.fourWheelerSlots = fourWheelerSlots;
		}
		public int getBlockID() {
			return blockID;
		}
		public void setBlockID(int blockID) {
			this.blockID = blockID;
		}
		public String getBlockName() {
			return blockName;
		}
		public void setBlockName(String blockName) {
			this.blockName = blockName;
		}
		public int getTotalFloors() {
			return totalFloors;
		}
		public void setTotalFloors(int totalFloors) {
			this.totalFloors = totalFloors;
		}
		public String getLocation() {
			return location;
		}
		public void setLocation(String location) {
			this.location = location;
		}
		@Override
		public String toString() {
			return "ParkingBuilding [blockID=" + blockID + ", blockName=" + blockName + ", totalFloors=" + totalFloors
					+ ", location=" + location + ", twoWheelerSlots=" + twoWheelerSlots + ", fourWheelerSlots="
					+ fourWheelerSlots + "]";
		}

		
		
		
}
