import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
response.setContentType("text/html");  
PrintWriter out=response.getWriter();  
String name=request.getParameter("name");  
String password=request.getParameter("password");  
if(password.equals("1234")){
	out.print("Hi "+name+",");  
	out.print("successfully logged in!\n"); 
	out.print("EMPLOYEE DATA \n"); 
	//request.getRequestDispatcher("DatabaseAccess.java").include(request, response);  

HttpSession session=request.getSession();  
session.setAttribute("name",name);
	response.setContentType("application/Json");
	response.setCharacterEncoding("UTF-8");          
if(session!=null){
response.setContentType("application/Json");
response.setCharacterEncoding("UTF-8");
Connection c = null;
Statement s = null;
ResultSet r = null;
PrintWriter pw = response.getWriter();
try {
	Class.forName("com.mysql.cj.jdbc.Driver");
	c = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee", "root", "Current-Root-Password");
	s = c.createStatement();
	r = s.executeQuery("select * from employee");

	JSONArray jsonr = new JSONArray();    
	while(r.next()) {
		JSONObject jo = new JSONObject();
		
		jo.put("empl_id", r.getLong("empl_id"));
		jo.put("id", r.getLong("id"));
		jo.put("empl_name", r.getString("empl_name"));
		jo.put("phone_number", r.getLong("phone_number"));
		jo.put("place", r.getString("place"));
		jo.put("role", r.getString("role"));
		jsonr.add(jo);
		      }
	
	pw.println(jsonr);
	JSONObject jo1 = new JSONObject();
	jo1.put("Employee_Data", jsonr);
     FileWriter file = new FileWriter("C:\\Users\\91965\\eclipse-workspace\\EmployeeManagementSystem\\employeeData.json");
     file.write(jo1.toJSONString());
     file.close();
     System.out.println("JSON FILE FOR EMPLOYEE DATA CREATED!");
}
catch (Exception e) {
	e.printStackTrace();
    out.close();  
}}

else {out.print("You ar not logged in");}

}


else{  
out.print("sorry, username or password error!");  
request.getRequestDispatcher("login.html").include(request, response);  
}  

out.close();  
}}  