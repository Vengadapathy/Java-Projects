package databaseOperations;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
	private static final String URL = "jdbc:mysql://localhost:3306/vehicleparking_v6";
	private static final String USERNAME = "root";
	private static final String PASSWORD = "";
	
	public static final String TWOWHEELER =  " Two Wheeler ";
	public static final String FOURWHEELER  = "Four Wheeler " ;
	
	public  Connection connection = null;
	public  PreparedStatement prep = null;

	public void getConnection() {	
		try {
			Class.forName(DRIVER );
			this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
			System.out.println("Database Connection established");
		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Vehicle getEmployeeVehicle(int empid, String vehicleNo) {
		String query = "select * from employee		" + 
						"		inner join employeetovehicle on employeetovehicle.empid = employee.empid		" + 
						"        inner join vehicle on vehicle.vehicleid = employeetovehicle.vehicleid		" + 
						"        inner join vehicletypes on vehicle.vehicletypeid = vehicletypes.vehicletypeid		" + 
						"        where employee.empid = ? and vehicleno = ? " ;
		Employee employee = null ; 
		Vehicle vehicle =  null ;
		try {
			prep = connection.prepareStatement(query);
			prep.setInt(1,empid);
			prep.setString(2,vehicleNo);
			ResultSet result = prep.executeQuery();
			if(	result.next()	== false 	)
			{	System.out.println("No employee record found");	}
			else {
			employee = new Employee(result.getString("ename")  , result.getInt("empid"), result.getLong("phoneno") , result.getString("mailid") , result.getString("teamname") , result.getString("seatno") , result.getString("blockname") ) ;
			vehicle = new Vehicle(result.getInt("vehicleid"),result.getString("vehicleno"), result.getString("vehiclename") , result.getString("vehicletype"), result.getString("vehiclecolour"), employee);			
			}
		} catch (SQLException e) {
			System.out.println("Employee not found");
			e.printStackTrace();
		}
		return vehicle;
	}
	
//		public ParkingSlot getParkingSlot(int slotid) {
//			String	query = "select * from parkingslot\n" + 
//						"		inner join floors on floors.floorid = parkingslot.floorid\n" + 
//						"		inner join parkingblock on floors.blockid = parkingblock.blockid\n" + 
//						"		inner join vehicletypes on vehicletypes.vehicletypeid = floors.floortypeid\n" + 
//						"        left join vehicle on vehicle.vehicleid = parkingslot.vehicleid\n" + 
//						"        left join employeetovehicle on employeetovehicle.vehicleid = vehicle.vehicleid\n" + 
//						"        left join employee on employee.empid = employeetovehicle.empid\n" + 
//						"        where slotid = ?	";	
//			ParkingSlot parkingSlot = null ;
//			ParkingBuilding block = null ;
//			FloorObject floor= null ;
//			try {
//				prep = connection.prepareStatement(query);
//				prep.setInt(1,slotid);
//				ResultSet result = prep.executeQuery();
//				if(	result.next() == false )
//					{		return null ;		}
//				else	{
//				block = new ParkingBuilding(result.getInt("blockid") , result.getString("blockname") , result.getInt("totalfloors") , result.getString("location"), result.getInt("twowheelerslotcount"), result.getInt("fourwheelerslotcount"));
//				floor = new FloorObject(result.getInt("floorid") , result.getString("floorname") , block ,result.getString("vehicletype"),result.getInt("slotcount"));
//				Employee employee =new Employee(result.getString("ename")  , result.getInt("empid"), result.getLong("phoneno") , result.getString("mailid") , result.getString("teamname") , result.getString("seatno") , result.getString("blockname") ) ;
//				Vehicle vehicle = new Vehicle(result.getInt("vehicleid"),result.getString("vehicleno"), result.getString("vehiclename") , result.getString("vehicletype"), result.getString("vehiclecolour"), employee);			
//				if(result.getInt("vehicleid") != 0 )	{	
//					parkingSlot = new ParkingSlot(result.getInt("slotid"),floor, vehicle  ); 
//				}	else
//					{	parkingSlot = new ParkingSlot(result.getInt("slotid"),floor,null);	}
//				}
//			} catch (SQLException e) {
//				System.out.println("Parking slot not available");
//				e.printStackTrace();
//			}
//				return parkingSlot ;
//		}
	
			public boolean getSlotAvailableFloor(int floortypeid) {
				String	query = "select floors.floorid, floors.floorname,vehicletypes.vehicletype ,count(slotid) as remainingslots from parkingslot\n" + 
						"		right join floors on floors.floorid = parkingslot.floorid\n" + 
						"        inner join vehicletypes on vehicletypes.vehicletypeid = floors.floortypeid\n" + 
						"where parkingslot.slotid NOT IN (select slotid from vehicleparking where outtime is null) AND parkingslot.slotid NOT IN (select slotid from slotoperation where slotcancellingtime is null) and vehicletypeid = ?  group by floors.floorid ";
						try {
								prep = connection.prepareStatement(query);
								prep.setInt(1,floortypeid);
								ResultSet result = prep.executeQuery();
								if(  result.next()	==	false  )
								{		System.out.println("Parking slots not available ");
										return false ;
								}	else {
									System.out.println(result.getString("vehicletype") +" slots available in "+result.getString("floorname")+"       -----     " +result.getInt("remainingslots")+" slots available");
									return true ;
								}
							} catch (SQLException e) {
								e.printStackTrace();
							}
							return true ;
			}	
	public int getVehicleSlotID(String vehicleno) {
	String	query = "select slotid from vehicleparking " + 
					"		inner join vehicle on vehicle.vehicleid = vehicleparking.vehicleid\n" + 
					"        where vehicleno = ?  and outtime is null order by intime desc;";
	try {
		prep = connection.prepareStatement(query);
		prep.setString(1,vehicleno);
		ResultSet result = prep.executeQuery();
		if(	result.next()	== false ) {
			System.out.println("Vehicle parking slot not found !!! Check whether your vehicle is parked here or not");
		} else {
		return result.getInt("slotid");	}
	} catch (SQLException e) {
		e.printStackTrace();
	}
		return 0 ;
}
	
	public VehicleParking parkingSlotEntry(ParkingSlot slot, Vehicle vehicle) {
		VehicleParking parking = null ;
		String	query = "INSERT into vehicleparking(slotid,vehicleid,empid) values  ("+slot.getParkingSlotID() + ",' " +vehicle.getVehicleID() +" ' , ' " +vehicle.getEmployee().getEmpID() +" ' )   " ;
		if(	vehicleParking(query)	) {
			parking = new VehicleParking( slot, new Date() , vehicle , vehicle.getEmployee() );
			System.out.println("Your vehicle is parked in slot "+slot.getParkingSlotID()+" in "+slot.getFloor().getFloorName()+" of "+slot.getFloor().getParkingBuilding().getBlockName());
		}	else 	{
			System.out.println("Vehicle parking failed");
		}
		return parking ;
	}
	
	public void updateParkingSlotEntry(VehicleParking parking) {
		String query = "    update vehicleparking set outtime = now() where vehicleid = "+parking.getVehicle().getVehicleID()+"  and slotid = "+parking.getParkingSlot().getParkingSlotID()+"  and outtime is  null " ;
		if(	vehicleParking(query)	) {
			System.out.println("Vehicle departure successful");
		}else {
			System.out.println("Vehicle departure unsuccessful");
		}
	}
			
	public boolean vehicleParking(String query) {
			try {
				prep = connection.prepareStatement(query);
				int result = prep.executeUpdate();
				if(result ==  1) {
					return true;	}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return false ;
	}
	
	
	
	
//	public boolean parkingSlotOperation(int vehicleid, int slotid,int typeid, int choice) {
//		int result ;
//		String query = null ;
//		if(choice == 1)
//			query = "Update parkingslot set vehicleid="+vehicleid+" where slotid = "+slotid ;
//		if(choice == 2)
//				query = "Update parkingslot set vehicleid= null  where slotid = "+slotid ;
//		try {
//			connection.setAutoCommit(false);
//			prep = connection.prepareStatement(query);
//			result = prep.executeUpdate(query);
//			if(result ==  1)
//				System.out.println("Parking slot Updated  ");
//			else
//				System.out.println("Parking slot not updated ");			
//			
//			query = " 	update slotoperation set slotcancellingtime = now() where slotoperationid = "+ ;
//			prep = connection.prepareStatement(query);
//			prep.setInt(1, vehicleid);
//			prep.setInt(2, slotid);
//			prep.setInt(3,typeid);
//			result = prep.executeUpdate();
//			if(result ==  1)
//				System.out.println("Parking slot Operation successful ");
//			else
//				System.out.println("Parking slot Operation unsuccessful");
//			connection.commit();
//		}  catch (SQLException e) {
//			System.out.println("Parking slot already booked or slot not available");
//			e.printStackTrace();
//					try {
//						connection.rollback();
//					} catch (SQLException e1) {
//						e1.printStackTrace();
//					}
//			return false ;
//		} finally {
//					try {
//						connection.setAutoCommit(true);
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//		}
//		return true ;
//	}
	
	public boolean checkSlotAvailability(int slotid) {
		String query = "SELECT outtime from vehicleparking where slotid= ? order by intime desc limit 1" ;
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

	public void unavailableBookedSlot(Employee employee, ParkingSlot slot) {
		String query="insert into unavailablebookedslot (empid,slotid) values (?,?)";
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
		String	query="select parkingid,vehicleparking.empid,employee.ename,vehicle.vehicleno,vehicleparking.slotid,vehicleparking.vehicleid,floors.floorname,parkingblock.blockname,vehicleparking.intime, vehicleparking.outtime from vehicleparking \n" + 
				"					inner join parkingslot on vehicleparking.slotid=parkingslot.slotid\n" + 
				"                    inner join floors on parkingslot.floorid = floors.floorid \n" + 
				"                    inner join parkingblock on floors.blockid = parkingblock.blockid \n" + 
				"					inner join employee on vehicleparking.empid = employee.empid \n" + 
				"                    inner join vehicle on vehicle.vehicleid = vehicleparking.vehicleid\n" + 
				"					where date(intime) >= date(now() - INTERVAL  "+days+"  day) order by vehicleparking.parkingid desc;\n" + 
				"" ;
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
			String query="select * from vehicleparking " + 
					"		inner join parkingslot on vehicleparking.slotid=parkingslot.slotid\n" + 
					"                    inner join floors on parkingslot.floorid = floors.floorid \n" + 
					"                    inner join parkingblock on floors.blockid = parkingblock.blockid \n" + 
					"					inner join employeetovehicle on employeetovehicle.vehicleid = vehicleparking.vehicleid\n" + 
					"					inner join employee on employeetovehicle.empid = employee.empid \n" + 
					"                    inner join vehicle on vehicle.vehicleid = employeetovehicle.vehicleid\n" + 
					"                    where employee.empid = ? order by vehicleparking.parkingid desc; ";
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
		String	query="select *  from vehicleparking " + 
					"		inner join parkingslot on vehicleparking.slotid=parkingslot.slotid " + 
					"		inner join floors on parkingslot.floorid = floors.floorid " + 
					"		inner join parkingblock on floors.blockid = parkingblock.blockid	" + 
					"		inner join employeetovehicle on employeetovehicle.vehicleid = vehicleparking.vehicleid	" + 
					"		inner join employee on employeetovehicle.empid = employee.empid 	" + 
					"		inner join vehicle on vehicle.vehicleid = employeetovehicle.vehicleid	" + 
					"      where parkingblock.blockid = ? and floors.floorid = ? and vehicleparking.outtime is null order by vehicleparking.parkingid desc;";
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
		String	query = "select * from vehicleparking\n" + 
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
//	public VehicleParking getVehicleParking(ParkingSlot slot , Vehicle vehicle) {
//		String	query = "     select * from vehicleparking where outtime is null and  slotid =?   and vehicleid =  ? ";
//		VehicleParking parking = null ;
//		try {
//			prep = connection.prepareStatement(query);
//			prep.setInt(1, slot.getParkingSlotID());
//			prep.setInt(2, vehicle.getVehicleID());
//			ResultSet  result = prep.executeQuery();
//			if(	result.next() == false )
//			{	System.out.println("No parking entry found");		}
//			else	{	parking = new VehicleParking(slot,result.getTimestamp("intime"), vehicle, vehicle.getEmployee() );	}
//		}catch (SQLException e) {
//			System.out.println("vehicle depart failed");
//			//e.printStackTrace();
//		}
//		return parking;
//	}
	public ParkingSlot getFreeSlot(Vehicle vehicle,int choice) {
		String query = null ;
		if(choice ==1 ) {
			query="			select *  from parkingslot\n" + 
					"		inner join floors on parkingslot.floorid = floors.floorid \n" + 
					"		inner join parkingblock on floors.blockid = parkingblock.blockid \n" + 
					"        left join vehicle on vehicle.vehicleid = parkingslot.vehicleid\n" + 
					"		left join employeetovehicle on employeetovehicle.vehicleid = vehicle.vehicleid\n" + 
					"		left join employee on employeetovehicle.empid = employee.empid\n" + 
					"        left join vehicletypes on vehicletypes.vehicletypeid = floors.floortypeid\n" + 
					"		left join (select * from vehicleparking inner join ( select max(parkingid) as id from vehicleparking group by slotid ) as table1 on table1.id = vehicleparking.parkingid ) as parking on parking.slotid = parkingslot.slotid\n" + 
					"        where (parking.outtime is not null or (parking.intime is null and parking.outtime is null) or parking.vehicleid = "+vehicle.getVehicleID()+") and parkingslot.vehicleid is null and  vehicletypes.vehicletype = 'TWO WHEELER'   	";	}
		if(choice ==2 ) {
			 query="select * from parkingslot\n" + 
			 		"		inner join floors on parkingslot.floorid = floors.floorid \n" + 
			 		"		inner join parkingblock on floors.blockid = parkingblock.blockid \n" + 
			 		"        inner join slotoperation on slotoperation.slotid = parkingslot.slotid\n" + 
			 		"        inner join vehicletypes on vehicletypes.vehicletypeid = floors.floortypeid\n" + 
			 		"        left join vehicle on vehicle.vehicleid = slotoperation.vehicleid\n" + 
			 		"		left join employee on slotoperation.empid = employee.empid\n" + 
			 		"        where vehicle.vehicleno = '"+vehicle.getVehicleNo()+"' and slotcancellingtime is null order by slotoperationid desc " ;
		}
		
		ParkingSlot parkingSlot = null ;
		ParkingBuilding block = null ;
		FloorObject floor= null ;
		try {
			prep = connection.prepareStatement(query);
			ResultSet result = prep.executeQuery();
			if(	result.next() == false )
				{	System.out.println("Cannot find parking slot");
					return null ;	
				}	else {
			block = new ParkingBuilding(result.getInt("blockid") , result.getString("blockname") , result.getInt("totalfloors") , result.getString("location"), result.getInt("twowheelerslotcount"), result.getInt("fourwheelerslotcount"));
			floor = new FloorObject(result.getInt("floorid") , result.getString("floorname") , block ,result.getString("vehicletype"),result.getInt("slotcount"));
			Employee employee =new Employee(result.getString("ename")  , result.getInt("empid"), result.getLong("phoneno") , result.getString("mailid") , result.getString("teamname") , result.getString("seatno") , result.getString("blockname") ) ;
			Vehicle vehicle1 = new Vehicle(result.getInt("vehicleid"),result.getString("vehicleno"), result.getString("vehiclename") , result.getString("vehicletype"), result.getString("vehiclecolour"), employee);			
			parkingSlot = new ParkingSlot(result.getInt("slotid"),floor, vehicle1 ); 
			}	
		} catch (SQLException e) {
			System.out.println("Parking slot not available");
			e.printStackTrace();
		}
			return parkingSlot ;
	}
	public void getSlotOperationList() {
		String query="select * from slotoperation\n" + 
				"                inner join parkingslot on slotoperation.slotid=parkingslot.slotid\n" + 
				"				inner join floors on parkingslot.floorid = floors.floorid \n" + 
				"				inner join parkingblock on floors.blockid = parkingblock.blockid \n" + 
				"				left join vehicle on slotoperation.vehicleid = vehicle.vehicleid \n" + 
				"				left join employee on employee.empid = slotoperation.empid order by slotoperationid desc " ;
		try {
		prep = connection.prepareStatement(query);
		ResultSet  result = prep.executeQuery();
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		System.out.printf("%20s		%20s		%20s		%20s		%20s		%20s		%15s		%20s		%20s	\n","SlotOperationID"," VehicleNumber " ,"EmployeeID","EmployeeName", "ParkingSlot ", " FloorName " , " Block Name " , " Booking Time " , " Cancelling Time ");
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		while (result.next()) {
				System.out.printf("%20s 	%30s		%30s		%30s		%30s		%30s		%30s		%20s		%30s	\n" , result.getInt("slotoperationid")  , result.getInt("vehicle.vehicleid") , result.getString("ename") , result.getString("vehicleno") , result.getInt("slotID") , result.getString("floorname") , result.getString("blockname") ,  result.getTimestamp("slotbookingtime") ,result.getTimestamp("slotcancellingtime")  );
			}
	}catch (SQLException e) {
		System.out.println("No vehicles parked\n\n\n");
		e.printStackTrace();
	}
	}
	
	public boolean isNotVehicleBooked(int vehicleid) {
		String	query = "select * from parkingslot where vehicleid = ? " ;
		try {
			prep = connection.prepareStatement(query);
			prep.setInt(1, vehicleid);
			ResultSet  result = prep.executeQuery();
			if(	result.next() == false ) {
				return true ;
			} else {
				return false ;		}
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}	
	
	public boolean isSlotNotParked(int slotid) {
		String	query = "select * from vehicleparking where slotid = ? order by parkingid desc" ;
		try {
			prep = connection.prepareStatement(query);
			prep.setInt(1, slotid);
			ResultSet  result = prep.executeQuery();
			if(	result.next() == false  )
			{	return true ;	}
			if(result.getTimestamp("outtime") == null )
			{		return false;  }	
		}catch (SQLException e) {
			e.printStackTrace();
		}
		return true;
	}

	public void getMoreEmployeeParkings() {
		String query="select employee.empid,employee.ename,employee.mailid,employee.phoneno,employee.seatno,employee.teamname, count(vehicleno) as totalvehicles from vehicleparking \n" + 
										"				inner join vehicle on vehicle.vehicleid = vehicleparking.vehicleid\n" + 
										"				inner join employee on employee.empid = vehicleparking.empid\n" + 
										"				where outtime is null group by employee.empid having count(vehicleno) > 1 " ;
		try {
		prep = connection.prepareStatement(query);
		ResultSet  result = prep.executeQuery();
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		System.out.printf("%20s		%20s		%30s		%30s		%20s		%20s		%35s		\n","EmployeeID","Employee Name"," MailID " ,"Phone number","Seat number",  " Team Name " , " Vehicles Parked Count ");
		System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
		while (result.next()) {
				System.out.printf("%20s 	%30s		%30s		%30s		%30s		%30s		%30s	\n" , result.getInt("empid")  ,result.getString("ename"),result.getString("mailid") , result.getString("phoneno") , result.getString("seatno") , result.getString("teamname") , result.getInt("totalvehicles") );
			}
	}catch (SQLException e) {
		System.out.println("No vehicles parked\n\n\n");
		e.printStackTrace();
	}
	}

	public Map<Integer, ParkingBuilding> loadParkingBlock() {
		Map<Integer, ParkingBuilding> parkingBlock = new HashMap<>();
		ParkingBuilding block = null ;
		String query = "select * from parkingblock ";
		try {
			prep = connection.prepareStatement(query);
			ResultSet  result = prep.executeQuery();
			while (result.next()) {
					block = new ParkingBuilding(result.getInt("blockid"), result.getString("blockname"), result.getInt("totalfloors"), result.getString("location"), result.getInt("twowheelerslotcount"), result.getInt("fourwheelerslotcount") );
					parkingBlock.put(block.getBlockID(), block);
			}
		}catch (SQLException e) {
			System.out.println("Parking building data loading failed");
			e.printStackTrace();
		}
		return parkingBlock;
	}

	public Map<Integer, FloorObject> loadParkingFloor(Map<Integer, ParkingBuilding> parkingBlock) {
		Map<Integer, FloorObject> parkingFloor = new HashMap<>();
		FloorObject floor = null ;
		String query = " select * from floors right join parkingblock on parkingblock.blockid = floors.blockid " + 
									   "	inner join vehicletypes on vehicletypes.vehicletypeid = floors.floortypeid " ;
		try {
			prep = connection.prepareStatement(query);
			ResultSet  result = prep.executeQuery();
			while (result.next()) {
					floor = new FloorObject(result.getInt("floorid"), result.getString("floorname"), parkingBlock.get( result.getInt("blockid")), result.getString("vehicletype"), result.getInt("slotcount"));
					parkingFloor.put(floor.getFloorNo(), floor);
			}
		}catch (SQLException e) {
			System.out.println("Parking Floors data loading failed");
			e.printStackTrace();
		}
		return parkingFloor;
	}

	public Map<Integer, ParkingSlot> loadParkingSlot(Map<Integer, FloorObject> parkingFloor) {
		Map<Integer, ParkingSlot> parkingSlot = new HashMap<>();
		ParkingSlot slot = null ;
		Vehicle vehicle = null ;
		String query = "select * from parkingslot\n" + 
				"		left join ( select slotid,vehicle.vehicleid, vehicle.vehicleno,vehicle.vehiclename,vehicletypes.vehicletype,vehicle.vehiclecolour,employee.empid,employee.ename,employee.mailid,employee.phoneno,employee.seatno,employee.teamname,employee.blockname from slotoperation\n" + 
				"        left join vehicle on slotoperation.vehicleid = vehicle.vehicleid\n" + 
				"        left join vehicletypes on vehicletypes.vehicletypeid = vehicle.vehicletypeid\n" + 
				"        left join employee on employee.empid = slotoperation.empid\n" + 
				"		where slotcancellingtime is null ) as slotoperationtable  on parkingslot.slotid = slotoperationtable.slotid order by parkingslot.slotid " ;
		try {
			prep = connection.prepareStatement(query);
			ResultSet  result = prep.executeQuery();
			while (result.next()) {
				if(result.getInt("vehicleid") != 0) {
					Employee employee = new Employee(result.getString("ename")  , result.getInt("empid"), result.getLong("phoneno") , result.getString("mailid") , result.getString("teamname") , result.getString("seatno") , result.getString("blockname") ) ;
					vehicle = new Vehicle(result.getInt("vehicleid"),result.getString("vehicleno"), result.getString("vehiclename") , result.getString("vehicletype"), result.getString("vehiclecolour"), employee);			
					slot = new ParkingSlot(result.getInt("slotid"), parkingFloor.get(	result.getInt("floorid") ), vehicle	);
				} else {
					slot = new ParkingSlot(result.getInt("slotid"), parkingFloor.get(	result.getInt("floorid") ), null	);
				}
					parkingSlot.put(slot.getParkingSlotID(), slot);
			}
		}catch (SQLException e) {
			System.out.println("Parking Slot data loading failed");
			e.printStackTrace();
		}
		return parkingSlot;
	}
	public Map<Integer, VehicleParking> loadParkingMap(Map<Integer, ParkingSlot> parkingSlot) {
		Map<Integer,VehicleParking> parkingMap = new HashMap<>();
		VehicleParking parking = null ;
		String query = "select * from vehicleparking  " + 
				"		left join vehicle on vehicleparking.vehicleid = vehicle.vehicleid  " + 
				"        left join employee on employee.empid = vehicleparking.empid  " + 
				"        left join vehicletypes on vehicletypes.vehicletypeid = vehicle.vehicletypeid\n" + 
				"		where outtime is null order by vehicleparking.slotid " ;
		try {
			prep = connection.prepareStatement(query);
			ResultSet  result = prep.executeQuery();
			while (result.next()) {
				Employee employee = new Employee(result.getString("ename")  , result.getInt("empid"), result.getLong("phoneno") , result.getString("mailid") , result.getString("teamname") , result.getString("seatno") , result.getString("blockname") ) ;
				Vehicle vehicle = new Vehicle(result.getInt("vehicleid"),result.getString("vehicleno"), result.getString("vehiclename") , result.getString("vehicletype"), result.getString("vehiclecolour"), employee);			
					parking = new VehicleParking(parkingSlot.get( result.getInt("slotid") ) ,result.getTimestamp("intime") ,  vehicle  ,  employee	); 
					parkingMap.put(parking.getParkingSlot().getParkingSlotID() , parking );
			}
		}catch (SQLException e) {
			System.out.println("Vehicle Parking  data loading failed");
			e.printStackTrace();
		}
		return parkingMap;
	}

	public Map<Integer, SlotOperation> loadBookingMap(Map<Integer, ParkingSlot> parkingSlot) {
		Map<Integer, SlotOperation> bookingMap = new HashMap<>();
		SlotOperation slotOperation = null ;
		String query = "select * from slotoperation " + 
				"        left join vehicle on slotoperation.vehicleid = vehicle.vehicleid " + 
				"        left join employee on employee.empid = slotoperation.empid " + 
				"        left join vehicletypes on vehicletypes.vehicletypeid = vehicle.vehicletypeid\n" + 
				"		where slotcancellingtime is null  order by slotoperation.slotid " ;
		try {
			prep = connection.prepareStatement(query);
			ResultSet  result = prep.executeQuery();
			while (result.next()) {
				Employee employee = new Employee(result.getString("ename")  , result.getInt("empid"), result.getLong("phoneno") , result.getString("mailid") , result.getString("teamname") , result.getString("seatno") , result.getString("blockname") ) ;
				Vehicle vehicle = new Vehicle(result.getInt("vehicleid"),result.getString("vehicleno"), result.getString("vehiclename") , result.getString("vehicletype"), result.getString("vehiclecolour"), employee);			
				slotOperation = new SlotOperation(  employee , vehicle ,   parkingSlot.get( result.getInt("slotid") )  , result.getTimestamp("slotbookingtime") , result.getTimestamp("slotcancellingtime")	);
					bookingMap.put( slotOperation.getSlot().getParkingSlotID()  , slotOperation );
			}
		}catch (SQLException e) {
			System.out.println("Parking Slot Operation data loading failed");
			e.printStackTrace();
		}
		return bookingMap;
	}

	public void cancelBookedSlot(SlotOperation slotOperation) throws SQLException {
			String query = " update slotoperation set slotcancellingtime = now() where vehicleid = ?  and slotid = ?  and slotcancellingtime is  null " ;
				prep = connection.prepareStatement(query); 
				prep.setInt(1, slotOperation.getVehicle().getVehicleID());
				prep.setInt(2, slotOperation.getSlot().getParkingSlotID());
				int result = prep.executeUpdate();
				if(result ==  1)
					System.out.println("Parking slot Cancelling successful ");
				else
					System.out.println("Parking slot Cancelling unsuccessful");
				updateParkingSlot( false , slotOperation) ;
	}

	public void updateParkingSlot(boolean operation , SlotOperation slotOperation ) throws SQLException {
		String query = null ;
		if( operation ) {
			query = "Update parkingslot set vehicleid="+slotOperation.getVehicle().getVehicleID()+" where slotid = "+slotOperation.getSlot().getParkingSlotID() ;
		} else {
				query = "Update parkingslot set vehicleid= null  where slotid = "+slotOperation.getSlot().getParkingSlotID() ; }
			prep = connection.prepareStatement(query);
			int result = prep.executeUpdate(query);
			if(result ==  1)
				System.out.println("Parking slot Updated  ");
			else
				System.out.println("Parking slot not updated ");	
	}

	public SlotOperation bookParkingSlot(ParkingSlot slot, Vehicle vehicle) throws SQLException {
		SlotOperation slotOperation = null ;
		String query = " insert into slotoperation(empid,vehicleid,slotid) values (?,?,?) " ;
			prep = connection.prepareStatement(query);
			prep.setInt(1, vehicle.getEmployee().getEmpID());
			prep.setInt(2, vehicle.getVehicleID());
			prep.setInt(3, slot.getParkingSlotID());
			int result = prep.executeUpdate();
			if(result ==  1) {
				System.out.println("Parking slot Booking successful ");
				slotOperation = new SlotOperation( vehicle.getEmployee() , vehicle, slot, new Date() , null );
				updateParkingSlot(true, slotOperation );
			} else {
				System.out.println("Parking slot Booking unsuccessful");
			}
		return slotOperation;
	}
	
//	public void cancelBookedSlot(SlotOperation slotOperation) {
//		String query = " update slotoperation set slotcancellingtime = now() where vehicleid = ?  and slotid = ?  and slotcancellingtime is  null " ;
//		try {
//			connection.setAutoCommit(false);
//			prep = connection.prepareStatement(query);
//			prep.setInt(1, slotOperation.getVehicle().getVehicleID());
//			prep.setInt(2, slotOperation.getSlot().getParkingSlotID());
//			int result = prep.executeUpdate();
//			if(result ==  1)
//				System.out.println("Parking slot Cancelling successful ");
//			else
//				System.out.println("Parking slot Cancelling unsuccessful");
//			updateParkingSlot( false , slotOperation) ;
//		} catch(SQLException ex) {
//			System.out.println("Parking slot cancelling failed");
//			ex.printStackTrace();
//			try {
//				connection.rollback();
//			} catch (SQLException e) {
//				e.printStackTrace();
//			} finally {
//				try {
//					connection.setAutoCommit(true);
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//		} 
//}
//
//public void updateParkingSlot(boolean operation , SlotOperation slotOperation ) {
//	String query = null ;
//	if( operation ) {
//		query = "Update parkingslot set vehicleid="+slotOperation.getVehicle().getVehicleID()+" where slotid = "+slotOperation.getSlot().getParkingSlotID() ;
//	} else {
//			query = "Update parkingslot set vehicleid= null  where slotid = "+slotOperation.getSlot().getParkingSlotID() ; }
//	try {
//		prep = connection.prepareStatement(query);
//		int result = prep.executeUpdate(query);
//		if(result ==  1)
//			System.out.println("Parking slot Updated  ");
//		else
//			System.out.println("Parking slot not updated ");	
//		connection.commit();
//	} catch (SQLException ex ) {
//		System.out.println("Parking slot Updation failed");
//		ex.printStackTrace();
//	} finally {
//		try {
//			connection.setAutoCommit(true);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//}
//
//public SlotOperation bookParkingSlot(ParkingSlot slot, Vehicle vehicle) {
//	String query = " insert into slotoperation(empid,vehicleid,slotid) values (?,?,?) " ;
//	try {
//		connection.setAutoCommit(false);
//		prep = connection.prepareStatement(query);
//		prep.setInt(1, vehicle.getEmployee().getEmpID());
//		prep.setInt(2, vehicle.getVehicleID());
//		prep.setInt(3, slot.getParkingSlotID());
//		int result = prep.executeUpdate();
//		if(result ==  1) {
//			System.out.println("Parking slot Booking successful ");
//			SlotOperation slotOperation = new SlotOperation( vehicle.getEmployee() , vehicle, slot, new Date() , null );
//			updateParkingSlot(true, slotOperation );
//		} else {
//			System.out.println("Parking slot Booking unsuccessful");
//		}
//	} catch(SQLException ex) {
//		System.out.println("Parking slot Booking failed");
//		ex.printStackTrace();
//		try {
//			connection.rollback();
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	} finally {
//		try {
//			connection.setAutoCommit(true);
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//	}
//	return null;
//}

	 
//	public void cancelBookedSlot(SlotOperation slotOperation) {
//		String query = " update slotoperation set slotcancellingtime = now() where vehicleid = "+slotOperation.getVehicle().getVehicleID()+"  and slotid = "+slotOperation.getSlot().getParkingSlotID()+"  and slotcancellingtime is  null " ;
//		slotOperation = updateParkingSlot(query, false, slotOperation  ) ;
//	}
//	
//	public SlotOperation bookParkingSlot(ParkingSlot slot, Vehicle vehicle) {
//		String query = " insert into slotoperation(empid,vehicleid,slotid) values ("+vehicle.getEmployee().getEmpID()+" , "+vehicle.getVehicleID()+" , "+slot.getParkingSlotID()+" ) " ;
//		SlotOperation slotOperation = new SlotOperation( vehicle.getEmployee() , vehicle, slot, new Date() , null );
//		slotOperation = updateParkingSlot( query, true , slotOperation ) ;
//		return slotOperation ;
//	}
			
//	public SlotOperation updateParkingSlot(String query , boolean operation, SlotOperation slotOperation2  ) {
//		SlotOperation slotOperation = null ;
//		String secondQuery = null ;
//			try {
//			connection.setAutoCommit(false);
//			prep = connection.prepareStatement(query);
//			int result = prep.executeUpdate();
//			if(result ==  1) {
//				System.out.println("Parking slot Booking successful ");
//			} else {
//				System.out.println("Parking slot Booking unsuccessful");
//			}
//			
//			
//			if( operation ) {
//				secondQuery = "Update parkingslot set vehicleid="+slotOperation.getVehicle().getVehicleID()+" where slotid = "+slotOperation.getSlot().getParkingSlotID() ;
//			} else {
//				secondQuery = "Update parkingslot set vehicleid= null  where slotid = "+slotOperation.getSlot().getParkingSlotID() ; }
//			prep = connection.prepareStatement(secondQuery);
//			result = prep.executeUpdate(query);
//			if(result ==  1)
//				System.out.println("Parking slot Updated  ");
//			else
//				System.out.println("Parking slot not updated ");	
//			connection.commit();
//		} catch (SQLException ex ) {
//			System.out.println("Parking slot Updation failed");
//			slotOperation = null ;
//			ex.printStackTrace();
//					try {
//						connection.rollback();
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//		} finally {
//					try {
//						connection.setAutoCommit(true);
//					} catch (SQLException e) {
//						e.printStackTrace();
//					}
//		}
//		return slotOperation;
//	}
	public void getEmployeeGroupedParkingList() {
		String	query="select employee.empid,employee.ename,parkingid,vehicle.vehicleno,vehicleparking.slotid,vehicleparking.vehicleid,floors.floorname,parkingblock.blockname,vehicleparking.intime, vehicleparking.outtime from vehicleparking \n" + 
				"					inner join parkingslot on vehicleparking.slotid=parkingslot.slotid\n" + 
				"                    inner join floors on parkingslot.floorid = floors.floorid \n" + 
				"                    inner join parkingblock on floors.blockid = parkingblock.blockid \n" + 
				"					inner join employee on vehicleparking.empid = employee.empid \n" + 
				"                    inner join vehicle on vehicle.vehicleid = vehicleparking.vehicleid\n" + 
				"					group by vehicleparking.empid , vehicleparking.parkingid order by vehicleparking.empid,parkingid desc" ;
					try {
						prep = connection.prepareStatement(query);
						ResultSet  result = prep.executeQuery();
						System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
						System.out.printf("%20s	%20s		%10s		%10s		%15s		%10s		%10s		%20s		%40s\n","EmployeeID","EmployeeName" , "ParkingID" , " VehicleNumber " , "ParkingSlot ", " FloorName " , " Block Name " , " In Time" ,"Out Time \n");
						System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n");
						while (result.next()) {
								System.out.printf("%20s	%30s		%40s		%20s		%20s		%30s		%40s		%20s		%20s\n" , result.getInt("empid") , result.getString("ename") , result.getInt("parkingid") , result.getString("vehicleno") , result.getInt("slotID") , result.getString("floorname") , result.getString("blockname") ,  result.getTimestamp("intime") ,  result.getTimestamp("outtime") );
							}
					}catch (SQLException e) {
						e.printStackTrace();
					}
		}
}
