package cz.vsb.tuo.kel0060;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Search {
	
	String url = Appconfig.SQLurl;
	
	
    private Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
    
   //Function to return manga with a specific name
    public List<String> getMangaByName(String mangaName){
    	List<String> mangaList = new ArrayList<>();
        String sql = "SELECT Manga.names "
                + "FROM Manga "
                + "WHERE Manga.names LIKE ?";
        
        try (Connection conn = this.connect();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
               
               // Setting the parameter with % for matching substrings
               stmt.setString(1, "%" + mangaName + "%");
               
               try (ResultSet rs = stmt.executeQuery()) {
                   while (rs.next()) {
                       mangaList.add(rs.getString("names"));
                   }
               }
           } catch (SQLException e) {
               e.printStackTrace();  // Handle exception, log it, or rethrow it as needed
           }
           
           return mangaList;
       }
	
    
    // Function to return all manga with a specific category
    public List<String> getMangaByCategory(String categoryName) {
        List<String> mangaList = new ArrayList<>();
        String sql = "SELECT Manga.names "
                   + "FROM Manga "
                   + "JOIN Manga_categories ON Manga.manga_id = Manga_categories.manga_id "
                   + "JOIN Categories ON Manga_categories.category_id = Categories.category_id "
                   + "WHERE Categories.names = ?";

        try (Connection conn = this.connect();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Set the value for the category name
            pstmt.setString(1, categoryName);

            ResultSet rs = pstmt.executeQuery();

            // Loop through the result set and add manga names to the list
            while (rs.next()) {
                mangaList.add(rs.getString("names"));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        return mangaList;
    }
	
    
}
