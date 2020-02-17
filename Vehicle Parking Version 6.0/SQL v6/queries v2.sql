insert into vehicleparking (slotid,vehicleid) values (205,1),(301,3) ;

select * from vehicleparking ;
/*GET VEHICLE USING EMPLOYEEID*/
select * from employee
		inner join employeetovehicle on employeetovehicle.empid = employee.empid
        inner join vehicle on vehicle.vehicleid = employeetovehicle.vehicleid
        inner join vehicletypes on vehicle.vehicletypeid = vehicletypes.vehicletypeid
        where employee.empid = 1000 and vehicle.vehicleno = 'TN 05 FN 1230';

/*CALCULATING FILLED AND UNFILLED SLOTS IN EACH FLOOR*/

select floors.floorid, vehicletypes.vehicletype ,count(slotid) as remainingslots from parkingslot
		right join floors on floors.floorid = parkingslot.floorid
        inner join vehicletypes on vehicletypes.vehicletypeid = floors.floortypeid
where parkingslot.slotid NOT IN (select slotid from vehicleparking where outtime is null) AND parkingslot.slotid NOT IN (select slotid from slotoperation where slotcancellingtime is null) and vehicletypeid = 1 group by floors.floorid;


select * from parkingslot
		right join floors on floors.floorid = parkingslot.floorid
        inner join vehicletypes on vehicletypes.vehicletypeid = floors.floortypeid
where parkingslot.slotid NOT IN (select slotid from vehicleparking where outtime is null) AND parkingslot.slotid NOT IN (select slotid from slotoperation where slotcancellingtime is null) ;



/*select  * from floors right join 
(select floors.floorid,floors.floorname,vehicletypes.vehicletype as floortype,count(vehicleparking.slotid) as totalslotsfilled , (floors.slotcount-count(vehicleparking.slotid)) as remainingslots from vehicleparking 
			right join parkingslot on parkingslot.slotid = vehicleparking.slotid
			right join floors on floors.floorid = parkingslot.floorid
			right join parkingblock on parkingblock.blockid = floors.blockid
            inner join vehicletypes on vehicletypes.vehicletypeid = floors.floortypeid
            where vehicleparking.outtime is null and vehicletypes.vehicletypeid = 1 group by floors.floorid	) as tb on floors.floorid = tb.floorid where remainingslots >0;
            
/* CALCULATING FILLED AND UNFILLED SLOTS IN EACH FLOOR CONSIDERING BOOKED SLOT ALSO */
/*select  * from floors right join 
(select floors.floorid,floors.floorname,vehicletypes.vehicletype as floortype,count(vehicleparking.slotid) as totalslotsfilled , (floors.slotcount-count(vehicleparking.slotid)) as remainingslots from vehicleparking 
			right join parkingslot on parkingslot.slotid = vehicleparking.slotid
			right join floors on floors.floorid = parkingslot.floorid
			right join parkingblock on parkingblock.blockid = floors.blockid
            inner join vehicletypes on vehicletypes.vehicletypeid = floors.floortypeid
            where vehicleparking.outtime is null and vehicletypes.vehicletypeid = 1 group by floors.floorid	) as tb on floors.floorid = tb.floorid where remainingslots >0;
          

*/
            
select  * from floors right join 
(select floors.floorid,floors.floorname,vehicletypes.vehicletype as floortype,count(vehicleparking.slotid) as totalslotsfilled , (floors.slotcount-count(vehicleparking.slotid)) as remainingslots from vehicleparking 
			right join parkingslot on parkingslot.slotid = vehicleparking.slotid
			right join floors on floors.floorid = parkingslot.floorid
			right join parkingblock on parkingblock.blockid = floors.blockid
            inner join slotoperation on slotoperation.slotid = parkingslot.slotid
            inner join vehicletypes on vehicletypes.vehicletypeid = floors.floortypeid
            where vehicleparking.outtime is null and slotoperation.slotcancellingtime is null and vehicletypes.vehicletypeid = 1 group by floors.floorid	) as tb on floors.floorid = tb.floorid where remainingslots >0;
            
select * from vehicleparking
				inner join slotoperation on slotoperation.slotid = vehicleparking.slotid 
                where slotoperation.slotcancellingtime is null and vehicleparking.outtime is null ;
            
select * from slotoperation ;            
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
		where empid = 1000 order by dateofoperation desc;
        
/*PARKING SLOT LIST*/
select parkingid,employee.empid,employee.ename,vehicle.vehicleno,vehicleparking.slotid,vehicleparking.vehicleid,floors.floorname,parkingblock.blockname,vehicleparking.intime, vehicleparking.outtime from vehicleparking 
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
        where (parking.outtime is not null or (parking.intime is null and parking.outtime is null) or parking.vehicleid = 5) and parkingslot.vehicleid is null and  vehicletypes.vehicletype = 'TWO WHEELER'    ;

