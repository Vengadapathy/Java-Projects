Automated Teller Machine :

Objective :
	To develop a fully operational web application by implementing the business logic of an automated teller machine (ATM).

Technologies used :
	1. Java OOPS
	2. Database : MySql
	3. MVC Architecture (Servlets)
	4. HTML & CSS
 	5. JavaScript - jQuery & Ajax

Functionality :
	1. Cash Deposit
	2. Cash Withdraw
	3. Fund Transfer
	4. Balance Enquiry
	5. Mini Statement
	6. PIN Change

Overview :
	- Every user has a bank account with ATM card and credentials to perform various operation in an ATM.
 	- The user need to provide his/her ATM card number along with 4-digit PIN to login into his/her account.
	- To perform a Transaction a valid money denomiation is to be entered.
	- Fund Transfer can be performed to other accounts available.
	- Balance enquiry is available to check account balance.
	- A detailed information regarding previous transaction can be viewed and a printed reciept will be available upto a limit of 20 transactions.
	- PIN change feature can be used to update 4-digit PIN credentials.

Object models :
	User - contains personal information regarding the user.
	Account - contains data related to user's bank account and user to which the account belong or user logged in.
	ATM Card - contains information related to ATM card and account data if a user logged in.
	ATM Machine - contains detailed information of the ATM machine.
	Transaction - contains neccessary datas to perform a transaction.

Special features :
	- Account model is implemented to different types of account (ie.,SB account,Joint account,Current account)
	- '6-digit OTP' authentication through user linked mail is carried out while changing PIN of Atm card.
	- Printed invoice consists of detailed transaction information.
	- User validation and money denomination validation is carried out before transaction. 



	