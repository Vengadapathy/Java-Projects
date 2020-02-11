create table parkingslot(
	slotid int primary key,
    floorid int not null,
    vehicleid int unique,
	constraint fk_floorid foreign key (floorid) references floors(floorid),
	constraint fk_vehicleid2 foreign key (vehicleid) references vehicle(vehicleid),
    constraint uk_slot unique(vehicleid,floorid)
    );
    
    insert into parkingslot (slotid,floorid) values (101,1),(102,1),(103,1),(104,1),(105,1),  (201,2),(202,2),(203,2),(204,2),(205,2)  ,  (301,3),(302,3),(303,3),(304,3),(305,3) ;

    insert into parkingslot (slotid,floorid) values (401,4),(402,4),(403,4),(404,4),(405,4),  (501,5),(502,5),(503,5),(504,5),(505,5)  ,  (601,6),(602,6),(603,6),(604,6),(605,6) ;

    insert into parkingslot (slotid,floorid,isslotbooked,empid) values (106,1,true,1000) ,(209,2,true,1001) ,(318,3,true,1002) ;
    
	insert into parkingslot (slotid,floorid) values (701,7),(702,7),(703,7),(704,7),(705,7);
    
    select * from parkingslot ;
	
    set foreign_key_checks = 1;
    
    alter table parkingslot change column empid empid int unique ;
    
    update parkingslot set vehicleid = null ;