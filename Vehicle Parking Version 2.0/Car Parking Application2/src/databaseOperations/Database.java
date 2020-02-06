package databaseOperations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import objects.Employee;
import objects.FloorObject;
import objects.ParkingBuilding;
import objects.ParkingSlot;
import objects.SlotOperation;
import objects.Vehicle;
import objects.VehicleParking;
import operations.SendMail;

public class Database {
	private static final String DRIVER = "com.mysql.cj.jdbc.Driver";
	private static final String URL = "jdbc:mysql://localhost:3306/vehicleparking_v2";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	
	public static final String TWOWHEELER =  " Two Wheeler ";
	public static final String FOURWHEELER  = "Four Wheeler " ;
	
	public  Connection connection = null;
	public  PreparedStatement prep = null;
	public String query = null;

	public void getConnection() {
		try {
			Class.forName(DRIVER );
			this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("Database Connection established");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	public Vehicle getEmployeeVehicle(int empid) {
		query = "select * from employee		" + 
						"		inner join employeetovehicle on employeetovehicle.empid = employee.empid		" + 
						"        inner join vehicle on vehicle.vehicleid = employeetovehicle.vehicleid		" + 
						"        inner join vehicletypes on vehicle.vehicletypeid = vehicletypes.vehicletypeid		" + 
						"        where employee.empid = ? " ;
		Employee employee = null ; 
		Vehicle vehicle =  null ;
		try {
			prep = connection.prepareStatement(query);
			prep.setInt(1,empid);
			ResultSet result = prep.executeQuery();
			result.next();
			employee = new Employee(result.getString("ename")  , result.getInt("empid"), result.getLong("phoneno") , result.getString("mailid") , result.getString("teamname") , result.getString("seatno") , result.getString("blockname") ) ;
			vehicle = new Vehicle(result.getInt("vehicleid"),result.getString("vehicleno"), result.getString("vehiclename") , result.getString("vehicletype"), result.getString("vehiclecolour"), employee);			
		} catch (SQLException e) {
			System.out.println("Employee not found");
		}
		return vehicle;
	}

//		public Vehicle getVehicle(String vehicleNo) {
//			query = "select * from vehicle " + 
//							"		inner join employeetovehicle on employeetovehicle.vehicleid = vehicle.vehicleid " + 
//							"      inner join employee on employee.empid = employeetovehicle.empid " + 
//							"        inner join vehicletypes on vehicletypes.vehicletypeid = vehicle.vehicletypeid" + 
//							"      where vehicle.vehicleno = ? ";
//			Vehicle vehicle = null ;
//			try {
//				prep = connection.prepareStatement(query);
//				prep.setString(1, vehicleNo);
//				ResultSet  result = prep.executeQuery();
//				result.next();
//				Employee employee = new Employee(result.getString("ename")  , result.getInt("empid"), result.getLong("phoneno") , result.getString("mailid") , result.getString("teamname") , result.getString("seatno") , result.getString("blockname") ) ;
//				vehicle = new Vehicle(result.getInt("vehicleid"),vehicleNo, result.getString("vehiclename") , result.getString("vehicletype"), result.getString("vehiclecolour"), employee);			
//			}catch (SQLException e) {
//				System.out.println("Vehicle not found ");
//				e.printStackTrace();
//			}
//			return vehicle;
//		}
	
		public ParkingSlot getParkingSlot(int slotid) {
				query = "select * from parkingslot" + 
						"		inner join floors on floors.floorid = parkingslot.floorid" + 
						"		inner join parkingblock on floors.blockid = parkingblock.blockid " + 
						"		inner join vehicletypes on vehicletypes.vehicletypeid = floors.floortypeid" + 
						"        where slotid = ?  ";	
			ParkingSlot parkingSlot = null ;
			ParkingBuilding block = null ;
			FloorObject floor= null ;
			try {
				prep = connection.prepareStatement(query);
				prep.setInt(1,slotid);
				ResultSet result = prep.executeQuery();
				if(	result.next() == false )
					{		return null ;		}
				block = new ParkingBuilding(result.getInt("blockid") , result.getString("blockname") , result.getInt("totalfloors") , result.getString("location"), result.getInt("twowheelerslotcount"), result.getInt("fourwheelerslotcount"));
				floor = new FloorObject(result.getInt("floorid") , result.getString("floorname") , block ,result.getString("vehicletype"),result.getInt("slotcount"));
				if(result.getInt("empid") != 0 )
					parkingSlot = new ParkingSlot(result.getInt("slotid"),floor, getEmployeeVehicle( result.getInt("empid")).getEmployee()  ); 
				else
					parkingSlot = new ParkingSlot(result.getInt("slotid"),floor,null);
			} catch (SQLException e) {
				System.out.println("Parking slot not available");
			}
				return parkingSlot ;
		}
	
			public int getSlotAvailableFloor(int floortypeid) {
						query = "select floors.floorname,vehicletypes.vehicletype as floortype,count(vehicleparking.slotid) as totalslotsfilled , (floors.slotcount-count(vehicleparking.slotid)) as remainingslots from vehicleparking  " + 
										"			right join parkingslot on parkingslot.slotid = vehicleparking.slotid  " + 
										"			right join floors on floors.floorid = parkingslot.floorid  " + 
										"			inner join parkingblock on parkingblock.blockid = floors.blockid  " + 
										"            inner join vehicletypes on vehicletypes.vehicletypeid = floors.floortypeid  " + 
										"            where vehicleparking.outtime is null and vehicletypes.vehicletypeid =  ? group by floors.floorid limit 1" ;
						try {
							prep = connection.prepareStatement(query);
							prep.setInt(1,floortypeid);
							ResultSet result = prep.executeQuery();
							if(  result.next()	==	false  )
							{		System.out.println("Parking slots not available");	}
							else {
								System.out.println(result.getString("floorname")+"       -----     " +result.getInt("remainingslots")+" slots available");
							}
						} catch (SQLException e) {
							e.printStackTrace();
						}
						return 0 ;
			}	
	public int getVehicleSlotID(String vehicleno) {
		query = "select slotid from vehicleparking " + 
					"		inner join vehicle on vehicle.vehicleid = vehicleparking.vehicleid\n" + 
					"        where vehicleno = ?  and outtime is null order by intime desc;";
	try {
		prep = connection.prepareStatement(query);
		prep.setString(1,vehicleno);
		ResultSet result = prep.executeQuery();
		result.next();
		return result.getInt("slotid");
	} catch (SQLException e) {
		System.out.println("Parking slot not available for your vehicle");
		e.printStackTrace();
	}
		return 0 ;
}
	
	public void parkingSlotEntry(int slotid, int vehicleid) {
			query = "INSERT into vehicleparking(slotid,vehicleid) values  ("+slotid + ",' " +vehicleid +" ')   " ;
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
		int result ;
		if(choice == 1)
			query = "Update parkingslot set empid="+empid+" where slotid = "+slotid ;
		if(choice == 2)
				query = "Update parkingslot set empid= null  where slotid = "+slotid ;
		try {
			prep = connection.prepareStatement(query);
			result = prep.executeUpdate(query);
			if(result ==  1)
				System.out.println("Parking slot Updated  ");
			else
				System.out.println("Parking slot not updated ");			
			
			query = " insert into slotoperation(empid,slotid,slotoperationtypeid) values (?,?,?);" ;
			prep = connection.prepareStatement(query);
			prep.setInt(1, empid);
			prep.setInt(2, slotid);
			prep.setInt(3,typeid);
			result = prep.executeUpdate();
			if(result ==  1)
				System.out.println("Parking slot Operation successful ");
			else
				System.out.println("Parking slot Operation unsuccessful");
		}  catch (SQLException e) {
			System.out.println("Parking slot already booked or slot not available");
		} 
	}
	
	public boolean checkSlotAvailability(int slotid) {
		query = "SELECT outtime from vehicleparking where slotid= ? order by intime desc limit 1" ;
		try {
			prep = connection.prepareStatement(query);
			prep.setInt(1, slotid);
			ResultSet  result = prep.executeQuery();
			if (result.next() == false) {
				return true;
			}
			if( null != (result.getTimestamp("outtime")) )
				return  true ;
			else
				return false;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println("Error whicle checking slot availabilty");
		}
		return true ;
	}
	public SlotOperation getSlotOperation(Employee employee, int ONE) {
			query="SELECT * from slotoperation inner join slotservicetypes on slotservicetypes.servicetypeid = slotoperation.slotoperationtypeid where empid = ? and slotoperationtypeid = ? order by dateofoperation desc";
			SlotOperation slotBooked = null ;
			try {
				prep = connection.prepareStatement(query);
				prep.setInt(1, employee.getEmpID());
				prep.setInt(2, ONE);
				ResultSet  result = prep.executeQuery();
				result.next();
				ParkingSlot slot =  getParkingSlot(result.getInt("slotid")) ;
				 slotBooked = new SlotOperation(result.getInt("slotoperationid"), employee, slot, result.getString("servicetype"), result.getTimestamp("dateofoperation"));
			}catch (SQLException e) {
				System.out.println("Booked Slot not available");
			}
			return slotBooked ;
	}
	public void unavailableBookedSlot(Employee employee, ParkingSlot slot) {
		query="insert into unavailablebookedslot (empid,slotid) values (?,?)";
		try {
			prep = connection.prepareStatement(query);
			prep.setInt(1, employee.getEmpID());
			prep.setInt(2,slot.getParkingSlotID());
			int result = prep.executeUpdate();
			if(result ==  1)
				System.out.println("Unavailable booked slot entry successful ");
			else
				System.out.println("Unavailable booked slot entry unsuccessful"); 
			
			query="select * from unavailablebookedslot	" + 
					"		inner join vehicleparking on vehicleparking.slotid = unavailablebookedslot.slotid	" + 
					"        inner join vehicle on vehicle.vehicleid = vehicleparking.vehicleid		" + 
					"		inner join employeetovehicle on vehicle.vehicleid = employeetovehicle.vehicleid	" + 
					"        inner join employee on employeetovehicle.empid = employee.empid	" + 
					"        where unavailablebookedslot.slotid = ? and vehicleparking.outtime is null ";
			prep = connection.prepareStatement(query);
			prep.setInt(1,slot.getParkingSlotID());
			ResultSet resultset = prep.executeQuery();
			if( resultset.next() == false )
				System.out.println("	Cannot find unavailable booked slot entry ");
			else
				{
				String body = "Your vehicle is parked in a booked slot alotted for another employee. Please depart your vehicle as soon as possible and park in some other unbooked free slot";
				SendMail.mail(resultset.getString("mailid"), "Vehicle parking in Booked slot", body);
				}
		}  catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public void getParkingSlotList(int days) {
					query="select parkingid,employee.empid,employee.ename,vehicle.vehicleno,vehicleparking.slotid,parkingslot.empid,floors.floorname,parkingblock.blockname,vehicleparking.intime, vehicleparking.outtime from vehicleparking \n" + 
							"					inner join parkingslot on vehicleparking.slotid=parkingslot.slotid\n" + 
							"                    inner join floors on parkingslot.floorid = floors.floorid \n" + 
							"                    inner join parkingblock on floors.blockid = parkingblock.blockid \n" + 
							"					inner join employeetovehicle on employeetovehicle.vehicleid = vehicleparking.vehicleid\n" + 
							"					inner join employee on employeetovehicle.empid = employee.empid \n" + 
							"                    inner join vehicle on vehicle.vehicleid = employeetovehicle.vehicleid\n" + 
							"					where date(intime) >= date(now() - INTERVAL "+days+"  day) ;" ;
					try {
						prep = connection.prepareStatement(query);
						ResultSet  result = prep.executeQuery();
						System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
						System.out.printf("%20s	%20s		%10s		%10s		%15s		%10s		%10s		%20s		%40s\n","ParkingID","EmployeeID","EmployeeName"," VehicleNumber " , "ParkingSlot ", " FloorName " , " Block Name " , " In Time" ,"Out Time \n");
						System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
						while (result.next()) {
								System.out.printf("%20s	%30s		%40s		%20s		%20s		%30s		%40s		%20s		%20s\n" , result.getInt("parkingid") , result.getInt("empid") , result.getString("ename") , result.getString("vehicleno") , result.getInt("slotID") , result.getString("floorname") , result.getString("blockname") ,  result.getTimestamp("intime") ,  result.getTimestamp("outtime") );
							}
					}catch (SQLException e) {
						e.printStackTrace();
					}
		}
		
		public void getEmployeeParkinglist(int empid) {
			query="select * from vehicleparking " + 
					"		inner join parkingslot on vehicleparking.slotid=parkingslot.slotid\n" + 
					"                    inner join floors on parkingslot.floorid = floors.floorid \n" + 
					"                    inner join parkingblock on floors.blockid = parkingblock.blockid \n" + 
					"					inner join employeetovehicle on employeetovehicle.vehicleid = vehicleparking.vehicleid\n" + 
					"					inner join employee on employeetovehicle.empid = employee.empid \n" + 
					"                    inner join vehicle on vehicle.vehicleid = employeetovehicle.vehicleid\n" + 
					"                    where employee.empid = ?";
		try {
			prep = connection.prepareStatement(query);
			prep.setInt(1,empid);
			ResultSet  result = prep.executeQuery();
			//System.out.println("EmployeeID :   "+result.getInt("empid")+"\nEmployeeName :   " +result.getString("ename")+"\n");
			System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
			System.out.printf("%20s		%20s		%20s		%20s		%15s		%20s		%20s	\n","ParkingID"," VehicleNumber " , "ParkingSlot ", " FloorNumber " , " Block Name " , " In Time" ,"Out Time \n");
			System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
			while (result.next()) {
					System.out.printf("%20s 	%30s		%30s		%30s		%30s		%20s		%30s	\n" , result.getInt("parkingid")  , result.getString("vehicleno") , result.getInt("slotID") , result.getString("floorname") , result.getString("blockname") ,  result.getTimestamp("intime") ,  result.getTimestamp("outtime") );
				}
		}catch (SQLException e) {
			System.out.println("No vehicles parked\n\n\n");
			e.printStackTrace();
		}
	}
	public void sendRequest(int blockid ,int floorno) {
			query="select *  from vehicleparking " + 
					"		inner join parkingslot on vehicleparking.slotid=parkingslot.slotid " + 
					"		inner join floors on parkingslot.floorid = floors.floorid " + 
					"		inner join parkingblock on floors.blockid = parkingblock.blockid	" + 
					"		inner join employeetovehicle on employeetovehicle.vehicleid = vehicleparking.vehicleid	" + 
					"		inner join employee on employeetovehicle.empid = employee.empid 	" + 
					"		inner join vehicle on vehicle.vehicleid = employeetovehicle.vehicleid	" + 
					"      where parkingblock.blockid = ? and floors.floorid = ? and vehicleparking.outtime is null ;";
			try {
				prep = connection.prepareStatement(query);
				prep.setInt(1, blockid );
				prep.setInt(2, floorno );
				ResultSet  result = prep.executeQuery();
				String body = null ;
				System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
				System.out.printf("%20s	%20s		%10s		%10s		%15s		%20s		%10s		%20s		%40s\n","ParkingID","EmployeeID","EmployeeName"," VehicleNumber " , "ParkingSlot " , " FloorName " , " Block Name " , " In Time" ,"Out Time \n");
				System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
				while(result.next()) {
					System.out.printf("%20s	%30s		%40s		%20s		%20s		%20s		%40s		%20s		%20s\n" , result.getInt("parkingid") , result.getInt("employee.empid") , result.getString("ename") , result.getString("vehicleno") , result.getInt("slotID") , result.getString("floorname") , result.getString("blockname") , result.getTimestamp("intime") ,  result.getTimestamp("outtime"));
					body = "We are planning to conduct a off campus recruitment in our zoho campus vehicle parking. Kindly shift your vehicle parked in slot number "+result.getInt("slotid")+" in "+result.getString("floorname")+" of "+result.getString("blockname")+" to other parking slots ." ;
					SendMail.mail(result.getString("employee.mailid"), "Vehicle shift request", body);
				}
				System.out.println("Request Mail sent");
			}catch (SQLException e) {
				e.printStackTrace();
				System.out.println("Sorry for the inconvineance !!! Try again ... \n\n\n");
			}
	}
	public boolean isNotVehicleParked(int vehicleid) {
		query = "select * from vehicleparking\n" + 
				"		inner join vehicle on vehicle.vehicleid = vehicleparking.vehicleid\n" + 
				"        where vehicle.vehicleid = ? and vehicleparking.outtime is null order by vehicleparking.intime desc limit 1" ;
		try {
			prep = connection.prepareStatement(query);
			prep.setInt(1, vehicleid);
			ResultSet  result = prep.executeQuery();
			if(	result.next() == false )
					return true ;
			if(result.getTimestamp("outtime") != null)
				return true ;
			else
				return false ;
						
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	public VehicleParking getVehicleParking(ParkingSlot slot , Vehicle vehicle) {
		query = "     select * from vehicleparking where outtime is null and  slotid =?   and vehicleid =  ? ";
		VehicleParking parking = null ;
		try {
			prep = connection.prepareStatement(query);
			prep.setInt(1, slot.getParkingSlotID());
			prep.setInt(2, vehicle.getVehicleID());
			ResultSet  result = prep.executeQuery();
			if(	result.next() == false )
			{	System.out.println("No parking entry found");		}
			else	{	parking = new VehicleParking(result.getInt("parkingid") ,slot,vehicle );	}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return parking;
	}
	public ParkingSlot getBookingSlot(Vehicle vehicle,int choice) {
		if(choice ==1 ) {
			query="select * from parkingslot		" + 
				"		inner join floors on parkingslot.floorid = floors.floorid 		" + 
				"		inner join parkingblock on floors.blockid = parkingblock.blockid 		" + 
				"		left join vehicleparking on parkingslot.slotid = vehicleparking.slotid		" + 
				"        inner join vehicletypes on vehicletypes.vehicletypeid = floors.floortypeid		" + 
				"        where (vehicleparking.outtime is null or (vehicleparking.outtime is not null and vehicleparking.outtime is not null )) and parkingslot.empid is null and vehicletypes.vehicletype = '"+vehicle.getVehicleType()+"' order by parkingslot.slotid limit 1 ";
		}	
		if(choice ==2 ) {
		query="select * from parkingslot\n" + 
				"		inner join floors on parkingslot.floorid = floors.floorid \n" + 
				"		inner join parkingblock on floors.blockid = parkingblock.blockid \n" + 
				"        inner join slotoperation on slotoperation.slotid = parkingslot.slotid\n" + 
				"        inner join employee on employee.empid = parkingslot.empid\n" + 
				"        inner join vehicletypes on vehicletypes.vehicletypeid = floors.floortypeid		" + 
				"        where employee.empid = "+ vehicle.getEmployee().getEmpID() +" order by dateofoperation desc ;";
		}
		
		ParkingSlot parkingSlot = null ;
		ParkingBuilding block = null ;
		FloorObject floor= null ;
		try {
			prep = connection.prepareStatement(query);
			ResultSet result = prep.executeQuery();
			if(	result.next() == false )
				{		return null ;		}
			block = new ParkingBuilding(result.getInt("blockid") , result.getString("blockname") , result.getInt("totalfloors") , result.getString("location"), result.getInt("twowheelerslotcount"), result.getInt("fourwheelerslotcount"));
			floor = new FloorObject(result.getInt("floorid") , result.getString("floorname") , block ,result.getString("vehicletype"),result.getInt("slotcount"));
				parkingSlot = new ParkingSlot(result.getInt("slotid"),floor, vehicle.getEmployee()  ); 
		} catch (SQLException e) {
			System.out.println("Parking slot not available");
			e.printStackTrace();
		}
			return parkingSlot ;
	}
	public void getSlotOperationList() {
		query="select * from slotoperation \n" + 
						"		inner join slotservicetypes on slotservicetypes.servicetypeid = slotoperation.slotoperationtypeid\n" + 
						"        inner join parkingslot on slotoperation.slotid=parkingslot.slotid\n" + 
						"		inner join floors on parkingslot.floorid = floors.floorid \n" + 
						"        inner join parkingblock on floors.blockid = parkingblock.blockid \n" + 
						"        inner join employee on parkingslot.empid = employee.empid \n" + 
						"		inner join employeetovehicle on employeetovehicle.empid = employee.empid \n" + 
						"		inner join vehicle on vehicle.vehicleid = employeetovehicle.vehicleid order by dateofoperation;";
		try {
		prep = connection.prepareStatement(query);
		ResultSet  result = prep.executeQuery();
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		System.out.printf("%20s		%20s		%20s		%20s		%20s		%20s		%15s		%20s		%20s	\n","SlotOperationID","ServiceType"," VehicleNumber " ,"EmployeeID","EmployeeName", "ParkingSlot ", " FloorName " , " Block Name " , " Date of Operation");
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		while (result.next()) {
				System.out.printf("%20s 	%30s		%30s		%30s		%30s		%30s		%30s		%20s		%30s	\n" , result.getInt("slotoperationid")  ,result.getString("servicetype"),result.getInt("employee.empid") , result.getString("ename") , result.getString("vehicleno") , result.getInt("slotID") , result.getString("floorname") , result.getString("blockname") ,  result.getTimestamp("dateofoperation")  );
			}
	}catch (SQLException e) {
		System.out.println("No vehicles parked\n\n\n");
		e.printStackTrace();
	}
	}
	
//	public ParkingSlot getCancellingSlot(Vehicle vehicle) {
//		query="select * from parkingslot\n" + 
//				"		inner join floors on parkingslot.floorid = floors.floorid \n" + 
//				"		inner join parkingblock on floors.blockid = parkingblock.blockid \n" + 
//				"        inner join slotoperation on slotoperation.slotid = parkingslot.slotid\n" + 
//				"        inner join employee on employee.empid = parkingslot.empid\n" + 
//				"        inner join vehicletypes on vehicletypes.vehicletypeid = floors.floortypeid		" + 
//				"        where employee.empid = "+ vehicle.getEmployee().getEmpID() +" order by dateofoperation desc ;";
//		ParkingSlot parkingSlot = null ;
//		ParkingBuilding block = null ;
//		FloorObject floor= null ;
//		try {
//			prep = connection.prepareStatement(query);
//			//prep.setInt(1, vehicle.getEmployee().getEmpID() );
//			ResultSet result = prep.executeQuery();
//			if(	result.next() == false )
//				{		
//						System.out.println("No slot booking found for employee");
//						return null ;
//				}
//			block = new ParkingBuilding(result.getInt("blockid") , result.getString("blockname") , result.getInt("totalfloors") , result.getString("location"), result.getInt("twowheelerslotcount"), result.getInt("fourwheelerslotcount"));
//			floor = new FloorObject(result.getInt("floorid") , result.getString("floorname") , block ,result.getString("vehicletype"),result.getInt("slotcount"));
//				parkingSlot = new ParkingSlot(result.getInt("slotid"),floor, vehicle.getEmployee()  ); 
//		} catch (SQLException e) {
//			System.out.println("Parking slot not available");
//			e.printStackTrace();
//		}
//			return parkingSlot ;
//	}
}
