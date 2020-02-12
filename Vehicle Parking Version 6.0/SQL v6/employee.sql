create table employee(
	empid int primary key auto_increment,
	ename varchar(30) not null,
    phoneno bigint not null unique,
    mailid varchar(30) not null unique,
    teamname varchar(30) ,
    seatno varchar(20) not null,
    blockname varchar(20) not null,
    constraint unique(seatno,blockname) 
    );
    
    select * from employee ;
    
    alter table employee auto_increment = 1000 ;
    
    
     insert into employee(ename,phoneno,mailid,teamname,seatno,blockname) values ('vengat1','12345110','vengatpy@gmail.com','sdp od','1BR01','tower building') ; 
    
	insert into employee(ename,phoneno,mailid,teamname,seatno,blockname) values ('barath1','12346111','vengatpy217@gmail.com','Im','1BN02','tower building') ; 
	
	insert into employee(ename,phoneno,mailid,teamname,seatno,blockname) values ('santhosh1','12347112','vengatpy22@gmail.com','IM','1BS03','tower building') ; 
    
        
    
    insert into employee(ename,phoneno,mailid,teamname,seatno,blockname) values ('vengat2','123451','vengatpy11@gmail.com','sdp od','2BR01','tower building') ; 
    
	insert into employee(ename,phoneno,mailid,teamname,seatno,blockname) values ('barath2','123461','vengatpy12@gmail.com','Im','2BN02','tower building') ; 
	
	insert into employee(ename,phoneno,mailid,teamname,seatno,blockname) values ('santhosh2','123471','vengatpy13@gmail.com','IM','2BS03','tower building') ; 
    
    
   
    insert into employee(ename,phoneno,mailid,teamname,seatno,blockname) values ('vengat3','123453','vengatpy31@gmail.com','sdp od','3BR01','tower building') ; 
    
	insert into employee(ename,phoneno,mailid,teamname,seatno,blockname) values ('barath3','123463','vengatpy32@gmail.com','Im','3BN02','tower building') ; 
	
	insert into employee(ename,phoneno,mailid,teamname,seatno,blockname) values ('santhosh3','123473','vengatpy33@gmail.com','IM','3BS03','tower building') ; 
    
    
    insert into employee(ename,phoneno,mailid,teamname,seatno,blockname) values ('vengat5','123455','vengatpy51@gmail.com','sdp od','5BR01','tower building') ; 
    
	insert into employee(ename,phoneno,mailid,teamname,seatno,blockname) values ('barath5','123465','vengatpy52@gmail.com','Im','5BN02','tower building') ; 
	
	insert into employee(ename,phoneno,mailid,teamname,seatno,blockname) values ('santhosh5','123475','vengatpy53@gmail.com','IM','5BS03','tower building') ; 
    
    
    insert into employee(ename,phoneno,mailid,teamname,seatno,blockname) values ('vengat4','123454','vengatpy41@gmail.com','sdp od','4BR01','tower building') ; 
    
	insert into employee(ename,phoneno,mailid,teamname,seatno,blockname) values ('barath4','123464','vengatpy42@gmail.com','Im','4BN02','tower building') ; 
	
	insert into employee(ename,phoneno,mailid,teamname,seatno,blockname) values ('santhosh4','123474','vengatpy43@gmail.com','IM','4BS03','tower building') ; 
    
    
    select * from employee ;
    
    set foreign_key_checks = 1;