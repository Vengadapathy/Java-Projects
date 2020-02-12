create table vehicletypes(
	vehicletypeid int primary key auto_increment,
    vehicletype varchar(30) unique not null
    );
    
    insert into vehicletypes(vehicletype) values ('two wheeler'),('four wheeler'),('e-vehicle') ;
    
    set foreign_key_checks = 1;
    
    select * from vehicletypes ;