create table slotoperation(
	slotoperationid int primary key auto_increment,
    empid int not null,
    slotid int not null,
    slotoperationtypeid int not null,
    dateofoperation datetime default now(),
	constraint fk_empid2 foreign key(empid) references employee(empid),
    constraint fk_slotid foreign key(slotid) references parkingslot(slotid),
	constraint fk_typeid1 foreign key(slotoperationtypeid) references slotservicetypes(servicetypeid)
	);
	
    truncate slotoperation ;
    
    insert into slotoperation(empid,slotid,slotoperationtypeid) values (1000,101,1);
    select * from slotoperation ;
    SELECT * from slotoperation where empid = 1 and slotoperationtypeid = 1 order by dateofoperation desc ;
    
    SELECT outtime from vehicleparking where slotid= 101 order by intime desc ;
    
    select empid,slotoperationtypeid from slotoperation where slotid = 306 order by dateofoperation desc;
    
    set foreign_key_checks = 0;
    
    select * from slotoperation 
		inner join slotservicetypes on slotservicetypes.servicetypeid = slotoperation.slotoperationtypeid
        inner join parkingslot on slotoperation.slotid=parkingslot.slotid
		inner join floors on parkingslot.floorid = floors.floorid 
        inner join parkingblock on floors.blockid = parkingblock.blockid 
        inner join employee on parkingslot.empid = employee.empid 
		inner join employeetovehicle on employeetovehicle.empid = employee.empid 
		inner join vehicle on vehicle.vehicleid = employeetovehicle.vehicleid order by dateofoperation;