select * from parkingslot
		inner join floors on parkingslot.floorid = floors.floorid 
		inner join parkingblock on floors.blockid = parkingblock.blockid 
        inner join slotoperation on slotoperation.slotid = parkingslot.slotid
        inner join vehicletypes on vehicletypes.vehicletypeid = floors.floortypeid
        left join vehicle on vehicle.vehicleid = slotoperation.vehicleid
		left join employee on slotoperation.empid = employee.empid
        where vehicle.vehicleno = 'TN 01 B 1001' and slotcancellingtime is null order by slotoperationid desc ;
        
        

select * from slotoperation
						inner join parkingslot on slotoperation.slotid=parkingslot.slotid
						inner join floors on parkingslot.floorid = floors.floorid 
						inner join parkingblock on floors.blockid = parkingblock.blockid 
						inner join employee on slotoperation.empid = employee.empid 
						inner join vehicle on vehicle.vehicleid = slotoperation.vehicleid order by slotoperationid desc;
                        
/*IS VEHICLE ALREADY BOOKED ANY SLOT*/
select * from parkingslot where vehicleid = 1 ;

/*PARKING SLOT OPERATION LIST*/
select * from slotoperation
                inner join parkingslot on slotoperation.slotid=parkingslot.slotid
				inner join floors on parkingslot.floorid = floors.floorid 
				inner join parkingblock on floors.blockid = parkingblock.blockid 
				left join vehicle on slotoperation.vehicleid = vehicle.vehicleid 
				left join employee on employee.empid = slotoperation.empid order by slotoperationid desc;


select * from parkingslot where vehicleid = 8 ;

select	sum(slotcount) as total,sum(totalslotsfilled) as filledslots, sum(remainingslots) as remainingslots from floors 
right join (               
select count(vehicleparking.slotid) as totalslotsfilled , (floors.slotcount-count(vehicleparking.slotid)) as remainingslots, floors.floorid from vehicleparking 
			right join parkingslot on parkingslot.slotid = vehicleparking.slotid
			right join floors on floors.floorid = parkingslot.floorid
			right join parkingblock on parkingblock.blockid = floors.blockid
            inner join vehicletypes on vehicletypes.vehicletypeid = floors.floortypeid
            where vehicleparking.outtime is null and vehicletypes.vehicletypeid = 1 group by floors.floorid	 ) as tb on tb.floorid = floors.floorid;
       
       
select * from floors ;

select * from vehicleparking where outtime is null ;

select vehicletypes.vehicletype,count(slotid) as filled from vehicleparking 
		inner join vehicle on vehicle.vehicleid = vehicleparking.vehicleid
		inner join vehicletypes on vehicletypes.vehicletypeid = vehicle.vehicletypeid
		where outtime is null group by vehicle.vehicletypeid;

select * from vehicleparking;
        
select * from parkingslot 
		left join vehicleparking on parkingslot.slotid = vehicleparking.slotid
		left join vehicle on vehicleparking.vehicleid = vehicle.vehicleid
        left join employee on employee.empid = vehicleparking.empid
		left join vehicletypes on vehicletypes.vehicletypeid = vehicle.vehicletypeid
        where outtime is null or (outtime is not null and outtime is not null) order by parkingslot.slotid  ;
        
/*VEHICLE PARKING LIST FOR EMPLOYEE ON DAILY BASIS */  
select employee.empid,employee.ename,parkingid,vehicle.vehicleno,vehicleparking.slotid,vehicleparking.vehicleid,floors.floorname,parkingblock.blockname,vehicleparking.intime, vehicleparking.outtime from vehicleparking 
					inner join parkingslot on vehicleparking.slotid=parkingslot.slotid
                    inner join floors on parkingslot.floorid = floors.floorid 
                    inner join parkingblock on floors.blockid = parkingblock.blockid 
					inner join employee on vehicleparking.empid = employee.empid 
                    inner join vehicle on vehicle.vehicleid = vehicleparking.vehicleid
					group by vehicleparking.empid , vehicleparking.parkingid order by vehicleparking.empid,parkingid desc;
                    
/*FLOOR WISE BOOKED AND PARKED SLOT COUNT*/
select * from parkingslot
		left join vehicleparking on parkingslot.slotid = vehicleparking.slotid
        left join slotoperation on parkingslot.slotid = slotoperation.slotid
        where outtime is not null and slotcancellingtime is not null ;
        
        
        
select floors.floorid,count(vehicleparking.slotid) from vehicleparking
				right join parkingslot on vehicleparking.slotid=parkingslot.slotid 
                right join floors on parkingslot.floorid = floors.floorid group by floors.floorid;
				right join parkingblock on floors.blockid = parkingblock.blockid 
        
        
select * from parkingslot 
		left join (select slotid,max(parkingid) from vehicleparking where outtime is not null group by slotid) as tb on tb.slotid = parkingslot.slotid;
        

select * from parkingslot 
		left join vehicleparking on parkingslot.slotid = vehicleparking.slotid
        where vehicleparking.outtime is not null ;

select * 
from parkingslot
where parkingslot.slotid NOT IN (select slotid from vehicleparking where outtime is null) AND parkingslot.slotid NOT IN (select slotid from slotoperation where slotcancellingtime is null);


select * from vehicleparking ;

select * from slotoperation where slotcancellingtime is null ;