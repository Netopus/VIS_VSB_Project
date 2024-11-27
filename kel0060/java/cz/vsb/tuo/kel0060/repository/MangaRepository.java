package cz.vsb.tuo.kel0060.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Entities.Manga;
import cz.vsb.tuo.kel0060.Appconfig;

import java.sql.DriverManager;


@Repository
public interface MangaRepository extends JpaRepository<Manga, Integer> {

    // Method to retrieve a manga by ID using the AppConfig
    public default Manga getMangaById(int mangaId) throws SQLException {
        String sql = "SELECT manga_id, language_id, names, descriptions, rating, last_update FROM Manga WHERE manga_id = ?";
        Manga manga = null;

        // Using AppConfig to get the database URL
        try (Connection conn = DriverManager.getConnection(Appconfig.SQLurl);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, mangaId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int languageId = rs.getInt("language_id");
                    String names = rs.getString("names");
                    String descriptions = rs.getString("descriptions");
                    int rating = rs.getInt("rating");
                    Timestamp lastUpdate = rs.getTimestamp("last_update");

                    // Create the Manga object from the result set
                    manga = new Manga(mangaId, languageId, names, descriptions, rating, lastUpdate);
                    
                }
            }
        }
        return manga;
    }

}
