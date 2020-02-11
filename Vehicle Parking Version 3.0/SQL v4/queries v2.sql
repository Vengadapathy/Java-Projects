insert into vehicleparking (slotid,vehicleid) values (205,1),(301,3) ;

select * from vehicleparking ;
/*GET VEHICLE USING EMPLOYEEID*/
select * from employee
		inner join employeetovehicle on employeetovehicle.empid = employee.empid
        inner join vehicle on vehicle.vehicleid = employeetovehicle.vehicleid
        inner join vehicletypes on vehicle.vehicletypeid = vehicletypes.vehicletypeid
        where employee.empid = 1000 and vehicle.vehicleno = 'TN 05 FN 1230';

/*CALCULATING FILLED AND UNFILLED SLOTS IN EACH FLOOR*/
select  * from floors right join 
(select floors.floorid,floors.floorname,vehicletypes.vehicletype as floortype,count(vehicleparking.slotid) as totalslotsfilled , (floors.slotcount-count(vehicleparking.slotid)) as remainingslots from vehicleparking 
			right join parkingslot on parkingslot.slotid = vehicleparking.slotid
			right join floors on floors.floorid = parkingslot.floorid
			right join parkingblock on parkingblock.blockid = floors.blockid
            inner join vehicletypes on vehicletypes.vehicletypeid = floors.floortypeid
            where vehicleparking.outtime is null and vehicletypes.vehicletypeid = 1 group by floors.floorid	) as tb on floors.floorid = tb.floorid where remainingslots >0;
            
/*GET EMPLOYEE QUERY*/
select * from employee where empid = 1001 ;

/*GET PARKING BLOCK QUERY*/
select * from parkingblock where blockid = 1 ;

/*GET FLOORS QUERY*/
select * from floors where flooris = 1 ;

/*GET PARKING SLOT QUERY*/
select * from parkingslot
		inner join floors on floors.floorid = parkingslot.floorid
		inner join parkingblock on floors.blockid = parkingblock.blockid
		inner join vehicletypes on vehicletypes.vehicletypeid = floors.floortypeid
        left join vehicle on vehicle.vehicleid = parkingslot.vehicleid
        left join employeetovehicle on employeetovehicle.vehicleid = vehicle.vehicleid
        left join employee on employee.empid = employeetovehicle.empid
        where slotid = 105 ;
        
/*FIND MY VEHICLE*/
/*GET SLOTID AND USING THE RESULT FROM QUERY FIND SLOT DETIALS FROM PREVIOUS QUERY*/
select slotid from vehicleparking 
		inner join vehicle on vehicle.vehicleid = vehicleparking.vehicleid
        where vehicleno = 'TN 05 FN 1230' and outtime is null order by intime desc;
        
/*GET VEHICLE USING VEHICLE NUMBER*/
select * from vehicle 
		inner join employeetovehicle on employeetovehicle.vehicleid = vehicle.vehicleid
        inner join employee on employee.empid = employeetovehicle.empid
        inner join vehicletypes on vehicletypes.vehicletypeid = vehicle.vehicletypeid
        where vehicle.vehicleno = 'TN 05 FN 1230' ;
        
/*SLOT OPERATION*/
SELECT * from slotoperation 
		inner join slotservicetypes on slotservicetypes.servicetypeid = slotoperation.slotoperationtypeid
		where empid = 1000 and slotoperationtypeid = 1 order by dateofoperation desc;
        
/*PARKING SLOT LIST*/
select parkingid,employee.empid,employee.ename,vehicle.vehicleno,vehicleparking.slotid,,parkingslot.vehicleid,floors.floorname,parkingblock.blockname,vehicleparking.intime, vehicleparking.outtime from vehicleparking 
					inner join parkingslot on vehicleparking.slotid=parkingslot.slotid
                    inner join floors on parkingslot.floorid = floors.floorid 
                    inner join parkingblock on floors.blockid = parkingblock.blockid 
					inner join employeetovehicle on employeetovehicle.vehicleid = vehicleparking.vehicleid
					inner join employee on employeetovehicle.empid = employee.empid 
                    inner join vehicle on vehicle.vehicleid = employeetovehicle.vehicleid
					where date(intime) >= date(now() - INTERVAL 1 day) ;

/*GET PARKING SLOT LIST FROM A EMPLOYEE*/
select * from vehicleparking 
		inner join parkingslot on vehicleparking.slotid=parkingslot.slotid
                    inner join floors on parkingslot.floorid = floors.floorid 
                    inner join parkingblock on floors.blockid = parkingblock.blockid 
					inner join employeetovehicle on employeetovehicle.vehicleid = vehicleparking.vehicleid
					inner join employee on employeetovehicle.empid = employee.empid 
                    inner join vehicle on vehicle.vehicleid = employeetovehicle.vehicleid
                    where employee.empid = 1000 ;

