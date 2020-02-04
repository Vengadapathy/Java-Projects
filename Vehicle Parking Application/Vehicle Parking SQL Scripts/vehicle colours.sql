create table vehiclecolours(
	vehiclecolourid int primary key auto_increment,
    vehiclecolour varchar(30) not null unique
    );
    
    insert into vehiclecolours(vehiclecolour) values ('red'),('green'),('black'),('blue');
    
    select * from vehiclecolours ;