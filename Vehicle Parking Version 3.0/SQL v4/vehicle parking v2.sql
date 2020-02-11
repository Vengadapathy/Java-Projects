
create table vehicleparking(
	parkingid int primary key auto_increment,
    slotid int not null,
    intime datetime default now(),
    outtime datetime ,
    vehicleno int not null,
    constraint fk_vehicleno foreign key(vehicleno) references vehicle(vehicleno),
    constraint fk_slotid1 foreign key(slotid) references parkingslot(slotid)
	);
    
    insert into vehicleparking(slotid,vehicleno) values(202,5410) ;
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
    
	select * from vehicleparking inner join employeetovehicle ;
    
    
    show full processlist ;