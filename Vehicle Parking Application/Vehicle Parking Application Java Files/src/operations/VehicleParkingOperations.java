package operations;

import java.util.InputMismatchException;
import java.util.Scanner;

import databaseOperations.Database;
import objects.Employee;
import objects.Vehicle;

public class VehicleParkingOperations extends VehicleParkingAbstract  {
	
	int choice ;
	public void start(){
		initialize();
		try {
		display();
		} catch(InputMismatchException ex) {
			System.out.println("Invalid Input entered");
		} finally {
			start();
		}	
	}
	public void display() {
		System.out.println("\n\nWelcome to ZOHO Multi-Level Car Parking Block");
		slotAvailableFloor();
		
		System.out.println("Login as \n   1.Admin\n   2.Employee");
		choice = input.nextInt();
		switch(choice) {
				case ONE :
					adminLogin();
					break;
				case TWO:
					System.out.println("Enter your employee ID");
					int empid = input.nextInt();
					parkingServices(empid);
					break;
				default :
					System.out.println("Invalid Choice");
					display();
		}
	}
	private void adminLogin() {
			
			System.out.println("Choose the required operation to be performed\n");
			System.out.println("1.Employee Parking Slot list");
			System.out.println("2.Request for shifting employee vehicles");
			System.out.println("3.Vehicle parked list for a period of time");
			System.out.println("4.Exit");
			choice = input.nextInt();
			switch(choice) {
			case ONE:
				employeeParkingSlotList();
				break;
			case TWO:
				vehicleShiftingRequest();
				break;
			case THREE:
				filteredParkingList();
				break;
			case FOUR:
				System.out.println("Thank you for using car parking services !!! ");
				display();
			default :
				System.out.println("Invalid choice");
			}
			adminLogin();
	}
	public void filteredParkingList() {
		System.out.println("Enter the number of days for which you want to display the parking list\n");
		int days = input.nextInt();
		database.getParkingSlotList(days);
	}
	public void vehicleShiftingRequest() {
		System.out.println("Enter the blockid for which vehicle shift request is to be sent");
		int blockid = input.nextInt();
		System.out.println("Enter the floor number where recruitment drive is planned to be conducted");
		int floorno=input.nextInt();
		database.sendRequest(blockid, floorno);
	}
	public void employeeParkingSlotList() {
		System.out.println("Enter employeeID whose parking history is to be obtained");
		int empid = input.nextInt();
		database.getEmployeeParkinglist(empid);
	}
	public void parkingServices(int employeeID) {
		
			Database database = new Database();
			Employee employee = database.getEmployee(employeeID);
			//ParkingSlot slot  = database.getParkingSlot(101,ONE);
			//ParkingBuilding block = new ParkingBuilding(1,"MLCP",7,"opposite to tower builidng",100,50);
			//ParkingSlot slot = new ParkingSlot(101, 1,TWOWHEELER, block);
			Vehicle vehicle = new Vehicle(1210,"Pulsar",TWOWHEELER,RED,employee);
			//VehicleParking parking = new VehicleParking(1,slot, vehicle);
			
			System.out.println("Select your required service\n");
			System.out.println("1.Book a slot");
			System.out.println("2.Parking entry");
			System.out.println("3.Depart vehicle");
			System.out.println("4.Find my vehicle");
			System.out.println("5. Cancel a booked slot");
			System.out.println("6.Unavaliable Booked slot");
			System.out.println("7.Exit");
			
			choice = input.nextInt();
			switch(choice) {
			case ONE:
				parkingSlotBooking(employee);
				break;
			case TWO:
				vehicleParking(vehicle);
				break;
			case THREE:
				departVehicle(vehicle);
				break;
			case FOUR:
				findVehicle(vehicle);
				break;
			case FIVE:
				cancelBookedSlot(vehicle);
				break;
			case SIX:
				bookedSlotUnavailability(vehicle);
				break;
			case SEVEN:
				System.out.println("Thank you for using car parking services !!! ");
				break;
			default :
				System.out.println("Invalid choice");
			}
			display();
	}

}
