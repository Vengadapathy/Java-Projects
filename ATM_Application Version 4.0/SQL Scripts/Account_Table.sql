use ATM_Version_2 ;

CREATE TABLE account (
    accountNo BIGINT(20)  NOT NULL auto_increment,
    pin INT NOT NULL default 1000 ,
    balance DOUBLE default 0.0,
    accountTypeID int not Null , 
    CONSTRAINT pk_accountNo primary key(accountNo),
    CONSTRAINT fk_accounttype foreign key(accountTypeID) references accountType(accountTypeId)
);

alter table account auto_increment = 1234500 ;

insert into account (PIN,balance,accountTypeID) values (1000,0,3);

insert into account (PIN,balance,accountTypeID) values (1001,0,3);

select * from account ;

update account set balance =0 ;

describe account;