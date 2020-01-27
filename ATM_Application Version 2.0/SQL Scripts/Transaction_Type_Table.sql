
	use ATM_Version_1 ;

	CREATE table transactionType(
	    transactionTypeId int primary key auto_increment ,
	    transactionType varchar(20) not null  unique
	    );
    
    insert into transactionType (transactionType)values('CREDIT'),('DEBIT');
    
    select * from transactionType;
