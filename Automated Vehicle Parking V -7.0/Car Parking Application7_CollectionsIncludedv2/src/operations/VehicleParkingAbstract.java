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
	private static Map<Integer,ParkingSlot> vehicleToParkedSlot = new HashMap<>();
	private static Map<Integer,ParkingSlot> vehicleToBookedSlot = new HashMap<>();

	public void initialize() {
	
			database  = new Database();
			input = new Scanner(System.in);
			database.getConnection();
			VehicleParkingAbstract.parkingBlock = database.loadParkingBlock();
			VehicleParkingAbstract.parkingFloor = database.loadParkingFloor( parkingBlock );
			VehicleParkingAbstract.parkingSlot = database.loadParkingSlot( parkingFloor );
			VehicleParkingAbstract.parkingMap = database.loadParkingMap( parkingSlot );
			VehicleParkingAbstract.bookingMap = database.loadBookingMap( parkingSlot );
			VehicleParkingAbstract.vehicleToParkedSlot =  database.loadVehicleToParkedSlot();
			VehicleParkingAbstract.vehicleToBookedSlot = database.loadVehicleToBookedSlot();
			printAllMaps();
		
	}

	public void slotAvailableFloor() {
		System.out.print("Two wheelers slot available in \t");
		database.getSlotAvailableFloor(ONE) ;
		System.out.print("Four wheeler slot available in \t");
		database.getSlotAvailableFloor(TWO) ;
	}

	public void parkingSlotBooking(Vehicle vehicle) {
		SlotOperation slotOperation = null ;
		if( vehicleToBookedSlot.get(vehicle.getVehicleID())==null     ) {
				ParkingSlot slot  = database.getFreeSlot( vehicle ,ONE );
		if(	slot != null 	&&   (	parkingMap.get(slot.getParkingSlotID())  == null ||  parkingMap.get(slot.getParkingSlotID()).getVehicle().getVehicleID()  == vehicle.getVehicleID()  ) ) {
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
			System.out.println(" The slot booked from you is  "+slot.getParkingSlotID() +" in "+slot.getFloor().getFloorName()+" of "+slot.getFloor().getParkingBuilding().getBlockName()+" Block ");	
			slot.setVehicle(vehicle);
			parkingSlot.put(slot.getParkingSlotID(), slot);
			vehicleToBookedSlot.put(vehicle.getVehicleID(), slot);
		}	else 	{
			System.out.println("All slots are engaged. No free slots available");
		}
//			printAllMaps();

		}
		else	{	System.out.println("Parking slot already booked for this vehicle");	}
	}
	
	public void cancelBookedSlot(Vehicle vehicle) {
		if( vehicleToBookedSlot.get(vehicle.getVehicleID()) != null     ) {
			ParkingSlot slot = vehicleToBookedSlot.get(vehicle.getVehicleID()) ;
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
			vehicleToBookedSlot.remove(vehicle.getVehicleID());
//			printAllMaps();
		}	else	{	System.out.println("No slot booking found for this vehicle");	}
		}
	
	public void vehicleParking(Vehicle vehicle ) {
		ParkingSlot slot  = null ;
			if(  vehicleToParkedSlot.get(vehicle.getVehicleID()) != null   ) {
				System.out.println("Vehicle already parked ");
			}	else	{
						if( vehicleToBookedSlot.get(vehicle.getVehicleID()) != null  &&  vehicleToBookedSlot.get(vehicle.getVehicleID()).getVehicle().getVehicleID() == vehicle.getVehicleID()   ) {
							slot = vehicleToBookedSlot.get(vehicle.getVehicleID()) ;
						}	else  {
							slot  = database.getFreeSlot( vehicle ,ONE );	
			}
			if(slot != null ) {
				VehicleParking parking = database.parkingSlotEntry(slot , vehicle );	
				parkingMap.put(slot.getParkingSlotID(), parking ) ;
				vehicleToParkedSlot.put(vehicle.getVehicleID(), slot) ;
			//	printAllMaps();
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
				vehicleToParkedSlot.remove(vehicle.getVehicleID()) ;
//				printAllMaps();
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
		return vehicleToParkedSlot.get(vehicle.getVehicleID()).getParkingSlotID();
	}
	
	public void bookedSlotUnavailability(Vehicle vehicle) {
		ParkingSlot slot = vehicleToBookedSlot.get(vehicle.getVehicleID()) ;
		if(	slot !=null  &&	parkingMap.get(slot.getParkingSlotID())!=null	 &&  ( parkingMap.get(slot.getParkingSlotID()).getVehicle().getVehicleID() == vehicle.getVehicleID() )	)	{
		System.out.println("Your vehicle is parked in your booked slot. No other vehicle is parked");
		} else 	if(	vehicleToBookedSlot.get(vehicle.getVehicleID())!=null  &&	parkingMap.get(slot.getParkingSlotID())!=null 	)	{
		database.unavailableBookedSlot(vehicle.getEmployee(),slot);
		} else		{	System.out.println("No vehicles parked in your booked slot");
		}
	}
	
	public void getSlotOperationRegister() {
		database.getSlotOperationList();
	}

	void printAllMaps() {
		for(	int building : parkingBlock.keySet()	) {
			System.out.println(building+"     "+parkingBlock.get(building));
		}
		
		System.out.println("PArking Floors Map Values");
		for(	int floor : parkingFloor.keySet()	) {
			System.out.println(floor+"     "+parkingFloor.get(floor));
		}
		
		System.out.println("PArking Slot Map Values");
		for(	int slot : parkingSlot.keySet()	) {
			System.out.println(slot+"     "+parkingSlot.get(slot));
		}
		
		System.out.println("Vehicle PArking  Map Values");
		for(	int floor : parkingMap.keySet()	) {
			System.out.println(floor+"     "+parkingMap.get(floor));
		}
		
		System.out.println("PArking Slot Booking  Map Values");
		for(	int slot : bookingMap.keySet()	) {
			System.out.println(slot+"     "+bookingMap.get(slot));
		}
		
		System.out.println("Vehicle PArking  Map Values");
		for(	int floor : vehicleToParkedSlot.keySet()	) {
			System.out.println(floor+"     "+vehicleToParkedSlot.get(floor));
		}
		
		System.out.println("PArking Slot Booking  Map Values");
		for(	int slot : vehicleToBookedSlot.keySet()	) {
			System.out.println(slot+"     "+vehicleToBookedSlot.get(slot));
		}
	}
}