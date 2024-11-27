package cz.vsb.tuo.kel0060;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class MangaGateway {
    private int mangaId;
    private int languageId;
    private String names;
    private String descriptions;
    private int rating;
    private Timestamp lastUpdate;
    private static String url = Appconfig.SQLurl;

    // Constructors
    public MangaGateway() {}

    public MangaGateway(int mangaId, int languageId, String names, String descriptions, int rating, Timestamp lastUpdate) {
        this.mangaId = mangaId;
        this.languageId = languageId;
        this.names = names;
        this.descriptions = descriptions;
        this.rating = rating;
        this.lastUpdate = lastUpdate;
    }

    // Add a new Manga
    public void addManga(int languageId, String names, String descriptions, int rating) {
        String sql = "INSERT INTO Manga(language_id, names, descriptions, rating, last_update) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            LocalDateTime now = LocalDateTime.now();
            pstmt.setInt(1, languageId);
            pstmt.setString(2, names);
            pstmt.setString(3, descriptions);
            pstmt.setInt(4, rating);
            pstmt.setTimestamp(5, Timestamp.valueOf(now));
            pstmt.executeUpdate();
            System.out.println("Manga přidána do systému.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Delete a Manga by ID
    public void deleteManga(int mangaId) {
        String sql = "DELETE FROM Manga WHERE manga_id = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, mangaId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Manga smazána.");
            } else {
                System.out.println("Manga s tímto ID neexistuje.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Update the last update date of a Manga
    public void updateMangaLastUpdate(int mangaId) {
        String sql = "UPDATE Manga SET last_update = ? WHERE manga_id = ?";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            LocalDateTime now = LocalDateTime.now();
            pstmt.setTimestamp(1, Timestamp.valueOf(now));
            pstmt.setInt(2, mangaId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Záznam manga aktualizován.");
            } else {
                System.out.println("Manga s tímto ID neexistuje.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Find Manga by ID
    public static MangaGateway findById(int mangaId) throws SQLException {
        String sql = "SELECT * FROM Manga WHERE manga_id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, mangaId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToManga(rs);
                } else {
                    return null; // No manga found
                }
            }
        }
    }

    // Find all Manga
    public static List<MangaGateway> findAll() throws SQLException {
        String sql = "SELECT * FROM Manga";
        List<MangaGateway> mangaList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                mangaList.add(mapRowToManga(rs));
            }
        }
        return mangaList;
    }

    // Find Manga by Language ID
    public static List<MangaGateway> findByLanguageId(int languageId) throws SQLException {
        String sql = "SELECT * FROM Manga WHERE language_id = ?";
        List<MangaGateway> mangaList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, languageId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    mangaList.add(mapRowToManga(rs));
                }
            }
        }
        return mangaList;
    }

    // Private helper method to map a ResultSet row to a MangaGateway object
    private static MangaGateway mapRowToManga(ResultSet rs) throws SQLException {
        return new MangaGateway(
                rs.getInt("manga_id"),
                rs.getInt("language_id"),
                rs.getString("names"),
                rs.getString("descriptions"),
                rs.getInt("rating"),
                rs.getTimestamp("last_update")
        );
    }

    // Getters and Setters
    public int getMangaId() {
        return mangaId;
    }

    public void setMangaId(int mangaId) {
        this.mangaId = mangaId;
    }

    public int getLanguageId() {
        return languageId;
    }

    public void setLanguageId(int languageId) {
        this.languageId = languageId;
    }

    public String getNames() {
        return names;
    }

    public void setNames(String names) {
        this.names = names;
    }

    public String getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(String descriptions) {
        this.descriptions = descriptions;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
        this.rating = rating;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
