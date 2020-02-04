create table parkingslot(
	slotid int primary key,
    floorno int not null,
    slottypeid int not null,
    blockid int not null,
    isslotbooked boolean default false,
    empid int  unique ,
    constraint fk_blockid foreign key (blockid) references parkingblock(blockid),
	constraint fk_empid4 foreign key (empid) references employee(empid),
    constraint fk_typeid2 foreign key (slottypeid) references vehicletypes(vehicletypeid)
    );
    
    insert into parkingslot(slotid,floorno,slottypeid,blockid) values(102,1,1,1),(203,2,1,1),(307,3,2,1);
    
    insert into parkingslot(slotid,floorno,slottypeid,blockid,isslotbooked,empid) values(306,3,2,1,true,6);
    
    select * from parkingslot inner join parkingblock on parkingslot.blockid=parkingblock.blockid inner join vehicletypes on vehicletypes.vehicletypeid = parkingslot.slottypeid where slotid = 101  ;
        
    update parkingslot set isslotbooked = 0 , empid = null where slotid = 202;
        
    select floorno,count(slotid) from parkingslot group by floorno ;
    
    truncate parkingslot ;
    
    select * from parkingslot ;
    
    select twowheelerslotcount from parkingblock;
    
    