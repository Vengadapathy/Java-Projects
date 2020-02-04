package operations;

import java.util.Scanner;

import databaseOperations.Database;
import objects.Employee;
import objects.ParkingSlot;
import objects.SlotOperation;
import objects.Vehicle;
import objects.VehicleParking;

public abstract class VehicleParkingAbstract implements VehicleParkingInterface {
	Scanner input ;
	Database database;
	void initialize() {
	  input = new Scanner(System.in);
	database  = new Database();
	}
	public void slotAvailableFloor() {
		database.getConnection();
		System.out.println( "\nTwo Wheeler slots available in floorNumber : "+database.getSlotAvailableFloor(TWOWHEELERSLOT,TWOWHEELER) );
		System.out.println( "\nFour Wheeler slots available in floorNumber : "+database.getSlotAvailableFloor(FOURWHEELERSLOT, FOURWHEELER)+"\n" );
	}

	public void parkingSlotBooking(Employee employee) {
		System.out.println("Enter the parking slot you want to book");
		ParkingSlot slot  = database.getParkingSlot(input.nextInt(),ONE);
		if( database.checkSlotAvailability(slot.getParkingSlotID()) )
			database.parkingSlotOperation(employee.getEmpID(),slot.getParkingSlotID(),ONE,ONE);
		else
			System.out.println("Parking Slot unavailable !!! ");
	}
	
	public void cancelBookedSlot(Vehicle vehicle) {
		database.parkingSlotOperation(vehicle.getEmployee().getEmpID(),getVehicleParkedSlot(vehicle).getParkingSlotID(),TWO,TWO);
	}
	
	public boolean vehicleParking(Vehicle vehicle) {
		System.out.println("Enter the slot where your vehicle is parked");
		ParkingSlot slot  = database.getParkingSlot(input.nextInt(),ONE);
		VehicleParking parking = new VehicleParking(1,slot, vehicle);
		database.parkingSlotEntry( parking.getParkingSlot().getParkingSlotID() , parking.getVehicle().getVehicleNo());
		return false;
	}
	
	public void departVehicle(Vehicle vehicle) {
		ParkingSlot slot  = database.getParkingSlot(101,ONE);
		VehicleParking parking = new VehicleParking(1,slot, vehicle);
		database.updateParkingSlotEntry(parking.getParkingid());
	}
	
	public void findVehicle(Vehicle vehicle) {
		ParkingSlot slot = getVehicleParkedSlot(vehicle);
		System.out.println("Your vehicle is parked in "+slot.getParkingBuilding().getBlockName() +" block .   Floor number : "+slot.getParkingSlotFloor()+" and  slotID : "+slot.getParkingSlotID());		
	}
	
	public ParkingSlot getVehicleParkedSlot(Vehicle vehicle) {
		return database.getParkingSlot(vehicle.getVehicleNo() , TWO );
	}
	
	public void bookedSlotUnavailability(Vehicle vehicle) {
		SlotOperation slotBooked = database.getSlotBooking(vehicle.getEmployee() , ONE);
		database.unavailableBookedSlot(vehicle,slotBooked);
		
	}

}
