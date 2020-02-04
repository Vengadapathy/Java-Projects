create table slotservicetypes(
	servicetypeid int primary key auto_increment,
    servicetype varchar(20) not null unique
    );
    
    insert into slotservicetypes (servicetype) values ('slot booking'),('slot cancelling') ;
    
    select * from slotservicetypes;