package cz.vsb.tuo.kel0060;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class UserGateway {
    private Long id;
    private String email;
    private String firstName;
    private String lastName;
    private char role;
    private String password;
    private Timestamp createDate;
    private Timestamp lastUpdate;
    private static String url = Appconfig.SQLurl;

    // Constructors
    public UserGateway() {}

    public UserGateway(Long id) {
        this.id = id;
    }

    public UserGateway(long id, String email, String firstName, String lastName, char role, String password, Timestamp createDate, Timestamp lastUpdate) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.password = password;
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
    }

    // Add a new user to the database
    public void addUser(String email, String firstName, String lastName, char role, String password) {
        String sql = "INSERT INTO User(email, first_name, last_name, roles, create_date, last_update, password) VALUES(?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime slightlyLater = now.plusSeconds(1);
            pstmt.setString(1, email);
            pstmt.setString(2, firstName);
            pstmt.setString(3, lastName);
            pstmt.setString(4, String.valueOf(role));
            pstmt.setTimestamp(5, Timestamp.valueOf(now)); // create_date
            pstmt.setTimestamp(6, Timestamp.valueOf(slightlyLater)); // last_update
            pstmt.setString(7, password);
            pstmt.executeUpdate();
            System.out.println("Uživatel přidán do systému.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Delete a user based on their ID
    public void deleteUser(int userId) {
        String sql = "DELETE FROM User WHERE users_id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Uživatel smazán.");
            } else {
                System.out.println("Uživatel s tímto ID neexistuje.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    // Update the user's last update date
    public void updateUserLastUpdate(int userId) {
        String sql = "UPDATE User SET last_update = ? WHERE users_id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            LocalDateTime now = LocalDateTime.now();
            pstmt.setTimestamp(1, Timestamp.valueOf(now)); // last_update
            pstmt.setInt(2, userId);
            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Uživatelův záznam aktualizován.");
            } else {
                System.out.println("Uživatel s tímto ID neexistuje.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
