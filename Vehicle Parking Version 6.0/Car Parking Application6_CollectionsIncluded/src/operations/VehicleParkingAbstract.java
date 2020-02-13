package operations;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import databaseOperations.Database;
import objects.FloorObject;
import objects.ParkingBuilding;
import objects.ParkingSlot;
import objects.SlotOperation;
import objects.Vehicle;
import objects.VehicleParking;

public abstract class VehicleParkingAbstract implements VehicleParkingInterface {
	Database database = null ;
	Scanner input = null ;
	private static Map<Integer,VehicleParking> parkingMap = new HashMap<>();
	private static Map<Integer,SlotOperation> bookingMap = new HashMap<>();	
	private static Map<Integer,ParkingSlot> parkingSlot = new HashMap<>();
	private static Map<Integer,FloorObject> parkingFloor = new HashMap<>();
	private static Map<Integer,ParkingBuilding> parkingBlock = new HashMap<>();

	public void initialize() {
		
			database  = new Database();
			input = new Scanner(System.in);
			database.getConnection();
			parkingBlock = database.loadParkingBlock();
			parkingFloor = database.loadParkingFloor( parkingBlock );
			parkingSlot = database.loadParkingSlot( parkingFloor );
			parkingMap = database.loadParkingMap( parkingSlot );
			bookingMap = database.loadBookingMap( parkingSlot );
	}

	public void slotAvailableFloor() {
		database.getSlotAvailableFloor(ONE) ;
		database.getSlotAvailableFloor(TWO) ;
	}

//	public void parkingSlotBooking(Vehicle vehicle) {
//		if( database.isNotVehicleBooked(vehicle.getVehicleID()) ) {
//		ParkingSlot slot  = database.getFreeSlot( vehicle ,ONE );
//		if(	slot!= null 	&& 	 parkingMap.get(slot.getParkingSlotID())== null	 ) {
//			SlotOperation slotOperation = database.bookParkingSlot(slot,vehicle);
//			bookingMap.put(	slot.getParkingSlotID() , slotOperation );
//			System.out.println(bookingMap.get(slot.getParkingSlotID()));
//			System.out.println(" The slot booked from you is  "+slot.getParkingSlotID() +" in "+slot.getFloor().getFloorName()+" of "+slot.getFloor().getParkingBuilding().getBlockName()+" Block ");}	}
//		else	{	System.out.println("Parking slot already booked for this vehicle or slot not available");	}
//	}
	
	public void parkingSlotBooking(Vehicle vehicle) {
		SlotOperation slotOperation = null ;
		if( database.isNotVehicleBooked(vehicle.getVehicleID()) ) {
				ParkingSlot slot  = database.getFreeSlot( vehicle ,ONE );
		System.out.println(slot.getParkingSlotID());
		if(	slot!= null 	&&   (	parkingMap.get(slot.getParkingSlotID())  == null ||  parkingMap.get(slot.getParkingSlotID()).getVehicle().getVehicleID()  == vehicle.getVehicleID()  ) ) {
			try {
					database.connection.setAutoCommit(false);
					slotOperation = database.bookParkingSlot(slot,vehicle);
					database.connection.commit();
			} catch (SQLException excep) {
				excep.printStackTrace();
							try {
								database.connection.rollback();
							} catch (SQLException e) {
								e.printStackTrace();
							}
			} finally {
							try {
								database.connection.setAutoCommit(true);
							} catch (SQLException e) {
								e.printStackTrace();
							}
			}
			bookingMap.put(	slot.getParkingSlotID() , slotOperation );
			System.out.println(" The slot booked from you is  "+slot.getParkingSlotID() +" in "+slot.getFloor().getFloorName()+" of "+slot.getFloor().getParkingBuilding().getBlockName()+" Block ");}	}
		else	{	System.out.println("Parking slot already booked for this vehicle or slot not available");	}
	}
	
	public void cancelBookedSlot(Vehicle vehicle) {
		if(	! database.isNotVehicleBooked(vehicle.getVehicleID()) ) {
			ParkingSlot slot  = database.getFreeSlot(vehicle,TWO);
			try {
				database.connection.setAutoCommit(false);
			database.cancelBookedSlot( bookingMap.get(slot.getParkingSlotID()) );
			database.connection.commit();
			} catch (SQLException excep) {
				excep.printStackTrace();
							try {
								database.connection.rollback();
							} catch (SQLException e) {
								e.printStackTrace();
							}
				} finally {
							try {
								database.connection.setAutoCommit(true);
							} catch (SQLException e) {
								e.printStackTrace();
							}
				}
			slot.setVehicle(null);
			parkingSlot.put(slot.getParkingSlotID(), slot);
			bookingMap.remove(slot.getParkingSlotID());
			System.out.println("Booked slot cancelling successful");
		}	else	{	System.out.println("No slot booking found for this vehicle");	}
		}
	
