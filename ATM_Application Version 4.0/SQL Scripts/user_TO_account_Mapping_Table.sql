use ATM_Version_1 ;

create table userToAccount (
	primarykeyID int primary key auto_increment,
    userid int not null,
    accountNo bigint not null,
    CONSTRAINT fk_useridd foreign key(userid) references user(userid),
    CONSTRAINT fk_accountNooo foreign key(accountNo) references account(accountNo),
    CONSTRAINT uk_useraccount unique key(userid,accountNo)
    );
    
alter table userToAccount auto_increment = 12300 ;

insert into userToAccount (userid , accountNo) values (1000,1234500),(1001,1234501); 

select * from userToAccount ;