create table vehicletypes(
	vehicletypeid int primary key auto_increment,
    vehicletype varchar(40) unique not null
    );
    
    insert into vehicletypes (vehicletype) values ('TWO WHEELER'),('FOUR WHEELER'),('E-VEHICLE') ;
    
    select * from vehicletypes ;