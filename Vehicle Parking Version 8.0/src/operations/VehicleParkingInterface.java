package operations;

import objects.Vehicle;

public interface VehicleParkingInterface {
		
		public static final int TWOWHEELERSLOT =  50 ;
		public static final int FOURWHEELERSLOT  = 20 ;
		public static final String TWOWHEELER =  " Two Wheeler ";
		public static final String FOURWHEELER  = "Four Wheeler " ;
		public static final int ONE = 1 ;
		public static final int TWO = 2 ;
		public static final int THREE = 3 ;
		public static final int FOUR = 4 ;
		public static final int FIVE = 5;
		public static final int SIX = 6;
		public static final int SEVEN = 7;
		public  static final String RED = "RED";
		
		void slotAvailableFloor() ;
		void parkingSlotBooking(Vehicle vehicle) ;
		void cancelBookedSlot(Vehicle vehicle);
		void vehicleParking(Vehicle vehicle) ;
		void findVehicle(Vehicle vehicle) ;
}
