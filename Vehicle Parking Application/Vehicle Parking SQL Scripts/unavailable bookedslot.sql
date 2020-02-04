create table unavailablebookedslot(
	operationid int primary key auto_increment,
    empid int not null,
    slotoperationid int not null unique,
    slotid int not null,
    constraint fk_empid3 foreign key(empid) references employee(empid),
    constraint fk_slotid3 foreign key(slotid) references parkingslot(slotid),
	constraint fk_slotoperationid1 foreign key(slotoperationid) references slotoperation(slotoperationid)
	);
    
    select * from unavailablebookedslot;
    
    SELECT * from slotoperation where empid = 1 and slotoperationtypeid = 1 order by dateofoperation desc ;
    
    truncate unavailablebookedslot ; 