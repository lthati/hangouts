package api;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class getEvents
 */
@WebServlet("/events")
public class getEvents extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public getEvents() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 *return a json
	 */
    //http://localhost:8080/Hangouts/events?username=ashlhu
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		JSONArray array = new JSONArray();
	   	 try {
	   		 if (request.getParameterMap().containsKey("username")) {
	   			 String userName = request.getParameter("username");
	  
	   			 // return some fake restaurants
	   			 array.put(new JSONObject().put("event_name", "happy hour"));
	   			 array.put(new JSONObject().put("event_name", "beer bash"));
	   		 }
	   	 } catch (JSONException e) {
	   		 e.printStackTrace();
	   	 }
	   	 RpcParser.writeOutput(response, array);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
