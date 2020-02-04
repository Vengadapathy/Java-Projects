create table employee(
	empid int primary key auto_increment,
	ename varchar(30) not null,
    phoneno bigint not null unique,
    mailid varchar(30) not null unique,
    teamname varchar(30) ,
    seatno varchar(20) not null,
    blockname varchar(20) not null
    );
    alter table employee auto_increment = 100 ;
    
    insert into employee(ename,phoneno,mailid,teamname,seatno,blockname) values ('vengat','12345','vengatpy@gmail.com','sdp od','12BR03','tower building') ; 
    
	insert into employee(ename,phoneno,mailid,teamname,seatno,blockname) values ('barath','12346','vengatpy1@gmail.com','Im','5BN03','tower building') ; 
	
	insert into employee(ename,phoneno,mailid,teamname,seatno,blockname) values ('santhosh','12347','vengatpy2@gmail.com','IM','5BS03','tower building') ; 
    
    select * from employee ;