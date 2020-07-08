package Servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONValue;

import ATM.ATM_Machine_Class;
import Objects.AtmCard;
import otherOperations.PIN_Change;


@WebServlet("/Other")
public class OtherOperations extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ATM_Machine_Class atm = new ATM_Machine_Class();
		atm.initialize();
		AtmCard atmcard = (AtmCard)request.getSession().getAttribute("atmcard");
		response.setContentType("appliction/json");
		try {
			String process = request.getParameter("event");
			
			if(process.equalsIgnoreCase("balance")) {
				JSONObject responseJson = new JSONObject();
//				System.out.println( atmcard.getAccount().getBalance());
				responseJson.put("balance", String.format("%.2f",atmcard.getAccount().getBalance()));
//				System.out.println(responseJson.get("balance"));
				response.getWriter().print(responseJson);
			} else if(process.equalsIgnoreCase("statement")) {
				ArrayList<LinkedHashMap<String,String>> list = atm.miniStatement(atmcard.getAccount());
				ArrayList<String> arr = new ArrayList<String>();
				for(int index=0 ; index < list.size() ;index++) {
					String jsonText = JSONValue.toJSONString(list.get(index));
					arr.add(index, jsonText);
				}
				response.getWriter().print(arr);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		atm.closeConnection();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ATM_Machine_Class atm = new ATM_Machine_Class();
		atm.initialize();
		AtmCard atmcard = (AtmCard) request.getSession().getAttribute("atmcard");
		String data = request.getParameter("otp");
		JSONObject responseJson = new JSONObject();
		try {
			if( data == null ) {
				int otp = atm.generateOtp();
				PIN_Change pin = new PIN_Change(atmcard.getAccount().getUser().getMailID());
				pin.Mailer(otp);
				request.getSession().setAttribute("otp",otp);
				responseJson.put("OtpStatus", true);
			} else {
				int otp = (int) request.getSession().getAttribute("otp");
				responseJson.put("status", atm.checkOtp(otp,Integer.parseInt(data)));
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		response.setContentType("appliction/json");
		response.getWriter().print(responseJson);
		
		atm.closeConnection();
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ATM_Machine_Class atm = new ATM_Machine_Class();
		atm.initialize();
		AtmCard atmcard = (AtmCard) request.getSession().getAttribute("atmcard");
		BufferedReader reader = new BufferedReader( request.getReader() );
		StringBuilder sb = new StringBuilder();
		String str = new String();
		JSONObject responseJson = new JSONObject();
		try {
			if((str = reader.readLine()) != null) {
				sb.append(str);
				JSONObject json = new JSONObject(sb.toString());
				responseJson.put("status", atm.setNewPin(atmcard.getCardNo(),json.getInt("pin")) );
			}
		} catch (JSONException e) {
				e.printStackTrace();
		}
		response.setContentType("appliction/json");
		response.getWriter().print(responseJson);
		atm.closeConnection();
	}
	
}