	public void vehicleParking(Vehicle vehicle ) {
		ParkingSlot slot  = null ;
		if(  ! database.isNotVehicleParked(vehicle.getVehicleID())  ) {
			System.out.println("Vehicle already parked ");
		}	else	{
					if(	! 	database.isNotVehicleBooked(vehicle.getVehicleID())	) {
							slot = database.getFreeSlot(vehicle,TWO);
							if(!  database.isSlotNotParked(slot.getParkingSlotID()) )
							{	slot  = database.getFreeSlot( vehicle ,ONE ); }
					}	else  {
							slot  = database.getFreeSlot( vehicle ,ONE );	}
		if(slot != null ) {
			VehicleParking parking = database.parkingSlotEntry(slot , vehicle );	
				parkingMap.put(slot.getParkingSlotID(), parking ) ;
		}
		}	
	}
	
	public void departVehicle(Vehicle vehicle) {
		ParkingSlot slot = parkingSlot.get(getVehicleParkedSlot(vehicle));
		if( slot == null ) {
				System.out.println("No slot found for this vehicle");
		}	else	{
				VehicleParking parking = parkingMap.get(slot.getParkingSlotID());
				database.updateParkingSlotEntry( parking );
				parkingMap.remove(slot.getParkingSlotID());
		}
	}
	
	public void findVehicle(Vehicle vehicle) {
		ParkingSlot slot = parkingSlot.get(getVehicleParkedSlot(vehicle));
		if( slot == null ) {
				System.out.println("No slot found for this vehicle");
		}	else	{
				System.out.println("Your vehicle is parked in ParkingSlot "+slot.getParkingSlotID() +" in "+slot.getFloor().getFloorName() +" of "+slot.getFloor().getParkingBuilding().getBlockName()+" BLOCK");		}
	}

	public int getVehicleParkedSlot(Vehicle vehicle) {
		return database.getVehicleSlotID(vehicle.getVehicleNo() );
	}
	
	public void bookedSlotUnavailability(Vehicle vehicle) {
		ParkingSlot slot  = database.getFreeSlot(vehicle,TWO);
		if(	!database.isNotVehicleBooked(vehicle.getVehicleID()) &&	!(	database.checkSlotAvailability(slot.getParkingSlotID())	&& parkingMap.get(slot.getParkingSlotID()).getVehicle().getVehicleID() == vehicle.getVehicleID() )	)	{
			System.out.println("Your vehicle is parked in your booked slot. No other vehicle is parked");
		} else 	if(	!database.isNotVehicleBooked(vehicle.getVehicleID()) &&	!(	database.checkSlotAvailability(slot.getParkingSlotID())	)	)	{
		database.unavailableBookedSlot(vehicle.getEmployee(),slot);
		} else		{	System.out.println("No vehicles parked in your booked slot");
		}
	}
	
	public void getSlotOperationRegister() {
		database.getSlotOperationList();
	}
	
//	
//	void printAllMaps() {
//		for(	int building : parkingBlock.keySet()	) {
//			System.out.println(building+"     "+parkingBlock.get(building));
//		}
//		
//		System.out.println("PArking Floors Map Values");
//		for(	int floor : parkingFloor.keySet()	) {
//			System.out.println(floor+"     "+parkingFloor.get(floor));
//		}
//		
//		System.out.println("PArking Slot Map Values");
//		for(	int slot : parkingSlot.keySet()	) {
//			System.out.println(slot+"     "+parkingSlot.get(slot));
//		}
//		
//		System.out.println("Vehicle PArking  Map Values");
//		for(	int floor : parkingMap.keySet()	) {
//			System.out.println(floor+"     "+parkingMap.get(floor));
//		}
//		
//		System.out.println("PArking Slot Booking  Map Values");
//		for(	int slot : bookingMap.keySet()	) {
//			System.out.println(slot+"     "+bookingMap.get(slot));
//		}
//	}
//	
	
	
}
