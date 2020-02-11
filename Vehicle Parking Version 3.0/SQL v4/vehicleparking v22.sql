
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
    
	select * from vehicleparking order by parkingid,slotid ;
        
    show full processlist ;
    
    update vehicleparking set outtime = now() ;
    
    select * from vehicleparking where slotid = 101 order by parkingid desc ;
       
    select * from vehicleparking inner join ( select max(parkingid) as id from vehicleparking group by slotid ) as ta on ta.id = vehicleparking.parkingid;   