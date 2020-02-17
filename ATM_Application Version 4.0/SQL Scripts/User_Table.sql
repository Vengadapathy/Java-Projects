use ATM_Version_1 ;

create table user(
	userid int not null auto_increment,
    username varchar(50) not null ,
    phone_number varchar(10) unique ,
    mailid varchar (50) not null unique,
    address varchar (100) not null ,
    CONSTRAINT pk_userid primary key(userid)
) ;

alter table user auto_increment = 1000 ;

insert into user (username,phone_number,mailid,address) values ('USER_1','90012300','vengatpy@gmail.com','Pondy') , ('USER_2','90012301','vengatpy2@gmail.com','Pondy') ;

select * from user ;