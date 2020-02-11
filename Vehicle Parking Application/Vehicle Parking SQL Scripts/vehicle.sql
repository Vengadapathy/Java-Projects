create table vehicle(
	vehicleid int primary key auto_increment,
    vehicleno varchar(20) unique not null,
    vehiclename varchar(20) not null ,
	vehicletypeid int not null ,
    vehiclecolour varchar(30) not null,
    CONSTRAINT fk_type foreign key(vehicletypeid) references vehicletypes(vehicletypeid)
    );
    
    insert into vehicle (vehicleno,vehiclename,vehicletypeid,vehiclecolour) values('TN 03 B 5','HONDA7',1,'BLACK');
    ('TN 15 CD 7410','RENAULT KWID',2,'BLACK');
    
    insert into vehicle (vehicleno,vehiclename,vehicletypeid,vehiclecolour) values('TN 01 NN 1210','ALTO',2,'YELLOW');
    ,('TN 39 AB 7400','INNOVA',2,'WHITE');

    
    select * from vehicle ;

	select * from vehicleparking
		inner join vehicle on vehicle.vehicleid = vehicleparking.vehicleid
        where vehicle.vehicleid = 1 and vehicleparking.outtime is null order by vehicleparking.intime desc limit 1;
    
    select * from vehicleparking where outtime is null and  slotid =101 and vehicleid = 1 ;
    
    select * from vehicleparking 
			inner join vehicle on vehicle.vehicleid = vehicleparking.vehicleid
		where vehicle.vehicleid = ? and vehicleparking.outtime is null order by vehicleparking.intime desc limit 1 ;
        
        
        
        
    insert into vehicle (vehicleno,vehiclename,vehicletypeid,vehiclecolour) values('TN 01 A 1000','HONDA',1,'BLACK'), ('TN 01 A 1001','HERO',1,'BLUE');
    
	insert into vehicle (vehicleno,vehiclename,vehicletypeid,vehiclecolour) values('TN 01 A 1002','TVS',1,'WHITE'), ('TN 01 A 1003','RE',1,'RED');
    
    insert into vehicle (vehicleno,vehiclename,vehicletypeid,vehiclecolour) values('TN 01 A 1004','YAMAHA',1,'GOLD'), ('TN 01 A 1005','SUZUKI',1,'YELLOW');
    
    
    insert into vehicle (vehicleno,vehiclename,vehicletypeid,vehiclecolour) values('TN 01 B 1000','HONDA',1,'BLACK'), ('TN 01 B 1001','HERO',1,'GREY');
    
	insert into vehicle (vehicleno,vehiclename,vehicletypeid,vehiclecolour) values('TN 01 B 1002','TVS',1,'GOLD'), ('TN 01 B 1003','RE',1,'GREEN');
    
    insert into vehicle (vehicleno,vehiclename,vehicletypeid,vehiclecolour) values('TN 01 B 1004','YAMAHA',1,'WHITE'), ('TN 01 B 1005','SUZUKI',1,'BLUE');
    
    
    
    
    set foreign_key_checks = 1 ;

	insert into vehicle (vehicleno,vehiclename,vehicletypeid,vehiclecolour) values('TN 02 A 1000','INNOVA',2,'BLACK'), ('TN 02 A 1001','NISSAN',2,'BLUE');
    
	insert into vehicle (vehicleno,vehiclename,vehicletypeid,vehiclecolour) values('TN 02 A 1002','RENAULT',2,'WHITE'), ('TN 02 A 1003','BMW',2,'RED');
    
    insert into vehicle (vehicleno,vehiclename,vehicletypeid,vehiclecolour) values('TN 02 A 1004','ALTO',2,'GOLD'), ('TN 02 A 1005','BENZ',2,'YELLOW');
    
    
    insert into vehicle (vehicleno,vehiclename,vehicletypeid,vehiclecolour) values('TN 02 B 1000','SANTRO',2,'BLACK'), ('TN 02 B 1001','INDIGO',2,'GREY');
    
	insert into vehicle (vehicleno,vehiclename,vehicletypeid,vehiclecolour) values('TN 02 B 1002','BOLERO',2,'GOLD'), ('TN 02 B 1003','SWIFT DESIRE',2,'GREEN');
    
    insert into vehicle (vehicleno,vehiclename,vehicletypeid,vehiclecolour) values('TN 02 B 1004','RENAULT',2,'WHITE'), ('TN 02 B 1005','MARUTHI SUZUKI',2,'BLUE');
    






    
    
    insert into vehicle (vehicleno,vehiclename,vehicletypeid,vehiclecolour) values('TN 01 NN 1210','ALTO',2,'YELLOW') ,('TN 39 AB 7400','INNOVA',2,'WHITE');    