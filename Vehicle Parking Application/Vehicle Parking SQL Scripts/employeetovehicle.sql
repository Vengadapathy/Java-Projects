create table employeetovehicle(
	mappingid int primary key auto_increment,
    empid int not null,
    vehicleno int not null,
    constraint unique key (empid,vehicleno),
    constraint fk_empid foreign key(empid) references employee(empid),
    constraint fk_vehicleid foreign key(vehicleno) references vehicle(vehicleno)
    );
    
    insert into employeetovehicle(empid,vehicleno) values (1,1230);,(5,5410);
    
    insert into employeetovehicle(empid,vehicleno) values (6,7410);
    
    select * from employeetovehicle ;
    
    