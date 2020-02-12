
create table vehicleparking(
	parkingid int primary key auto_increment,
    slotid int not null,
    intime datetime default now(),
    outtime datetime ,
    vehicleid int not null,
    constraint fk_vehicleid foreign key(vehicleid) references vehicle(vehicleid),
    constraint fk_slotid1 foreign key(slotid) references parkingslot(slotid)
	);
    
    insert into vehicleparking(slotid,vehicleid) values(105,8) ;
    
    alter table vehicleparking change column empid empid int not null ; 
    alter table vehicleparking drop column empid ;
	alter table vehicleparking add constraint fk_empid5 foreign key(empid) references employee(empid) ; 


    select * from vehicleparking;
    
    truncate vehicleparking ;
    
	SELECT * FROM vehicleparking 
				inner join parkingslot on vehicleparking.slotid = parkingslot.slotid  
				inner join parkingblock on parkingslot.blockid=parkingblock.blockid
                inner join vehicletypes on vehicletypes.vehicletypeid = parkingslot.slottypeid
                order by intime desc limit 1;
	
    UPDATE vehicleparking SET outtime = now() where slotid = 101 ;
    
	SELECT outtime from vehicleparking where slotid= 101 order by intime desc ;
    
    select vehicleno,slotid  from vehicleparking where  outtime is null ;

	select * from parkingslot ;
    
   SELECT * FROM vehicleparking 
				inner join parkingslot on vehicleparking.slotid = parkingslot.slotid 
				inner join parkingblock on parkingslot.blockid=parkingblock.blockid inner join vehicletypes on vehicletypes.vehicletypeid = parkingslot.slottypeid
				WHERE vehicleno = 5 order by intime desc limit 1;
    
	select * from vehicleparking  ;
        
    show full processlist ;
    
    update vehicleparking set outtime = now() ;
    
    select * from vehicleparking where slotid = 101 order by parkingid desc ;
       
    select * from vehicleparking inner join ( select max(parkingid) as id from vehicleparking group by slotid ) as ta on ta.id = vehicleparking.parkingid;   
    
    insert into vehicleparking (slotid,intime,outtime,vehicleid) values (101,'2020-01-01 09:30:55','2020-01-01 04:30:55',1),(102,'2020-01-01 09:30:55','2020-01-01 04:30:55',2),(103,'2020-01-01 09:30:55','2020-01-01 04:20:55',3) ;

	insert into vehicleparking (slotid,intime,outtime,vehicleid) values (101,'2020-01-02 09:30:55','2020-01-02 04:30:55',1),(102,'2020-01-02 09:30:55','2020-01-02 04:30:55',2),(103,'2020-01-02 09:30:55','2020-01-02 04:20:55',3) ;

    insert into vehicleparking (slotid,intime,outtime,vehicleid) values (201,'2020-01-03 09:30:55','2020-01-03 04:30:55',1),(202,'2020-01-03 09:30:55','2020-01-03 04:30:55',2),(203,'2020-01-03 09:30:55','2020-01-03 04:20:55',3) ;

    insert into vehicleparking (slotid,intime,outtime,vehicleid) values (203,'2020-01-04 09:30:55','2020-01-04 04:30:55',1),(204,'2020-01-04 09:30:55','2020-01-04 04:30:55',2),(205,'2020-01-04 09:30:55','2020-01-04 04:20:55',3) ;
	
    insert into vehicleparking (slotid,intime,outtime,vehicleid) values (102,'2020-01-07 09:30:55','2020-01-07 04:30:55',5),(103,'2020-01-07 09:30:55','2020-01-07 04:30:55',7),(104,'2020-01-07 09:30:55','2020-01-07 04:20:55',9) ;
    

    insert into vehicleparking (slotid,intime,outtime,vehicleid) values (103,'2020-01-10 09:30:55','2020-01-10 04:30:55',2),(204,'2020-01-10 09:30:55','2020-01-10 04:30:55',4),(205,'2020-01-10 09:30:55','2020-01-10 04:20:55',6) ;










	insert into vehicleparking (slotid,intime,vehicleid) values (101,'2020-01-28 12:20:55',6),(202,'2020-01-22 03:20:55',7),(203,'2020-02-27 03:20:55',4) ;

    
	insert into vehicleparking (slotid,intime,vehicleid) values (201,'2020-01-20 10:20:55',2),(202,'2020-01-22 03:20:55',3),(203,'2020-01-27 03:20:55',4) ;
    
    insert into vehicleparking (slotid,intime,vehicleid) values (301,'2020-01-31 06:10:55',13),(302,'2020-02-01 13:00:55',14),(303,'2020-02-03 23:20:55',15) ;
    
   	insert into vehicleparking (slotid,intime,vehicleid) values (204,'2020-01-01 10:2:55',4),(202,'2020-01-27 03:20:55',8),(203,'2020-02-04 03:20:55',9) ;

    
    select parkingid,employee.empid,employee.ename,vehicle.vehicleno,vehicleparking.slotid,parkingslot.vehicleid,floors.floorname,parkingblock.blockname,vehicleparking.intime, vehicleparking.outtime from vehicleparking  
							left join parkingslot on vehicleparking.slotid=parkingslot.slotid 
							 left join floors on parkingslot.floorid = floors.floorid   
							  left join parkingblock on floors.blockid = parkingblock.blockid 
                              left join vehicle on vehicle.vehicleid = vehicleparking.vehicleid
							left join employeetovehicle on employeetovehicle.vehicleid = vehicle.vehicleid  
							left join employee on employeetovehicle.empid = employee.empid  
							where date(intime) >= date(now() - INTERVAL 10  day) ;
                            
	select * from vehicleparking left join parkingslot on vehicleparking.slotid = parkingslot.slotid
								left join floors on parkingslot.floorid = floors.floorid   
							  left join parkingblock on floors.blockid = parkingblock.blockid 
                              left join vehicle on vehicle.vehicleid = vehicleparking.vehicleid
							left join employeetovehicle on employeetovehicle.vehicleid = vehicle.vehicleid  
							left join employee on employeetovehicle.empid = employee.empid  
							where date(intime) >= date(now() - INTERVAL 100  day) 	;
                            
	select intime ,outtime from vehicleparking where slotid = 101 order by intime  ;
    
    
    insert into vehicleparking (slotid,intime,vehicleid,empid) values (206,'2020-02-12 10:20:55',3,1002);
    
    delete from vehicleparking where parkingid = 65 ;