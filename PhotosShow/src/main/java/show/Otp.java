package show;

import jakarta.servlet.ServletException;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import javaFile.DatabaseSignin;
import javaFile.LoginDao;
import servlet.Register;

import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * Servlet implementation class Otp
 */
public class Otp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Otp() {
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
		
		String uotp = request.getParameter("first")+request.getParameter("second")+request.getParameter("third")+request.getParameter("fourth")+request.getParameter("fifth")+request.getParameter("sixth");
		
		HttpSession session1=request.getSession();      
       
       String name = (String) session1.getAttribute("name");
       String email = (String) session1.getAttribute("email");
       String phone = (String) session1.getAttribute("phone");
       String password = (String) session1.getAttribute("password");
       String OTP = (String) session1.getAttribute("OTP");


try {
	if(DatabaseSignin.insert(name,email,phone,password,uotp,OTP)) {
		
		Cookie cookie = new Cookie("Status", "true");
		cookie.setMaxAge(12*60*60);
		response.addCookie(cookie);
		
		
	String userid =	LoginDao.fetchUserid(name,email,phone,password);
		
		Cookie cookie1 = new Cookie("userid", String.valueOf((Integer.parseInt(userid))*12345+12345));
		cookie1.setMaxAge(12*60*60);
		response.addCookie(cookie1);
		
		System.out.println("login success page....!");
		response.sendRedirect("index.html");
	}else {
		
		
		
	        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
	        Date date = new Date();          
	        log.info(formatter.format(date)+" - Registration FAILED...!   WRONG OTP");
	        
		response.sendRedirect("registration.jsp");
	}
} catch (ClassNotFoundException e) {
	 SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
    Date date = new Date();          
    log.info(formatter.format(date)+" - Otp.java -   classnotfound error");
} catch (SQLException e) {
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
   Date date = new Date();          
   log.info(formatter.format(date)+" - Otp.java -   SQL Exception error");
} catch (IOException e) {
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
   Date date = new Date();          
   log.info(formatter.format(date)+" - Otp.java -   IO exception error");
} catch (Exception e) {
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");  
   Date date = new Date();          
   log.info(formatter.format(date)+" - Otp.java -   Exception error");
}
       
	}

}
