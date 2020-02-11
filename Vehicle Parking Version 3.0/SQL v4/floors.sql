create table floors(
	floorid int primary key auto_increment,
    floorname varchar(30) not null,
    blockid int not null,
    floortypeid int not null,
	slotcount int not null,
    constraint fk_blockid foreign key (blockid) references parkingblock(blockid),
    constraint fk_typeid2 foreign key (floortypeid) references vehicletypes(vehicletypeid),
    constraint uk_floor unique key (floorname,blockid)
);

insert into floors (floorname,blockid,floortypeid,slotcount) values ('FIRST FLOOR',1,1,50),('SECOND FLOOR',1,1,50),('THIRD FLOOR',1,2,20),('FOURTH FLOOR',1,2,20),('FIFTH FLOOR',1,2,20),('SIXTH FLOOR',1,2,20),('SEVENTH FLOOR',1,2,20) ;


select * from floors ;