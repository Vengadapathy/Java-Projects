package Servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import ATM.ATM_Machine_Class;
import Objects.AtmCard;

@WebServlet(description = "ATM Login Page", urlPatterns = { "/Login" })
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		ATM_Machine_Class atm = new ATM_Machine_Class();
		atm.initialize();
		long cardNo = Long.parseLong(request.getParameter("cardno"));
		int pin = Integer.parseInt(request.getParameter("pin"));
		AtmCard atmcard = atm.userAccount(cardNo,pin);
		boolean authenticationStatus = atm.authentication(atmcard);
		try {
			JSONObject json = new JSONObject();
			response.setContentType("appliction/json");
			System.out.println(json);
			if( authenticationStatus ) {
				System.out.println(json);
				request.getSession().setAttribute("atmcard", atmcard);
				response.getWriter().print(json.put("status", true));
				System.out.println(json);
			} else {
				System.out.println(json);
				response.getWriter().print(json.put("status", false));
			}
		} catch (IOException | JSONException e) {
				e.printStackTrace();
		}
		atm.closeConnection();
	}

	@Override
	protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Delete request");
		System.out.println(request.getParameter("process"));
		request.getSession().removeAttribute("atmcard");
		request.getSession().invalidate();
		response.getWriter().print("Logout completed");
	}

}
