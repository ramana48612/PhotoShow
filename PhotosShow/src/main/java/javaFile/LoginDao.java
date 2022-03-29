package javaFile;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;



public class LoginDao {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public boolean validate(LoginBean loginBean) throws Exception {
		
		
		
		
		 Class.forName("com.mysql.cj.jdbc.Driver");	    
			
	     Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/photoshow","root","");
	     
	 	
	     boolean status = false;
	     PreparedStatement ps = con.prepareStatement("select * from users where id = ? and password = ?");
	     ps.setString(1,loginBean.getUserid());
	     ps.setString(2,loginBean.getPassword());
	     
	   ResultSet rs =   ps.executeQuery();
	     status = rs.next();
	    
	     con.close();
//	     System.out.println(loginBean.getUserid());
//	     System.out.println(loginBean.getPassword());
//	     System.out.println(status);     
	     return status;
	     
		}
	
	public static String fetchUserid(String name, String email, String phone, String password) throws ClassNotFoundException, SQLException {
		 Class.forName("com.mysql.cj.jdbc.Driver");	    
			
	     Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/photoshow","root","");
	     
	     PreparedStatement ps = con.prepareStatement("select id from users where password = ? and name=? and email=? and phone=?");
	     ps.setString(1,password);
	     ps.setString(2,name);
	     ps.setString(3,email);
	     ps.setString(4,phone);
	     ResultSet rs =   ps.executeQuery();
	     
	     while(rs.next()) {
				return rs.getString(1);
				
			}
	 	
		con.close();
		
		return "";
	}

}
