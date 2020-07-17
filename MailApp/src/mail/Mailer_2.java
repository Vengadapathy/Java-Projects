package mail;

import java.util.Properties;    
import javax.mail.*;    
import javax.mail.internet.*;    
public class Mailer_2{  
    public static boolean send(String from,String password,String to,String sub,String msg){  
          //Get properties object    
    	System.out.println(from +"  \n"+password+"\n  "+to+"  \n "+sub+"  \n"+msg);
    	          
  		Properties pro = new Properties();
  		pro.put("mail.smtp.host","smtp.gmail.com");  
  		pro.put("mail.smtp.starttls.enable","true");
  		pro.put("mail.smtp.auth","true");
  		pro.put("mail.smtp.port",587);
  		
  		Session session = Session.getDefaultInstance(pro,  new javax.mail.Authenticator() {    
  	           protected PasswordAuthentication getPasswordAuthentication() {    
  	           return new PasswordAuthentication(from,password);  
  	           }    
  	          });    
  		
  		MimeMessage message = new MimeMessage(session);
  		try {
  			message.setFrom(new InternetAddress(from));
  			message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
  			message.setSubject(sub);
  			message.setText(msg);
  		Transport.send(message);
  		System.out.println("Message sent");
  		return true;
  		} catch (MessagingException e) {
  			e.printStackTrace();
  			return false; 
  		}
		   
    }  
    public static void main(String[] args) {
    	send("vengatpy@gmail.com", "VENGAT1999", "vengatpy@gmail.com", "vengatpy@gmail.com", "vengatpy@gmail.com");
    }
}
