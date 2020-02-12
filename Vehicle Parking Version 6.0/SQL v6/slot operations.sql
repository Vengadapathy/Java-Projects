create table slotoperation(
	slotoperationid int primary key auto_increment,
    vehicleid int not null,
    slotid int not null,
    slotoperationtypeid int not null,
    dateofoperation datetime default now(),
	constraint fk_vehicle3 foreign key(vehicleid) references vehicle(vehicleid),
    constraint fk_slotid foreign key(slotid) references parkingslot(slotid),
	constraint fk_typeid1 foreign key(slotoperationtypeid) references slotservicetypes(servicetypeid)
	);
	
    truncate slotoperation;
        
    insert into slotoperation(vehicleid,slotid,slotoperationtypeid) values (13,102,2);
    
    select * from slotoperation ;
    SELECT * from slotoperation where empid = 1 and slotoperationtypeid = 1 order by dateofoperation desc ;
    
    SELECT outtime from vehicleparking where slotid= 101 order by intime desc ;
    
    select empid,slotoperationtypeid from slotoperation where slotid = 306 order by dateofoperation desc;
    
    delete from slotoperation where slotoperationid >= 14;
    
    select * from parkingslot where slotid = 101;
    
    
    