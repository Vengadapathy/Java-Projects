package operations;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMail {
//	private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
//	private static LocalDateTime now = LocalDateTime.now();
//	private static String time = now.format(dtf);
//	
	public static void mail ( String to , String sub ,  String body ) {
		
		String from = "bank.atm.info@gmail.com";
		Properties pro = new Properties();
		pro.put("mail.smtp.host","smtp.gmail.com");  
		pro.put("mail.smtp.starttls.enable","true");
		pro.put("mail.smtp.auth","true");
		pro.put("mail.smtp.port",587);
		
		Session session = Session.getDefaultInstance(pro,  new javax.mail.Authenticator() {    
	           protected PasswordAuthentication getPasswordAuthentication() {    
	           return new PasswordAuthentication(from,"PUDUCHERRY");  
	           }    
	          });    
		
		MimeMessage msg = new MimeMessage(session);
		try {
			msg.setFrom(new InternetAddress(from));
			msg.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
			msg.getSentDate();
			msg.setSubject(sub);
			msg.setText(body);
		Transport.send(msg);
		//System.out.println("Message sent");
		} catch (MessagingException e) {
			e.printStackTrace();
		}          
	}
}
