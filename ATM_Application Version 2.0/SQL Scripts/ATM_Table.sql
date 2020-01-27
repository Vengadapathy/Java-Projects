
use ATM_Version_1;

CREATE TABLE atm (
    atmid INT not null auto_increment,
	branchCode varchar(20) not null,
    type VARCHAR(20) default 'debit',
    amountRemaining DOUBLE not null,
    location VARCHAR(20) not null,
    status varchar(20) default'active',
	constraint pk_atmid primary key(atmid) 
);

	alter table atm auto_increment = 100 ;
		
    insert into atm (branchCode,type ,amountRemaining ,location,status) values ('CHENNAI100','HYBRID',100000.00,'Chennai','Active');

    insert into atm (branchCode,type ,amountRemaining ,location,status) values ('CHENNAI101','DEBIT',100000.00,'Chennai','Active');
    
    select * from atm ;
