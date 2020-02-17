use ATM_Version_1 ;

CREATE TABLE transaction (
    transactionid INT not null auto_increment  ,
    userid int NOT NULL,
    accountNo BIGINT NOT NULL ,
    transactionDate DATETIME not null default now(),
    transactionTypeID int not null,
    amount DOUBLE not null ,
    atmid INT ,
    balance double default 0,
    constraint pk_TransactionId PRIMARY KEY (transactionid) ,
    constraint fk_atmid FOREIGN KEY (atmid) REFERENCES atm (atmid),
    constraint fk_userid FOREIGN KEY (userid) REFERENCES user (userid),
    constraint fk_accountNo FOREIGN KEY (accountNo) REFERENCES account (accountNo),
    constraint fk_transactionType foreign key(transactionTypeID) references transactionType(transactionTypeId)
);
	
    alter table transaction auto_increment = 10001 ;
    
    select * from transaction ;
		
	truncate transaction ;