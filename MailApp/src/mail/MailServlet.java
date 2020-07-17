package mail;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MailServlet extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		PrintWriter pw = response.getWriter();
		String sender = request.getParameter("sender mail");
		String getter = request.getParameter("recipient mail");
		// String[] recipients = getter.split(",");
		String subject = request.getParameter("subject mail");
		String body = request.getParameter("body mail");
		// pw.println("Done...");

		boolean result = Mailer_2.send(sender, "VENGAT1999", getter, subject, body);
		if (result)
			pw.print("Mail sent Sucessufully !!!");
		else
			pw.println("Mail not sent.");
	}

}
