package otherOperations;

import java.util.Random;
import java.util.Scanner;
public class PIN_Change{
	
	private static String subject = "OTP Authentication Mail";
	private static String bodyContent = " is the OTP generated for the initiation of the PIN Change for your debit card ";
	protected static String to ;
	public PIN_Change(String to){
		PIN_Change.to = to ;
	}
	
	public static void Mailer(String otp) {
		
		String body = otp.concat(bodyContent);
		MailProcess.Mail( to , subject , body );
		
	}
	public boolean is_OTP_Matching()  {
				Integer val = new Random().nextInt(1000000);
				  String otp = val.toString();
				Mailer( otp );
				
				try (Scanner sc = new Scanner(System.in)) {
					System.out.println("Enter the OTP Generated");
					String generatedOTP = sc.next();
					return ( otp.equals(generatedOTP) ? true : false );
				}
		}
}