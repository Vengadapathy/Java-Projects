create table employeetovehicle(
	mappingid int primary key auto_increment,
    empid int not null,
    vehicleid int not null,
    constraint unique key (empid,vehicleid),
    constraint fk_empid foreign key(empid) references employee(empid),
    constraint fk_vehicleid foreign key(vehicleid) references vehicle(vehicleid)
    );
    
    insert into employeetovehicle(empid,vehicleid) values (1000,1),(1001,2);
    
    insert into employeetovehicle(empid,vehicleid) values (1002,3);
    
    select * from employeetovehicle ;
    
    