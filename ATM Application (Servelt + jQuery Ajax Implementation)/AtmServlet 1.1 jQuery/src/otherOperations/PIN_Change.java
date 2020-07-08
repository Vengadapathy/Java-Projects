package otherOperations;

public class PIN_Change{
	
	private static String subject = "OTP Authentication Mail";
	private static String bodyContent = " is the OTP generated for the initiation of the PIN Change for your debit card ";
	protected static String to ;
	public PIN_Change(String to){
		PIN_Change.to = to ;
	}
	
	public void Mailer(int val) {
		String otp = Integer.toString(val);
		String body = otp.concat(bodyContent);
		MailProcess.Mail( to , subject , body );
	}
	
	public static void main(String[] args) {
		PIN_Change p = new PIN_Change("vengatpy@gmail.com");
		p.Mailer(123456);
	}

}