
/*UPDATE BALANCE QUERY :*/

	update account set balance = balance + 500 where accountNo = 1234500;
    
    
/*TRANSACTION ENTRY QUERY :*/

	insert into transaction ( userid , accountNo , transactionTypeID , amount ,atmid, balance) 
		(select userToAccount.userid ,userToAccount.accountNo,2, 400, 100, account.balance  from userToAccount 
        inner join account on userToAccount.accountNo = account.accountNo 
        where account.accountNo = 1234500 ) ;


/*TRANSACTION LIST QUERY ( Mini Statement ) :*/

	select 	user.username, transaction.transactionid , account.accountNo, transaction.amount,transaction.balance, transaction.transactionDate , transactionType.transactionType, atm.atmid , atm.location  from transaction 
		inner join account on transaction.accountNo = account.accountNo 
        inner join user on transaction.userid = user.userid
        left join atm on transaction.atmid= atm.atmid 
        inner join transactionType on transaction.transactionTypeID = transactionType.transactionTypeId
        where account.accountNo = 1234500 order by transaction.transactionid desc;
        
        
	insert into transaction (userid , accountNo ,transactionProcessID ,transactionTypeID, transactionModeID,recipientAccountNo, amount , balance) 
							( select userToAccount.userid ,userToAccount.accountNo , 3 , 2  , 1 , userToAccount.accountNo , 900   , account.balance  from userToAccount 
							 inner join account on userToAccount.accountNo = account.accountNo 
                             inner join user on usertoaccount.userid= user.userid
							 where account.accountNo = 1234500 and user.userid = 1000 ) ;
                             
  	insert into transaction (userid) values (10) ;

select * from transaction ;

 
 insert into transaction (userid , accountNo ,transactionProcessID ,transactionTypeID, transactionModeID,recipientAccountNo, amount , balance) ( select userToAccount.userid ,userToAccount.accountNo , 3 , 2  , 1 , 1234502 , 100.0   , account.balance  from userToAccount 
 inner join account on userToAccount.accountNo = account.accountNo 
 inner join user on usertoaccount.userid= user.userid
 where account.accountNo = 1234500 and user.userid = 1000 ) ;
 
 insert into transaction (userid , accountNo ,transactionProcessID ,transactionTypeID, transactionModeID,recipientAccountNo, amount , balance) ( select 1000 ,account.accountNo , 3 , 1  , 1 , 1234500 , 100.0   , (select balance from account where accountNo = 1234502)+100.0   from account 
 where account.accountNo = 1234502  ) ;
 
 SELECT user.username, transaction.transactionid , transaction.accountNo,transaction.recipientAccountNo, transaction.amount,transaction.balance, transaction.transactionDate ,transactionprocess.transactionProcess ,transactionType.transactionType, transactionModes.transactionMode , CASE WHEN atm.atmid IS NOT NULL THEN atm.atmid ELSE mobile.mobileID END AS TransactionModeID, IFNULL(atm.location , 'N/A') as location from transaction  
							left join transactiontoatm on transaction.transactionid=transactiontoatm.transactionid   
							left join transactiontomobile on transaction.transactionid=transactiontomobile.transactionid  
						    inner join transactionType on transaction.transactionTypeID = transactionType.transactionTypeID  
						    inner join user on transaction.userid= user.userid   
                            left join atm on atm.atmid = transactiontoatm.atmid   
						    left join mobile on mobile.mobileID = transactiontomobile.mobileid   
						    inner join transactionModes on transactionModes.transactionModeID = transaction.transactionModeID  
                            inner join transactionprocess on transaction.transactionProcessID = transactionprocess.transactionProcessID
						    where ( transaction.accountNo =  1234502 and transaction.transactionTypeID = 2 ) or (transaction.recipientAccountNo = 1234502 and transaction.transactionTypeID = 1)  order by transaction.transactionDate desc ;  
                            

