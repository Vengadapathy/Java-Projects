create table vehicle(
	vehicleno int primary key ,
    vehiclename varchar(20) not null ,
	vehicletypeid int not null,
    vehiclecolourid int not null,
    CONSTRAINT fk_colour foreign key(vehiclecolourid) references vehiclecolours(vehiclecolourid),
    CONSTRAINT fk_type foreign key(vehicletypeid) references vehicletypes(vehicletypeid)
    );
    
    insert into vehicle (vehicleno,vehiclename,vehicletypeid,vehiclecolourid) values(1230,'Bullet',1,1),(7410,'renault kwid',2,2);
    
    select * from vehicle ;