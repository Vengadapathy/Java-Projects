create table vehicleparking(
	parkingid int primary key auto_increment,
    slotid int not null,
    intime datetime default now(),
    outtime datetime ,
    vehicleid int not null,
    constraint fk_vehicleid1 foreign key(vehicleid) references vehicle(vehicleid),
    constraint fk_slotid1 foreign key(slotid) references parkingslot(slotid)
	);
    
    select * from vehicleparking ;
    
    update vehicleparking set outtime = now() ;
    
    truncate vehicleparking ;