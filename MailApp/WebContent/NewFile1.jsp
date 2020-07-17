<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<form action="MailServlet" method="post">
				<p>Sender Mail Address :<br> <input type="text" name="sender mail"><br>
				<p>Recipient Mail Address : <br> <input type="text" name="recipient mail"><br>
				<p>Subject of the mail :<br> <input type ="text" name="subject mail"><br>
				<p>Body of the mail : <br><input type="text"  name="body mail"><br><br>
				<input type="submit" value="SEND" >
		</form>
</body>
</html>