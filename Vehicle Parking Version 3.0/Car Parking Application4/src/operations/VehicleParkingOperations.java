package operations;

import java.sql.SQLException;
import java.util.InputMismatchException;

import objects.Vehicle;

public class VehicleParkingOperations extends VehicleParkingAbstract  {
	
	public void start(){
		try {
			initialize();
			display();
		} catch(InputMismatchException ex) {
			System.out.println("Invalid Input entered");
		} catch(Exception ex1){
			ex1.printStackTrace();
			System.out.println("Unknown Exception ");
		}finally {
				try {
					database.connection.close();
					System.out.println("Database connection closed \n\n");
				} catch (SQLException e) {
					e.printStackTrace();
				}
			database = null ;
			input = null ;
			start() ;
		}
	}
	public void display() {
		System.out.println("\n\nWelcome to ZOHO Multi-Level Car Parking Block\n");
		System.out.println("Login as \n   1.Admin\n   2.Employee");
		int choice ;
		choice = input.nextInt();
		switch(choice) {
				case ONE :
					adminLogin();
					break;
				case TWO:
					System.out.println("Enter your employee ID");
					int empid = input.nextInt();
					input.nextLine();
					System.out.println("Enter your vehicle Number");
					String vehicleNo =input.nextLine();
					Vehicle vehicle = database.getEmployeeVehicle(empid, vehicleNo);
					if(vehicle == null )
						display();
					else
						parkingServices(vehicle);
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
			System.out.println("4.Get slot Booking or cancelling list");
			System.out.println("5.Exit");
			int choice ;
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
				getSlotOperationRegister();
			case FIVE:
				System.out.println("Thank you for using car parking services !!! ");
				display();
			default :
				System.out.println("Invalid choice");
			}
			adminLogin();
	}
	
	public void parkingServices(Vehicle vehicle) {
			slotAvailableFloor();
			System.out.println("Select your required service\n");
			System.out.println("1.Book a slot");
			System.out.println("2.Parking entry");
			System.out.println("3.Depart vehicle");
			System.out.println("4.Find my vehicle");
			System.out.println("5. Cancel a booked slot");
			System.out.println("6.Unavaliable Booked slot");
			System.out.println("7.Exit");
			int choice ;
			choice =input.nextInt();
			switch(choice) {
			case ONE:
				parkingSlotBooking(vehicle);
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
		int empid =	input.nextInt();
		database.getEmployeeParkinglist(empid);
	}
}
