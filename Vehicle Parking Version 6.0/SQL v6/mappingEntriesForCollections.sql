create database vehicleparking_v7 ;

select * from employee ;

select * from vehicleparking ;


select * from slotoperation ;
        
select * from slotoperation group by slotoperationid ;        

/*PARKING BUILDING OBJECT MAP */
select * from parkingblock ;


/*FLOOR OBJECTS MAP*/
select * from floors 
			right join parkingblock on parkingblock.blockid = floors.blockid
			inner join vehicletypes on vehicletypes.vehicletypeid = floors.floortypeid ;


/*SLOT OBJECTS MAP*/

select * from parkingslot
		left join ( select slotid,vehicle.vehicleid, vehicle.vehicleno,vehicle.vehiclename,vehicletypes.vehicletype,vehicle.vehiclecolour,employee.empid,employee.ename,employee.mailid,employee.phoneno,employee.seatno,employee.teamname,employee.blockname from slotoperation
        left join vehicle on slotoperation.vehicleid = vehicle.vehicleid
        left join vehicletypes on vehicletypes.vehicletypeid = vehicle.vehicletypeid
        left join employee on employee.empid = slotoperation.empid
		where slotcancellingtime is null ) as slotoperationtable  on parkingslot.slotid = slotoperationtable.slotid order by parkingslot.slotid ;

select * from slotoperation
        left join vehicle on slotoperation.vehicleid = vehicle.vehicleid
        left join vehicletypes on vehicletypes.vehicletypeid = vehicle.vehicletypeid
        left join employee on employee.empid = slotoperation.empid
		where slotcancellingtime is null ;

/*SLOT PARKING OBJECTS FOR PARKING-MAP*/
select * from vehicleparking
		left join vehicle on vehicleparking.vehicleid = vehicle.vehicleid
        left join employee on employee.empid = vehicleparking.empid
		left join vehicletypes on vehicletypes.vehicletypeid = vehicle.vehicletypeid
        where outtime is null order by vehicleparking.slotid ;
        
/*SLOT OPERATION OBJECTS FOR BOOKINGMAP*/
select * from slotoperation
        left join vehicle on slotoperation.vehicleid = vehicle.vehicleid
        left join employee on employee.empid = slotoperation.empid
		left join vehicletypes on vehicletypes.vehicletypeid = vehicle.vehicletypeid
        where slotcancellingtime is null  order by slotoperation.slotid ;
        
        
/*VEHICLE TO PARKED SLOT MAPPING*/
select parkingslot.slotid,parkingslot.floorid,floors.blockid,floors.floorname,vehicletypes.vehicletype,floors.slotcount,parkingblock.blockname,parkingblock.location,parkingblock.totalfloors,parkingblock.twowheelerslotcount, parkingblock.fourwheelerslotcount from vehicleparking
		left join parkingslot on parkingslot.slotid = vehicleparking.slotid
        left join floors on floors.floorid = parkingslot.floorid
		left join parkingblock on floors.blockid = parkingblock.blockid
        left join vehicle on parkingslot.vehicleid = vehicle.vehicleid
        left join employeetovehicle on employeetovehicle.vehicleid = vehicle.vehicleid
        left join employee on employee.empid = employeetovehicle.empid
		left join vehicletypes on vehicletypes.vehicletypeid = floors.floortypeid
        where outtime is null ;

select * from vehicleparking
		left join parkingslot on parkingslot.slotid = vehicleparking.slotid
        left join floors on floors.floorid = parkingslot.floorid
		left join parkingblock on floors.blockid = parkingblock.blockid
        left join vehicle on parkingslot.vehicleid = vehicle.vehicleid
        left join employeetovehicle on employeetovehicle.vehicleid = vehicle.vehicleid
        left join employee on employee.empid = employeetovehicle.empid
		left join vehicletypes on vehicletypes.vehicletypeid = floors.floortypeid
        where outtime is null ;


/*VEHICLE TO BOOKED SLOT MAPPING*/
select * from slotoperation
		left join parkingslot on parkingslot.slotid = slotoperation.slotid
        left join floors on floors.floorid = parkingslot.floorid
		left join parkingblock on floors.blockid = parkingblock.blockid
        left join vehicle on slotoperation.vehicleid = vehicle.vehicleid
        left join employee on employee.empid = slotoperation.empid
		left join vehicletypes on vehicletypes.vehicletypeid = floors.floortypeid
        where slotcancellingtime is null ;
