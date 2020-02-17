
UPDATE BALANCE QUERY :

	update account set balance = balance + 500 where accountNo = 1234500;
    
    
TRANSACTION ENTRY QUERY :

	insert into transaction ( userid , accountNo , transactionTypeID , amount ,atmid, balance) 
		(select userToAccount.userid ,userToAccount.accountNo,2, 400, 100, account.balance  from userToAccount 
        	inner join account on userToAccount.accountNo = account.accountNo 
       		 where account.accountNo = 1234500 ) ;





TRANSACTION LIST QUERY ( Mini Statement ) :

	select 	user.username , transaction.transactionid , account.accountNo , transaction.amount , transaction.balance , transaction.transactionDate , transactionType.transactionType , atm.atmid , 			atm.location  from transaction 
			inner join account on transaction.accountNo = account.accountNo 
       		inner join user on transaction.userid = user.userid
        	left join atm on transaction.atmid= atm.atmid 
        	inner join transactionType on transaction.transactionTypeID = transactionType.transactionTypeId
        	where account.accountNo = 1234500 order by transaction.transactionid desc;
        
