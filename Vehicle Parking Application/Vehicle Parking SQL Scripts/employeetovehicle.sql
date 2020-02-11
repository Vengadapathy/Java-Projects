create table employeetovehicle(
	mappingid int primary key auto_increment,
    empid int not null,
    vehicleid int not null,
    constraint unique key (empid,vehicleid),
    constraint fk_empid foreign key(empid) references employee(empid),
    constraint fk_vehicleid foreign key(vehicleid) references vehicle(vehicleid)
    );
    
    insert into employeetovehicle(empid,vehicleid) values (1000,1),(1000,7);
    
    insert into employeetovehicle(empid,vehicleid) values (1001,2),(1001,8);
    
    insert into employeetovehicle(empid,vehicleid) values (1002,3),(1002,9);
    
    
    insert into employeetovehicle(empid,vehicleid) values (1003,4),(1003,10);
    
    insert into employeetovehicle(empid,vehicleid) values (1004,5),(1004,11);
    
    insert into employeetovehicle(empid,vehicleid) values (1005,6),(1005,12);
    
    select * from employeetovehicle ;
    
    
    insert into employeetovehicle(empid,vehicleid) values (1000,13),(1000,19);
    
    insert into employeetovehicle(empid,vehicleid) values (1001,14),(1001,20);
    
    insert into employeetovehicle(empid,vehicleid) values (1002,15),(1002,21);
    
    
    insert into employeetovehicle(empid,vehicleid) values (1003,16),(1003,22);
    
    insert into employeetovehicle(empid,vehicleid) values (1004,17),(1004,23);
    
    insert into employeetovehicle(empid,vehicleid) values (1005,18),(1005,24);
    
    
    insert into employeetovehicle(empid,vehicleid) values (1005,1) ;
    
    insert into employeetovehicle(empid,vehicleid) values (1004,2) ;
	
    
    insert into employeetovehicle(empid,vehicleid) values (1000,24) ;
    
    insert into employeetovehicle(empid,vehicleid) values (1001,23) ;