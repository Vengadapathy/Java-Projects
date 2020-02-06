create table parkingslot(
	slotid int primary key,
    floorno int not null,
    slottypeid int not null,
    blockid int not null,
    empid int  unique ,
    constraint fk_blockid foreign key (blockid) references parkingblock(blockid),
	constraint fk_empid4 foreign key (empid) references employee(empid),
    constraint fk_typeid2 foreign key (slottypeid) references vehicletypes(vehicletypeid)
    );
    describe parkingslot ;
    insert into parkingslot(slotid,floorno,slottypeid,blockid) values(102,1,1,1),(203,2,1,1),(307,3,2,1);
    
    insert into parkingslot(slotid,floorno,slottypeid,blockid,isslotbooked,empid) values(306,3,2,6);
    
    select * from parkingslot inner join parkingblock on parkingslot.blockid=parkingblock.blockid inner join vehicletypes on vehicletypes.vehicletypeid = parkingslot.slottypeid where slotid = 101  ;
        
    update parkingslot set empid = null ;
        
    select floorno,count(slotid) from parkingslot group by floorno ;
    
    set foreign_key_checks = 1 ;
    
    alter table parkingslot drop column isslotbooked   ;
    
    truncate parkingslot ;
    
    select * from parkingslot ;
    
    select twowheelerslotcount from parkingblock;
    
    
    insert into parkingslot (slotid,floorno,slottypeid,blockid) values (101,1,1,1),(102,1,1,1),(103,1,1,1),(104,1,1,1),(105,1,1,1),  (201,2,1,1),(202,2,1,1),(203,2,1,1),(204,2,1,1),(205,2,1,1)  ,  (301,3,2,1),(302,3,2,1),(303,3,2,1),(304,3,2,1),(305,3,2,1) ;
    insert into parkingslot (slotid,floorno,slottypeid,blockid) values (401,4,2,1),(402,4,2,1),(403,4,2,1),(404,4,2,1),(405,4,2,1),  (501,5,2,1),(502,5,2,1),(503,5,2,1),(504,5,2,1),(505,5,2,1)  ,  (601,6,2,1),(602,6,2,1),(603,6,2,1),(604,6,2,1),(605,6,2,1) , (701,7,2,1),(702,7,2,1),(703,7,2,1),(704,7,2,1),(705,7,2,1) ;

    