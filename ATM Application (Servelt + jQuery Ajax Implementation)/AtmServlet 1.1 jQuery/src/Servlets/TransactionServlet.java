package Servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import ATM.ATM_Machine_Class;
//import ATM.InsufficientAmountException;
import Objects.AtmCard;

@WebServlet("/Transaction")
public class TransactionServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ATM_Machine_Class atm =new ATM_Machine_Class();
		atm.initialize();
		AtmCard card = (AtmCard) request.getSession().getAttribute("atmcard");
		HashMap<String,Long> map = atm.getAccountMap();
		map.remove(String.valueOf(card.getAccount().getAccountNo()));
		JSONObject json = new JSONObject(map);
		response.setContentType("appliction/json");
		response.getWriter().print(json);
		atm.closeConnection();
	}

	
	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean status = false ;
		ATM_Machine_Class atm =new ATM_Machine_Class();
		atm.initialize();
		AtmCard atmcard = (AtmCard) request.getSession().getAttribute("atmcard");
		if( atmcard == null) {
			request.getSession().removeAttribute("atmcard");
			response.sendRedirect("LoginPage.html");
		}
		String process = null ;
		double amount = 0.0d ;
		BufferedReader reader = new BufferedReader( request.getReader() );
		StringBuilder sb = new StringBuilder();
		String str = new String();
		if((str = reader.readLine()) != null) {
			sb.append(str);
		}
		try {
			JSONObject json = new JSONObject(sb.toString());
			process = json.getString("process");
			amount = json.getDouble("amount");
			JSONObject responseJson = new JSONObject();
			if(("Fund Transfer").equalsIgnoreCase(process)) {
				int accountNo = Integer.parseInt(json.getString("accountno"));
				//Get AccountNo lists
				status = atm.transaction(atmcard.getAccount(),accountNo,3,"DEBIT",amount);
			} else if(("Deposit").equalsIgnoreCase(process)) {
				status = atm.transaction(atmcard.getAccount(),atmcard.getAccount().getAccountNo(),1,"CREDIT",amount);
			} else if(("Withdraw").equalsIgnoreCase(process)) {
				status = atm.transaction(atmcard.getAccount(),atmcard.getAccount().getAccountNo(),2,"DEBIT",amount);
			}
			responseJson.put("status", status);
			response.setContentType("appliction/json");
			response.getWriter().print(responseJson);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		System.out.println("servlet status "+status);
		atm.closeConnection();
	}

}
