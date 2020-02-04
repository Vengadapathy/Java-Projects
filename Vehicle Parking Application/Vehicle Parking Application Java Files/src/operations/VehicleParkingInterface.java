package operations;

import objects.Employee;
import objects.ParkingSlot;
import objects.Vehicle;
import objects.VehicleParking;

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
		void parkingSlotBooking(Employee employee) ;
		void cancelBookedSlot(Vehicle vehicle);
		boolean vehicleParking(Vehicle vehicle) ;
		void findVehicle(Vehicle vehicle) ;
}