/*GET PARKED VEHICLES IN A PARTICULAR FLOOR*/
select * from vehicleparking 
		inner join parkingslot on vehicleparking.slotid=parkingslot.slotid
                    inner join floors on parkingslot.floorid = floors.floorid 
                    inner join parkingblock on floors.blockid = parkingblock.blockid 
					inner join employeetovehicle on employeetovehicle.vehicleid = vehicleparking.vehicleid
					inner join employee on employeetovehicle.empid = employee.empid 
                    inner join vehicle on vehicle.vehicleid = employeetovehicle.vehicleid
                    where floors.floorid  between 2 and 3 ;

/*GET A FREE SLOT FOR EMPLOYEE REQUESTING FOR A BOOKED SLOT*/

		select *  from parkingslot
		inner join floors on parkingslot.floorid = floors.floorid 
		inner join parkingblock on floors.blockid = parkingblock.blockid 
        left join vehicle on vehicle.vehicleid = parkingslot.vehicleid
		left join employeetovehicle on employeetovehicle.vehicleid = vehicle.vehicleid
		left join employee on employeetovehicle.empid = employee.empid
        left join vehicletypes on vehicletypes.vehicletypeid = floors.floortypeid
		left join (select * from vehicleparking inner join ( select max(parkingid) as id from vehicleparking group by slotid ) as table1 on table1.id = vehicleparking.parkingid ) as parking on parking.slotid = parkingslot.slotid
        where (parking.outtime is not null or (parking.intime is null and parking.outtime is null)) and parkingslot.vehicleid is null and  vehicletypes.vehicletype = 'TWO WHEELER'   ;

select * from parkingslot
		inner join floors on parkingslot.floorid = floors.floorid 
		inner join parkingblock on floors.blockid = parkingblock.blockid 
        inner join slotoperation on slotoperation.slotid = parkingslot.slotid
        inner join vehicletypes on vehicletypes.vehicletypeid = floors.floortypeid
        left join vehicle on vehicle.vehicleid = parkingslot.vehicleid
		left join employeetovehicle on employeetovehicle.vehicleid = vehicle.vehicleid
		left join employee on employeetovehicle.empid = employee.empid
        where vehicle.vehicleid = 'TN 05 FN 1230' order by dateofoperation desc ;
        
        

select * from slotoperation
						inner join slotservicetypes on slotservicetypes.servicetypeid = slotoperation.slotoperationtypeid
						inner join parkingslot on slotoperation.slotid=parkingslot.slotid
						inner join floors on parkingslot.floorid = floors.floorid 
						inner join parkingblock on floors.blockid = parkingblock.blockid 
						inner join employee on parkingslot.empid = employee.empid 
						inner join employeetovehicle on employeetovehicle.empid = employee.empid 
						inner join vehicle on vehicle.vehicleid = employeetovehicle.vehicleid order by dateofoperation;
                        
/*IS VEHICLE ALREADY BOOKED ANY SLOT*/
select * from parkingslot where vehicleid = 1 ;

/*PARKING SLOT OPERATION LIST*/
select * from slotoperation
				inner join slotservicetypes on slotservicetypes.servicetypeid = slotoperation.slotoperationtypeid
                inner join parkingslot on slotoperation.slotid=parkingslot.slotid
				inner join floors on parkingslot.floorid = floors.floorid 
				inner join parkingblock on floors.blockid = parkingblock.blockid 
				left join vehicle on slotoperation.vehicleid = vehicle.vehicleid 
				left join employeetovehicle on employeetovehicle.vehicleid = slotoperation.vehicleid  
				left join employee on employee.empid = employeetovehicle.empid order by dateofoperation desc;




select	sum(slotcount) as total,sum(totalslotsfilled) as filledslots, sum(remainingslots) as remainingslots from floors 
right join (               
select count(vehicleparking.slotid) as totalslotsfilled , (floors.slotcount-count(vehicleparking.slotid)) as remainingslots, floors.floorid from vehicleparking 
			right join parkingslot on parkingslot.slotid = vehicleparking.slotid
			right join floors on floors.floorid = parkingslot.floorid
			right join parkingblock on parkingblock.blockid = floors.blockid
            inner join vehicletypes on vehicletypes.vehicletypeid = floors.floortypeid
            where vehicleparking.outtime is null and vehicletypes.vehicletypeid = 1 group by floors.floorid	 ) as tb on tb.floorid = floors.floorid;
       
       
       
       
select * from floors ;