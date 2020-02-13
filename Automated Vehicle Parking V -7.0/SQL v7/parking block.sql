create table parkingblock(
	blockid int primary key auto_increment,
    blockname varchar(40) not null,
    totalfloors int not null,
    location varchar(40) not null,
    twowheelerslotcount int not null,
    fourwheelerslotcount int not null
    );
    
    insert into parkingblock(blockname,totalfloors,location,twowheelerslotcount,fourwheelerslotcount) values('MLCP',7,'opposite to tower builidng',10,25);
    
    select * from parkingblock ;
    
    truncate parkingblock ;
    
    set foreign_key_checks = 1;