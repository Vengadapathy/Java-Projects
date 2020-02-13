create table slotoperation(
	slotoperationid int primary key auto_increment,
    empid int not null,
    slotid int not null,
    slotoperationtypeid int not null,
    bookingtime datetime default now(),
    cancellingtime datetime ,
	constraint fk_empid2 foreign key(empid) references employee(empid),
    constraint fk_slotid foreign key(slotid) references parkingslot(slotid)
	);
	
    truncate slotoperation ;
    
    alter table slotoperation add column empid int;
    alter table slotoperation add constraint fk_empid6 foreign key(empid) references employee(empid);
    alter table slotoperation change column empid empid int not null;
		
    alter table slotoperation drop column slotoperationtypeid  ; 
    
    alter table slotoperation drop foreign key fk_typeid1;
    
    insert into slotoperation(empid,slotid) values (1000,101);
    select * from slotoperation ;
    SELECT * from slotoperation where empid = 1 and slotoperationtypeid = 1 order by dateofoperation desc ;
    
    SELECT outtime from vehicleparking where slotid= 101 order by intime desc ;
    
    select empid,slotoperationtypeid from slotoperation where slotid = 306 order by dateofoperation desc;
    
    set foreign_key_checks = 0;
    
    select * from slotoperation 
		inner join slotservicetypes on slotservicetypes.servicetypeid = slotoperation.slotoperationtypeid
        inner join parkingslot on slotoperation.slotid=parkingslot.slotid
		inner join floors on parkingslot.floorid = floors.floorid 
        inner join parkingblock on floors.blockid = parkingblock.blockid 
        inner join employee on parkingslot.empid = employee.empid 
		inner join employeetovehicle on employeetovehicle.empid = employee.empid 
		inner join vehicle on vehicle.vehicleid = employeetovehicle.vehicleid order by dateofoperation;
        
        
	update slotoperation set slotcancellingtime = now() where vehicleid = 5 and slotid = 101 and slotcancellingtime is  null ;
    
    update parkingslot set vehicleid = null where slotid = 101 ;
    
    select * from slotoperation where vehicleid = 5  and slotid = 101 and slotcancellingtime is  null ;
    
    update vehicleparking set outtime = now() where vehicleid = ?  and slotid = ?  and outtime is  null ;
    
    select * from vehicleparking  where vehicleid =2   and slotid =103   and outtime is  null ;
    