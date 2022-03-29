package show;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import servlet.Register;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.json.simple.JSONObject;

/**
 * Servlet implementation class Fetch
 */
public class Fetch extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Fetch() {
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
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Logger log=Logger.getLogger(Register.class);
		 String log4jConfPath = "/home/venkat-zstk269/eclipse-workspaceUpdated/PhotosShow/src/main/java/log4j.properties";
	        PropertyConfigurator.configure(log4jConfPath);
	        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	       
		PrintWriter out = response.getWriter();
		List<JSONObject> j1;
		try {
		 	j1=fetch(response);
			
			
			out.write(j1.toString());
			out.flush();
			out.close();
		} catch (Exception e) {
			JSONObject j2=new JSONObject();
			j2.put("status", "failed");
			out.flush();
			out.close();
			out.write(j2.toJSONString());
			// TODO Auto-generated catch block
			 Date date = new Date();  	        
		        log.error(formatter.format(date)+" ERROR (Fetch.java- error in json creating file)  : ");
		    
		}
		
		
	}
	
	private List<JSONObject> fetch(HttpServletResponse response) throws Exception {
		List<JSONObject> ljs = new ArrayList();
		     
				
				
				
				Class.forName("com.mysql.cj.jdbc.Driver");	    

				@SuppressWarnings("unchecked")
				Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/photoshow","root","");

				PreparedStatement ps = con.prepareStatement("select * from datas");

				
				ResultSet rs =   ps.executeQuery();

		         while(rs.next()) {
		        	  JSONObject jo = new JSONObject();
		        	 jo.put("id",rs.getString(1) );
		        	 jo.put("generatedid",rs.getString(2) );
		        	 jo.put("name",rs.getString(3) );
		        	 jo.put("content",rs.getString(4) );
		        	 jo.put("date",rs.getString(5) );
		        	 jo.put("path",rs.getString(6) );
		        	 
		        	 ljs.add(jo);
		         }

				
				return ljs;
			}

}
