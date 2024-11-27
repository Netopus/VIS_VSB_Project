package cz.vsb.tuo.kel0060;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;


@Repository
public class AccessLogRepository {

    // Method to log access to the AccessLog table
	public void logAccess(Connection conn, int mangaId, String username) throws SQLException {
	    String sqlInsertLog = "INSERT INTO AccessLog (manga_id, username) VALUES (?, ?)";

	    try (PreparedStatement pstmt = conn.prepareStatement(sqlInsertLog)) {
	        pstmt.setInt(1, mangaId);
	        pstmt.setString(2, username);
	        int rowsAffected = pstmt.executeUpdate(); // Execute the insert
	        System.out.println("Rows affected: " + rowsAffected); // Debug output
	    } catch (SQLException e) {
	        e.printStackTrace(); // Print exception if there is an error
	    }
	}


    // Method to count access logs for a specific manga
    public int getAccessCountByMangaId(Connection conn, int mangaId) throws SQLException {
        String sqlCountLogs = "SELECT COUNT(*) FROM AccessLog WHERE manga_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sqlCountLogs)) {
            pstmt.setInt(1, mangaId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1); 
                }
            }
        }
        return 0; 
    }

    // Method to retrieve recent access logs for a specific manga
    public List<AccessLog> getRecentAccessLogsByMangaId(Connection conn, int mangaId) throws SQLException {
        String sqlRecentLogs = "SELECT log_id, manga_id, username, access_time FROM AccessLog WHERE manga_id = ? ORDER BY access_time DESC";
        List<AccessLog> accessLogs = new ArrayList<>();

        try (PreparedStatement pstmt = conn.prepareStatement(sqlRecentLogs)) {
            pstmt.setInt(1, mangaId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int logId = rs.getInt("log_id");
                    String username = rs.getString("username");
                    Timestamp accessTime = rs.getTimestamp("access_time");
                    
                    
                    AccessLog log = new AccessLog(logId, mangaId, username, accessTime);
                    accessLogs.add(log);
                }
            }
        }
        return accessLogs;
    }
}
