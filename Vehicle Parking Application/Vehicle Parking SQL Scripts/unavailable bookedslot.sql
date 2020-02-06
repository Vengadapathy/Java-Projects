create table unavailablebookedslot(
	operationid int primary key auto_increment,
    empid int not null,
    slotid int not null,
    dateandtime datetime default now(),
    constraint fk_empid3 foreign key(empid) references employee(empid),
    constraint fk_slotid3 foreign key(slotid) references parkingslot(slotid)
	);
    
    select * from unavailablebookedslot;
    
    SELECT * from slotoperation where empid = 1 and slotoperationtypeid = 1 order by dateofoperation desc ;
    
    truncate unavailablebookedslot ; 
    
	select * from vehicleparking 
			inner join parkingslot on vehicleparking.slotid = parkingslot.slotid 
            where vehicleparking.slotid = 105 and vehicleparking.outtime is null order by intime desc ;
	
    delete from vehicleparking ;
    
    update vehicleparking set outtime = now() ;
	
    insert into unavailablebookedslot (empid,slotid) values (1002,102);
    
    select * from unavailablebookedslot
		inner join vehicleparking on vehicleparking.slotid = unavailablebookedslot.slotid
        inner join vehicle on vehicle.vehicleid = vehicleparking.vehicleid
		inner join employeetovehicle on vehicle.vehicleid = employeetovehicle.vehicleid
        inner join employee on employeetovehicle.empid = employee.empid
        where unavailablebookedslot.slotid = 102 and vehicleparking.outtime is null ;