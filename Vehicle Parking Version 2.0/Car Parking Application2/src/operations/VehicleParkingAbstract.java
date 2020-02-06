package operations;

import java.util.Scanner;

import databaseOperations.Database;
import objects.ParkingSlot;
import objects.Vehicle;
import objects.VehicleParking;

public abstract class VehicleParkingAbstract implements VehicleParkingInterface {
	Database database = null ;
	Scanner input = null ;
	
	public void initialize() {
			database  = new Database();
			database.getConnection();
			input = new Scanner(System.in);
	}

	public void slotAvailableFloor() {
		System.out.print("Two wheelers slot available in \t");
		database.getSlotAvailableFloor(ONE) ;
		System.out.print("Four wheeler slot available in \t");
		database.getSlotAvailableFloor(TWO) ;
	}

	public void parkingSlotBooking(Vehicle vehicle) {
		// get computer generated parking slot 
		ParkingSlot slot  = database.getBookingSlot( vehicle ,ONE );
		System.out.println(slot.getParkingSlotID());
			database.parkingSlotOperation(vehicle.getEmployee().getEmpID(),slot.getParkingSlotID(),ONE,ONE);
			System.out.println(" The slot booked from you is  "+slot.getParkingSlotID() +" in "+slot.getFloor().getFloorName()+" of "+slot.getFloor().getParkingBuilding().getBlockName()+" Block ");	
	}
	
	public void cancelBookedSlot(Vehicle vehicle) {
		ParkingSlot slot  = database.getBookingSlot(vehicle,TWO);
		if(slot.getEmployee() != null  &&  slot.getEmployee().getEmpID()  ==  vehicle.getEmployee().getEmpID())
			database.parkingSlotOperation(vehicle.getEmployee().getEmpID()  , slot.getParkingSlotID() ,TWO,TWO);	
			System.out.println("Booked slot cancelling successful");	
		}
	
	public void vehicleParking(Vehicle vehicle ) {
		ParkingSlot slot  = database.getParkingSlot( getSlotID() );
		if( database.checkSlotAvailability(slot.getParkingSlotID()) && 		database.isNotVehicleParked(vehicle.getVehicleID()) )
			{		database.parkingSlotEntry(slot.getParkingSlotID(), vehicle.getVehicleID());		}
		else	{	System.out.println("Cannot park your vehicle or Parking slot failed");	}
	}
	
	public void departVehicle(Vehicle vehicle) {
		ParkingSlot slot = database.getParkingSlot( getVehicleParkedSlot(vehicle) );
		VehicleParking parking = database.getVehicleParking(slot, vehicle);
		database.updateParkingSlotEntry(parking.getParkingid());
	}
	
	public void findVehicle(Vehicle vehicle) {
		ParkingSlot slot = database.getParkingSlot( getVehicleParkedSlot(vehicle) );
		System.out.println("Your vehicle is parked in ParkingSlot "+slot.getParkingSlotID() +" in "+slot.getFloor().getFloorName() +" of "+slot.getFloor().getParkingBuilding().getBlockName()+" BLOCK");		
	}

	public int getVehicleParkedSlot(Vehicle vehicle) {
		return database.getVehicleSlotID(vehicle.getVehicleNo() );
	}
	
	public void bookedSlotUnavailability(Vehicle vehicle) {
		ParkingSlot slot  = database.getBookingSlot(vehicle,TWO);
		if(	!	(	database.checkSlotAvailability(slot.getParkingSlotID())	)	)	{
		database.unavailableBookedSlot(vehicle.getEmployee(),slot);	}
		else	{	System.out.println("No vehicles parked in your booked slot");	}
	}
	
	public void getSlotOperationRegister() {
		database.getSlotOperationList();
	}
	
	int getSlotID() {
			System.out.println("Enter the SlotID\n");
			int slotid = input.nextInt();
			return slotid ;
	}
		
}
