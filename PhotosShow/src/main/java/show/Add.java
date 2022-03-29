package show;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;



/**
 * Servlet implementation class Add
 */

@MultipartConfig
public class Add extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public Add() {
        super();
        // TODO Auto-generated constructor stub
    }


	@SuppressWarnings("unlikely-arg-type")
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		PrintWriter out = response.getWriter();
		Cookie ck[] = request.getCookies();
				String userid = "123";
		for(Cookie cookie : ck ){
			
			if("userid".equals(cookie.getName())){
				
				userid = cookie.getValue();
			}
		}
		
		
		int id = ((Integer.parseInt(userid))-12345)/12345;
		
		
		
		
		String content = request.getParameter("content");
		final Part filePart = request.getPart("photo"); 
		System.out.println(filePart);
		 String fileName="";
			try {
				fileName = getFileName(filePart);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		if(content.equals("") || filePart.equals("") || fileName.equals("")) {
			response.sendRedirect("add.html");
		}else {
		
		
		
		LocalDateTime myDateObj = LocalDateTime.now();
		 DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

		    String date = myDateObj.format(myFormatObj);
		
		
		
	        
	        
         
	     
	        
	        
	        String name="";
			try {
				name = getName(id);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        String path ="";
	         
	        try {
				path = uploadIntoDatas(request,filePart,id,content,fileName);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        System.out.println(id+" "+name+" "+content+" "+date + "   "+path);
		       
	        
	        try {
				if(upload(id,name,content,date,path)==1) {
					response.sendRedirect("index.html");
				}else {
					response.sendRedirect("add.html");
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}  
	         
	}


	
static String uploadIntoDatas(HttpServletRequest request, Part filePart, int id, String content, String fileName) throws Exception {

	
	
	
	String arr[] = fileName.split("\\."); 
	System.out.println(arr[arr.length-1]);
      
    OutputStream otpStream = null;  
    InputStream iptStream = null;  
//    final PrintWriter writer = response.getWriter(); 
    
    LocalDateTime myDateObj = LocalDateTime.now();  
    
    DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH:mm:ss");  
    
    String fd = myDateObj.format(myFormatObj); 
      
    try {  
    	
        otpStream = new FileOutputStream(new File("/home/venkat-zstk269/eclipse-workspaceUpdated/PhotosShow/src/main/webapp/pics/" + File.separator + id+"-"+content+fd+"."+arr[arr.length-1]));
    	
        iptStream = filePart.getInputStream();  

        int read = 0;  
          
        final byte[] bytes = new byte[1024];  
          
        while ((read = iptStream.read(bytes)) != -1) {  
            otpStream.write(bytes, 0, read);  
        }  
//        writer.println("New file " + fileName + " created at " + path);  

    }  
     
    catch (FileNotFoundException fne){  
//        writer.println("You either did not specify a file to upload or are trying to upload a file to a protected or nonexistent location.");  
//        writer.println("<br/> ERROR: " + fne.getMessage());  
 
    }  
 
    finally {  
        if (otpStream != null) {  
            otpStream.close();  
        }  
        if (iptStream != null) {  
            iptStream.close();  
        }  
        
    }  
    
    return "http://localhost:8081/PhotosShow/pics/"+ id+"-"+content+fd+"."+arr[arr.length-1];	
	
}


 private static String getFileName(final Part part) {  
        // get header(content-disposition) from the part  
        final String partHeader = part.getHeader("content-disposition");  
//        LOGGER.log(Level.INFO, "Part Header = {0}", partHeader);  
          
        // code to get file name from the header  
        for (String content : part.getHeader("content-disposition").split(";")) {  
            if (content.trim().startsWith("filename")) {  
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");  
            }  
        }  
        // it will return null when it doesn't get file name in the header   
        return null;  
    }  
	

	
	
	
	
	
	private int upload(int id, String name, String content, String date, String path) throws Exception {
		

		 DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/photoshow","root","");
int i = 0;
         // constructs SQL statement
         String sql = "INSERT INTO datas (id, name, content, date,path) values (?, ?, ?, ?, ?)";
         PreparedStatement statement = con.prepareStatement(sql);
         statement.setInt(1, id);
         statement.setString(2, name);
         statement.setString(3, content);
         statement.setString(4, date);
         statement.setString(5, path);
        
         int row = statement.executeUpdate();
         if (row > 0) {
             i =1;
         }
         
         
         return i;
         
         
         
          
		
	}


	private String getName(int id) throws Exception {
		
		 DriverManager.registerDriver(new com.mysql.jdbc.Driver());
       Connection  con = DriverManager.getConnection("jdbc:mysql://localhost:3306/photoshow","root","");
       
       PreparedStatement ps = con.prepareStatement("select name from users where id=?");
       ps.setInt(1, id);
       
       ResultSet rs = ps.executeQuery();
       
       while(rs.next()) {
           return (rs.getString(1));
           
        }
       	
		return null;
	}
	
	
	

}
