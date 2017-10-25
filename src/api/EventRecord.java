package api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class EventRecord
 */
@WebServlet("/record")
public class EventRecord extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EventRecord() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 *Post an new event
	 *Post url : http://localhost:8080/Hangouts/record
	 *json format : {'username':'ashlhu', 'event_to_add':['beer bash', 'team lunch']}
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 try {
	   		 JSONObject input = RpcParser.parseInput(request);
	   		 if (input.has("username") && input.has("event_to_add")) {
	   			 String userName = (String) input.get("username");
	   			 JSONArray array = (JSONArray) input.get("event_to_add");
	   			 List<String> EventsList = new ArrayList<>();
	   			 for (int i = 0; i < array.length(); i++) {
	   				 String businessId = (String) array.get(i);
	   				EventsList.add(businessId);
	   			 }
	   			 RpcParser.writeOutput(response, new JSONObject().put("status", "OK"));
	   		 } else {
	   			 RpcParser.writeOutput(response, new JSONObject().put("status", "InvalidParameter"));
	   		 }
	   	 } catch (JSONException e) {
	   		 e.printStackTrace();
	   	 }

	}

}
