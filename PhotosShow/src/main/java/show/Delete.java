package show;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.catalina.startup.FailedContext;

/**
 * Servlet implementation class Delete
 */
public class Delete extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Delete() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	PrintWriter out = response.getWriter();

		JFrame  f=new JFrame();   
	    String id=JOptionPane.showInputDialog(f,"Enter the ID : ");   
	    
	    Cookie ck[] = request.getCookies();
	    String userid1 = "";
	    for(Cookie cookie : ck ){
	    	if("userid".equals(cookie.getName())){
	    		
	    		userid1 = cookie.getValue();
	    	}
	    }
	    
	   int userid = (Integer.parseInt(userid1)-12345)/12345;
	    
	  if( checkId(id,userid)) {
	
	  
	    
		try {
			
			if(id==null) {
				response.sendRedirect("index.html");
			}else {
			
			 int id1 = Integer.parseInt(id);
			 
			String path = getPath(id1);
			 
			 deleteFromPath(path);
			 
			 
			 
			 if(deleteData(id1)) {
				response.sendRedirect("index.html");
			 }else {
			 	response.sendRedirect("index.html");
			 }
			
			}
		} catch (Exception e) {
              
        
//		        log.error(formatter.format(date)+" ERROR (Delete  - while deleting data (id is not found))  : ");
		    
			 
			 out.println("<script type=\"text/javascript\">");
		       out.println("alert('Please give Correct Number...');");
		       out.println("window.location.href=\"index.html\"");
		       out.println("</script>");

			
			
		}
	  }else {
		  System.out.println("Failed.................!");
		  out.println("<script>");
	       out.println("alert('WARNING!, Dont try to delete others posts...');");
	       out.println("window.location.href=\"index.html\"");		      
	       out.println("</script>");
//		  response.sendRedirect("index.html");
	  }
	}

	private void deleteFromPath(String path) {
		// TODO Auto-generated method stub

		
		String[] arr = path.split("pics");
		
		path = (arr[1].substring(1,arr[1].length()));
		
		File myObj = new File("/home/venkat-zstk269/eclipse-workspaceUpdated/PhotosShow/src/main/webapp/pics/"+path); 
	    if (myObj.delete()) { 
	      System.out.println("Deleted the file: " + myObj.getName());
	    } else {
	      System.out.println("Failed to delete the file.");
	      
	    } 
		
		
		
		
	}

	private String getPath(int id1) {
		
		String path="";
		
		Connection con = null;
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");	    
		
	     con= DriverManager.getConnection("jdbc:mysql://localhost:3306/photoshow","root","");
	     
	    
		
	     PreparedStatement ps = con.prepareStatement("select path from datas where generatedId = "+id1);
	     
	     ResultSet rs = ps.executeQuery();
	     
	    while(rs.next()) {
	    	path = rs.getString(1);
	    }
	    
	    return path;
	     
		}catch (Exception e) {
	
			System.out.println("error........while checking..");
			
		}
		
		return null;
		// TODO Auto-generated method stub
		
	}

	private  boolean checkId(String id, int userid) {
		
		Connection con = null;
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");	    
		
	     con= DriverManager.getConnection("jdbc:mysql://localhost:3306/photoshow","root","");
	     
	    
		
	     PreparedStatement ps = con.prepareStatement("select * from datas where generatedId = "+id+" and id="+userid);
	     
	     ResultSet rs = ps.executeQuery();
	     
	    while(rs.next()) {
	    	return true;
	    }
	    
	     
		}catch (Exception e) {
	
			System.out.println("error........while checking..");
			return false;
		}
		return false;
		
		
	}

	private boolean deleteData(int id1) throws Exception {
		Connection con = null;
		try {
		Class.forName("com.mysql.cj.jdbc.Driver");	    
		
	     con= DriverManager.getConnection("jdbc:mysql://localhost:3306/photoshow","root","");
	     
	    
		
	     PreparedStatement ps = con.prepareStatement("delete from datas where generatedId = "+id1);
	     
	    if( ps.executeUpdate()==1) {
	    	return true;
	    }
	     
		}catch (Exception e) {
	
			System.out.println("error........while deleting..");
			return false;
		}finally {
			con.close();
		}
		return false;
	}
	
	
	
	
	
	
	
	
	

}
