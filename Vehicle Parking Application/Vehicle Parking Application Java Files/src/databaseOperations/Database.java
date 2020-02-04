package databaseOperations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import objects.Employee;
import objects.ParkingBuilding;
import objects.ParkingSlot;
import objects.SlotOperation;
import objects.Vehicle;
import operations.SendMail;

public class Database {
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/vehicleparking";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	
	public static final String TWOWHEELER =  " Two Wheeler ";
	public static final String FOURWHEELER  = "Four Wheeler " ;
	
	public static Connection connection = null;
	public static PreparedStatement prep = null;
	public String query = null;
	Scanner input = new Scanner(System.in);

	public void getConnection() {
		try {
			Class.forName(DRIVER );
			connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	public Employee getEmployee(int empid) {
		query = "SELECT * from employee where empid = ? ";
		Employee employee = null ;
		try {
			prep = connection.prepareStatement(query);
			prep.setInt(1,empid);
			ResultSet result = prep.executeQuery();
			result.next();
			employee = new Employee(result.getString("ename")  , result.getInt("empid"), result.getLong("phoneno") , result.getString("mailid") , result.getString("teamname") , result.getString("seatno") , result.getString("blockname") ) ;
		} catch (SQLException e) {
			System.out.println("Employee not found");
			//e.printStackTrace();
		}
		return employee;
	}
	
	public int getSlotAvailableFloor(int maxSlot, String vehicleType) {
				query = "SELECT floorno,count(slotid) as slotAvailable from parkingslot group by floorno ;" ;
				try {
					prep = connection.prepareStatement(query);
					ResultSet result = prep.executeQuery();
					int slots ;
					int floorno ;
					while(result.next()) {
						slots = result.getInt("slotAvailable");
						floorno = result.getInt("floorno");   
						if(slots < maxSlot  &&  TWOWHEELER.equalsIgnoreCase(vehicleType)  )
						{  return  floorno; }
						if(slots < maxSlot  &&  FOURWHEELER.equalsIgnoreCase(vehicleType)  &&  floorno> 2 )
						{  return floorno;  }
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return 0 ;
	}
	public ParkingSlot getParkingSlot(int id, int choice) {
		if(choice == 1 )
		query = "SELECT * FROM parkingslot inner join parkingblock on parkingslot.blockid=parkingblock.blockid inner join vehicletypes on vehicletypes.vehicletypeid = parkingslot.slottypeid WHERE slotid =  ? ";
		if(choice == 2)
		query = "SELECT * FROM vehicleparking \n" + 
				"inner join parkingslot on vehicleparking.slotid = parkingslot.slotid  \n" + 
				"inner join parkingblock on parkingslot.blockid=parkingblock.blockid\n" + " inner join vehicletypes on vehicletypes.vehicletypeid = parkingslot.slottypeid " +
				" WHERE vehicleno = ? order by intime desc limit 1;\n" ;
		ParkingSlot parkingSlot = null ;
		ParkingBuilding block = null ;
		try {
			prep = connection.prepareStatement(query);
			prep.setInt(1,id);
			ResultSet result = prep.executeQuery();
			result.next();
			block = new ParkingBuilding(result.getInt("blockid") , result.getString("blockname") , result.getInt("totalfloors") , result.getString("location"), result.getInt("twowheelerslotcount"), result.getInt("fourwheelerslotcount"));
			if(result.getInt("empid") != 0 )
				parkingSlot = new ParkingSlot(result.getInt("slotid"),result.getInt("floorno"),result.getString("vehicletype"),block, getEmployee( result.getInt("empid") )); 
			else
				parkingSlot = new ParkingSlot(result.getInt("slotid"),result.getInt("floorno"),result.getString("vehicletype"),block,null);
		} catch (SQLException e) {
			System.out.println("Parking slot not available");
			//e.printStackTrace();
		}
			return parkingSlot ;
	}
	public void parkingSlotEntry(int slotid, int vehicleNo) {
			query = "INSERT into vehicleparking(slotid,vehicleno) values  ("+slotid + ", " +vehicleNo +")   " ;
			vehicleParking(query);
	}
	
	public void updateParkingSlotEntry(int parkingid) {
		query = "UPDATE vehicleparking SET outtime = now() where parkingid = "+parkingid;
		vehicleParking(query);
	}
			
	public void vehicleParking(String query) {
			try {
				prep = connection.prepareStatement(query);
				int result = prep.executeUpdate();
				if(result ==  1)
					System.out.println("Parking slot entry successful ");
			} catch (SQLException e) {
				e.printStackTrace();
			}
	}
	public void parkingSlotOperation(int empid, int slotid,int typeid, int choice) {
		query = " insert into slotoperation(empid,slotid,slotoperationtypeid) values (?,?,?);" ;
		try {
			prep = connection.prepareStatement(query);
			prep.setInt(1, empid);
			prep.setInt(2, slotid);
			prep.setInt(3,choice);
			int result = prep.executeUpdate();
			if(result ==  1)
				System.out.println("Parking slot Operation successful ");
			else
				System.out.println("Parking slot Operation unsuccessful");
			if(choice == 1)
			query = "Update parkingslot set isslotbooked =  true , empid="+empid ;
			if(choice == 2)
				query = "Update parkingslot set isslotbooked =  false , empid= null " ;
			prep = connection.prepareStatement(query);
			result = prep.executeUpdate();
			if(result ==  1)
				System.out.println("Parking slot Updated  ");
			else
				System.out.println("Parking slot not updated ");
			
			connection.commit();
		}  catch (SQLException e) {
			System.out.println("Operation failure");
			if(connection != null )
				try {
					connection.rollback();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
		} finally {
			try {
				connection.setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
	public boolean checkSlotAvailability(int slotid) {
		query = "SELECT outtime from vehicleparking where slotid= ? order by intime desc " ;
		try {
			connection.setAutoCommit(false);
			prep = connection.prepareStatement(query);
			prep.setInt(1, slotid);
			ResultSet  result = prep.executeQuery();
			result.next();
			if( null != (result.getTimestamp("outtime")) )
				return  true ;
			else
				return false;
		} catch (SQLException e) {
			System.out.println("vehicleParing is empty");
			try {
				connection.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
		return true ;
	}
	public SlotOperation getSlotBooking(Employee employee, int ONE) {
			query="SELECT * from slotoperation where empid = ? and slotoperationtypeid = ? order by dateofoperation desc";
			SlotOperation slotBooked = null ;
			try {
				prep = connection.prepareStatement(query);
				prep.setInt(1, employee.getEmpID());
				prep.setInt(2, ONE);
				ResultSet  result = prep.executeQuery();
				result.next();
				ParkingSlot slot =  getParkingSlot(result.getInt("slotid"),ONE) ;
				 slotBooked = new SlotOperation(result.getInt("slotoperationid"), employee, slot, slot.getSlotType(), result.getTimestamp("dateofoperation"));
			}catch (SQLException e) {
				System.out.println("Booked Slot not available");
			}
			return slotBooked ;
	}
	public void unavailableBookedSlot(Vehicle vehicle, SlotOperation slotBooked) {
		query="insert into unavailablebookedslot (empid,slotoperationid,slotid) values (?,?,?)";
		try {
			prep = connection.prepareStatement(query);
			prep.setInt(1, vehicle.getEmployee().getEmpID());
			prep.setInt(2, slotBooked.getOperationid());
			prep.setInt(3,slotBooked.getSlot().getParkingSlotID());
			int result = prep.executeUpdate();
			if(result ==  1)
				System.out.println("Parking slot Operation successful ");
			else
				System.out.println("Parking slot Operation unsuccessful"); 
		}  catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void getParkingSlotList(int days) {
					query="select parkingid,employee.empid,employee.ename,vehicleparking.vehicleno,vehicleparking.slotid,parkingslot.isslotbooked,parkingslot.empid,parkingslot.floorno,parkingblock.blockname,vehicleparking.intime, vehicleparking.outtime from parkingslot  \n" + 
					"		inner join vehicleparking on vehicleparking.slotid=parkingslot.slotid \n" + 
					"        inner join employeetovehicle on employeetovehicle.vehicleno = vehicleparking.vehicleno\n" + 
					"        inner join employee on employeetovehicle.empid = employee.empid\n" + 
					"        inner join parkingblock on parkingslot.blockid = parkingblock.blockid\n" + 
					"        where date(intime) >= date(now() - INTERVAL "+days+" day)"  ;
					try {
						prep = connection.prepareStatement(query);
						ResultSet  result = prep.executeQuery();
						System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
						System.out.printf("%20s	%20s		%10s		%10s		%15s		%20s		%10s		%10s		%20s		%40s\n","ParkingID","EmployeeID","EmployeeName"," VehicleNumber " , "ParkingSlot "," Slot Status " , " FloorNumber " , " Block Name " , " In Time" ,"Out Time \n");
						System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
						while (result.next()) {
								System.out.printf("%20s	%30s		%40s		%20s		%20s		%20s		%30s		%40s		%20s		%20s\n" , result.getInt("parkingid") , result.getInt("empid") , result.getString("ename") , result.getInt("vehicleno") , result.getInt("slotID") , result.getString("isslotbooked") , result.getInt("floorno") , result.getString("blockname") ,  result.getTimestamp("intime") ,  result.getTimestamp("outtime") );
							}
					}catch (SQLException e) {
						e.printStackTrace();
					}
		}
		
		public void getEmployeeParkinglist(int empid) {
			query="select parkingid,employee.empid,employee.ename,vehicleparking.vehicleno,vehicleparking.slotid,parkingslot.isslotbooked,parkingslot.empid,parkingslot.floorno,parkingblock.blockname,vehicleparking.intime, vehicleparking.outtime from parkingslot  \n" + 
					"		inner join vehicleparking on vehicleparking.slotid=parkingslot.slotid \n" + 
					"        inner join employeetovehicle on employeetovehicle.vehicleno = vehicleparking.vehicleno\n" + 
					"        inner join employee on employeetovehicle.empid = employee.empid\n" + 
					"        inner join parkingblock on parkingslot.blockid = parkingblock.blockid\n" + 
					"        where employee.empid = "+empid;
		try {
			prep = connection.prepareStatement(query);
			ResultSet  result = prep.executeQuery();
			result.next();
			System.out.println("EmployeeID :   "+result.getInt("empid")+"\nEmployeeName :   " +result.getString("ename")+"\n");
			System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
			System.out.printf("%20s		%20s		%20s		%20s		%10s		%10s		%20s		%40s\n","ParkingID"," VehicleNumber " , "ParkingSlot "," Slot Status " , " FloorNumber " , " Block Name " , " In Time" ,"Out Time \n");
			System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
			while (result.next()) {
					System.out.printf("%20s 	%30s		%30s		%30s		%30s		%310s		%20s		%20s\n" , result.getInt("parkingid")  , result.getInt("vehicleno") , result.getInt("slotID") , result.getString("isslotbooked") , result.getInt("floorno") , result.getString("blockname") ,  result.getTimestamp("intime") ,  result.getTimestamp("outtime") );
				}
		}catch (SQLException e) {
			System.out.println("No vehicles parked\n\n\n");
		}
	}
	public void sendRequest(int blockid ,int floorno) {
			query="select * from parkingslot  \n" + 
					"		inner join vehicleparking on vehicleparking.slotid=parkingslot.slotid \n" + 
					"        inner join employeetovehicle on employeetovehicle.vehicleno = vehicleparking.vehicleno\n" + 
					"        inner join employee on employeetovehicle.empid = employee.empid\n" + 
					"        inner join parkingblock on parkingslot.blockid = parkingblock.blockid\n" + 
					"        where parkingblock.blockid = ? and floorno =? and vehicleparking.outtime is null ;";
			try {
				prep = connection.prepareStatement(query);
				prep.setInt(1, blockid);
				prep.setInt(2, floorno);
				ResultSet  result = prep.executeQuery();
				String body = null ;
				while(result.next()) {
					body = "We are planning to conduct a off campus recruitment in our zoho campus vehicle parking. Kindly shift your vehicle parked in slot number "+result.getInt("slotid")+" in "+result.getInt("floorno")+" of "+result.getString("blockname")+" to other parking slots ." ;
					SendMail.mail(result.getString("mailid"), "Vehicle shift request", body);
				}
				System.out.println("Request Mail sent");
			}catch (SQLException e) {
				System.out.println("Sorry for the inconvineance !!! Try again ... \n\n\n");
			}
	}
}
