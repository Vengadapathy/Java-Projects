create table slotservicetypes(
	servicetypeid int primary key auto_increment,
    servicetype varchar(20) not null unique
    );
    
    insert into slotservicetypes (servicetype) values ('SLOT BOOKING'),('SLOT CANCELLING') ;
    
    select * from slotservicetypes;
    
    set foreign_key_checks = 1;