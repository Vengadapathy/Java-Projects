create table vehicle(
	vehicleid int primary key auto_increment,
    vehicleno varchar(20) unique not null,
    vehiclename varchar(20) not null ,
	vehicletypeid int not null ,
    vehiclecolour varchar(30) not null,
    CONSTRAINT fk_type foreign key(vehicletypeid) references vehicletypes(vehicletypeid)
    );
    
    insert into vehicle (vehicleno,vehiclename,vehicletypeid,vehiclecolour) values('TN 05 FN 1230','ROYAL ENFEILD',1,'RED'),('TN 15 CD 7410','RENAULT KWID',2,'BLACK');
    
    insert into vehicle (vehicleno,vehiclename,vehicletypeid,vehiclecolour) values('TN 27 MN 1230','PULSAR',1,'BLUE'),('TN 39 AB 7400','INNOVA',2,'WHITE');

    
    select * from vehicle ;

	select * from vehicleparking
		inner join vehicle on vehicle.vehicleid = vehicleparking.vehicleid
        where vehicle.vehicleid = 1 and vehicleparking.outtime is null order by vehicleparking.intime desc limit 1;
    
    select * from vehicleparking where outtime is null and  slotid =101 and vehicleid = 1 ;