package javaFile;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import jakarta.servlet.http.HttpSession;




public class DatabaseSignin {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	public static boolean insert(String name, String email, String phone, String password,String uotp, String otp) throws Exception {
		 Class.forName("com.mysql.cj.jdbc.Driver");	    
			
	     Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/photoshow","root","");
	     
	     PreparedStatement ps = con.prepareStatement("insert into users(name,phone,email,password) values(?,?,?,?)");
	     

 if(uotp.equals(otp)) {
	     
			
			   
		    ps.setString(1, name);
		    ps.setString(2, phone);		
		    ps.setString(3, email);	
		    ps.setString(4, password);
		    
		   
//		    
////		    JFrame  f=new JFrame();   
////		    String uotp=JOptionPane.showInputDialog(f,"Enter OTP");     
////		    
////		    
//		    System.out.println("checking");
//		    System.out.println(uotp);
//	        System.out.println(name);
//	        System.out.println(phone);
//	        System.out.println(password);
//	        System.out.println(otp);
//		
		 
		    
		      if(ps.executeUpdate()==1) {
		    	return true;    	
	      
		   }else {
			   
		    	return false;
			    
		    }	         
			
 }else {
	
	 return false;

	}
 
 
 
 


			
	}
	
public static void sendMail(String recepient, String msg ){
		
	Logger log=Logger.getLogger(DatabaseSignin.class);
	 String log4jConfPath = "/home/venkat-zstk269/eclipse-workspaceUpdated/PhotosShow/src/main/java/log4j.properties";
       PropertyConfigurator.configure(log4jConfPath);
       
       
    	Properties properties = new Properties();
		
		 properties.put("mail.smtp.auth","true");
	        properties.put("mail.smtp.ssl.protocols", "TLSv1.2");
	        properties.put("mail.smtp.starttls.enable","true");
	        properties.put("mail.smtp.host","smtp.gmail.com");
	        properties.put("mail.smtp.port","587");
		
		String username = "secured.message.encrypt@gmail.com";
//	        String username = "otp.verify.bank@gmail.com";
		String password = "thamirabarani";
		
		Session session = Session.getInstance(properties,new Authenticator(){
			
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
			
		});
		
		
		
		
			
			
			
			
			Message message=new MimeMessage(session);
			try {
				message.setFrom(new InternetAddress(username));
				message.setRecipient(Message.RecipientType.TO, new InternetAddress(recepient));
				message.setSubject("OTP Verification...");
				
//				int rnd = (int) (Math.random()*50000 + Math.random()*50000 + 100000);
				
				
				
				message.setText(msg);
				
				
			}catch(Exception e) {
				
				 LocalDateTime myDateObj = LocalDateTime.now();
			        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

		        
			        log.error(myDateObj.format(myFormatObj)+" ERROR (msg adding in mail) ");
			    
			}
			
			
			
		
			try {
				Transport.send(message);
			} catch (MessagingException e) {
				// TODO Auto-generated catch block
			
			        LocalDateTime myDateObj = LocalDateTime.now();
			        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

			        
			        log.error(myDateObj.format(myFormatObj)+" ERROR (While sending mail) ");
			    
			} 	
    	
    	
    	
   
    	
	 
	 
	 }
}
