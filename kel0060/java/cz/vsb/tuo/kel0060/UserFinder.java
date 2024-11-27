package cz.vsb.tuo.kel0060;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserFinder {
    private static String url = Appconfig.SQLurl;

    // Find a user by their ID
    public static UserGateway findById(Long id) throws SQLException {
        String sql = "SELECT * FROM User WHERE users_id = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUser(rs);
                } else {
                    return null; // No user found
                }
            }
        }
    }

    // Find a user by their email
    public static UserGateway findByEmail(String email) throws SQLException {
        String sql = "SELECT * FROM User WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapRowToUser(rs);
                } else {
                    return null; // No user found
                }
            }
        }
    }

    // Find all users with a specific role
    public static List<UserGateway> findByRole(char role) throws SQLException {
        String sql = "SELECT * FROM User WHERE roles = ?";
        List<UserGateway> users = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, String.valueOf(role));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(mapRowToUser(rs));
                }
            }
        }
        return users;
    }

    // Find all users
    public static List<UserGateway> findAll() throws SQLException {
        String sql = "SELECT * FROM User";
        List<UserGateway> users = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(url);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                users.add(mapRowToUser(rs));
            }
        }
        return users;
    }

    // Private helper method to map a ResultSet row to a UserGateway object
    private static UserGateway mapRowToUser(ResultSet rs) throws SQLException {
        return new UserGateway(
            rs.getLong("users_id"),
            rs.getString("email"),
            rs.getString("first_name"),
            rs.getString("last_name"),
            rs.getString("roles").charAt(0),
            rs.getString("password"),
            rs.getTimestamp("create_date"),
            rs.getTimestamp("last_update")
        );
    }
}
