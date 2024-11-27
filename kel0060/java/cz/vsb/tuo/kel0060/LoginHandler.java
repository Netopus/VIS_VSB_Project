package cz.vsb.tuo.kel0060;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginHandler {
	String url = Appconfig.SQLurl; // specify your file path
	
	 public boolean login(String email, String password) {
	        String sql = "SELECT password FROM user WHERE email = ?";
	        
	        try (Connection conn = DriverManager.getConnection(url);
	             PreparedStatement pstmt = conn.prepareStatement(sql)) {
	            pstmt.setString(1, email);
	            ResultSet rs = pstmt.executeQuery();
	            
	            if (rs.next()) {
	                String storedPassword = rs.getString("password");
	                if (storedPassword.equals(password)) {
	                    System.out.println("Uspesne prihlaseni");
	                    return true; 
	                }
	            } else {
	                System.out.println("User nenalezen.");
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	        System.out.println("Login nebo neni sparvne.");
	        return false; 
	    }
	
	
}
