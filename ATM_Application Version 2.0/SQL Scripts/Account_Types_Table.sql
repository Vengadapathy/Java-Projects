
use ATM_Version_1 ;

	CREATE table accountType(
	accountTypeId int primary key auto_increment ,
    	accountType varchar(20) not null  unique
    	);
    

insert into accountType (accountType) values ('CURRENT ACCOUNT'),('JOINT ACCOUNT') ,('SAVINGS');

SELECT * from accountType ;
