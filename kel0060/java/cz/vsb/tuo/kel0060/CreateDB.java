package cz.vsb.tuo.kel0060;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class CreateDB {
	
	static String url = Appconfig.SQLurl;
	
    private static Connection connect() {
        Connection conn = null;
        try {
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return conn;
    }
	

    public static void createDataBase() {
    	
        File dbFile = new File("mangasite.db"); // Adjust the path if necessary

        // Check if the database file already exists
        if (dbFile.exists()) {
            System.out.println("Database already exists: " + dbFile.getAbsolutePath());
            return; // Exit the method if the database exists
        }

        Connection conn = null;
        try {
            // Create a connection to the database
            conn = DriverManager.getConnection(url);

            if (conn != null) {
                System.out.println("A new database has been created.");
                
                createTables(conn);
                insertData(conn);
                
                //.dropTables(conn);
                
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
    
    
    private static void createTables(Connection conn) {
    	
        // SQL statement for creating the Languages table
        String sqlLanguages = "CREATE TABLE IF NOT EXISTS Languages ("
                + "language_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "names TEXT NOT NULL,"
                + "last_update DATETIME NOT NULL"
                + ");";

        // SQL statement for creating the Manga table
        String sqlManga = "CREATE TABLE IF NOT EXISTS Manga ("
                + "manga_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "language_id INTEGER NOT NULL,"
                + "names TEXT NOT NULL,"
                + "descriptions TEXT,"
                + "rating INTEGER CHECK (rating BETWEEN 1 AND 5),"
                + "last_update DATETIME NOT NULL,"
                + "FOREIGN KEY (language_id) REFERENCES Languages(language_id) ON DELETE CASCADE ON UPDATE CASCADE"
                + ");";

        // SQL statement for creating the User table
        String sqlUserCt = "CREATE TABLE IF NOT EXISTS User ("
                + "users_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "first_name TEXT NOT NULL,"
                + "last_name TEXT,"
                + "email TEXT NOT NULL,"
                + "roles CHAR(1) NOT NULL CHECK (roles IN ('C', 'A', 'U')),"
                + "password TEXT NOT NULL,"
                + "create_date DATETIME NOT NULL,"
                + "last_update DATETIME NOT NULL,"
                + "CHECK (create_date < last_update)"
                + ");";

        // SQL statement for creating the User_follow table
        String sqlUserFollow = "CREATE TABLE IF NOT EXISTS User_follow ("
                + "users_id INTEGER NOT NULL,"
                + "manga_id INTEGER NOT NULL,"
                + "typ_sledovani CHAR(1) NOT NULL CHECK (typ_sledovani IN ('c', 'h', 'o', 'l')),"
                + "last_update DATETIME NOT NULL,"
                + "PRIMARY KEY (users_id, manga_id),"
                + "FOREIGN KEY (users_id) REFERENCES User_ct(users_id),"
                + "FOREIGN KEY (manga_id) REFERENCES Manga(manga_id)"
                + ");";

        // SQL statement for creating the Chapter table
        String sqlChapter = "CREATE TABLE IF NOT EXISTS Chapter ("
                + "chapter_id INTEGER PRIMARY KEY,"
                + "manga_id INTEGER NOT NULL,"
                + "names TEXT NOT NULL,"
                + "last_update DATETIME NOT NULL,"
                + "FOREIGN KEY (manga_id) REFERENCES Manga(manga_id) ON DELETE CASCADE ON UPDATE CASCADE"
                + ");";

        // SQL statement for creating the Author table
        String sqlAuthor = "CREATE TABLE IF NOT EXISTS Author ("
                + "author_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "name TEXT NOT NULL,"
                + "last_update DATETIME NOT NULL"
                + ");";

        // SQL statement for creating the Author_manga table
        String sqlAuthorManga = "CREATE TABLE IF NOT EXISTS Author_manga ("
                + "manga_id INTEGER NOT NULL,"
                + "author_id INTEGER NOT NULL,"
                + "last_update DATETIME NOT NULL,"
                + "PRIMARY KEY (author_id, manga_id),"
                + "FOREIGN KEY (author_id) REFERENCES Author(author_id),"
                + "FOREIGN KEY (manga_id) REFERENCES Manga(manga_id)"
                + ");";

        // SQL statement for creating the Categories table
        String sqlCategories = "CREATE TABLE IF NOT EXISTS Categories ("
                + "category_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "names TEXT NOT NULL,"
                + "last_update DATETIME NOT NULL"
                + ");";

        // SQL statement for creating the Manga_categories table
        String sqlMangaCategories = "CREATE TABLE IF NOT EXISTS Manga_categories ("
                + "category_id INTEGER NOT NULL,"
                + "manga_id INTEGER NOT NULL,"
                + "last_update DATETIME NOT NULL,"
                + "PRIMARY KEY (category_id, manga_id),"
                + "FOREIGN KEY (category_id) REFERENCES Categories(category_id),"
                + "FOREIGN KEY (manga_id) REFERENCES Manga(manga_id)"
                + ");";
        
     // SQL statement for creating the AccessLog table
        String sqlAccessLog = "CREATE TABLE IF NOT EXISTS AccessLog ("
                + "log_id INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "manga_id INTEGER NOT NULL,"
                + "username TEXT NOT NULL,"
                + "access_time DATETIME DEFAULT CURRENT_TIMESTAMP,"
                + "FOREIGN KEY (manga_id) REFERENCES Manga(manga_id) ON DELETE CASCADE ON UPDATE CASCADE"
                + ");";

        String sqlOrder = "CREATE TABLE IF NOT EXISTS `OrderSystem` ("
                + "id BIGINT PRIMARY KEY AUTO_INCREMENT,"
                + "user_id BIGINT NOT NULL,"
                + "order_details TEXT,"
                + "FOREIGN KEY (user_id) REFERENCES User(id) ON DELETE CASCADE ON UPDATE CASCADE"
                + ");";
        		
        String sqlMedia = "CREATE TABLE IF NOT EXISTS Media ("
                + "media_id BIGINT PRIMARY KEY AUTO_INCREMENT,"
                + "title VARCHAR(255) NOT NULL,"
                + "description TEXT,"
                + "release_date TIMESTAMP,"
                + "media_type VARCHAR(255) NOT NULL,"
                + "CHECK (media_type IN ('Manga', 'Image', 'Text'))"  
                + ");";

        try (Statement stmt = conn.createStatement()) {
            // Execute each SQL statement
            stmt.execute(sqlLanguages);
            stmt.execute(sqlManga);
            stmt.execute(sqlUserCt);
            stmt.execute(sqlUserFollow);
            stmt.execute(sqlChapter);
            stmt.execute(sqlAuthor);
            stmt.execute(sqlAuthorManga);
            stmt.execute(sqlCategories);
            stmt.execute(sqlMangaCategories);
            stmt.execute(sqlAccessLog);
            stmt.execute(sqlOrder);
            stmt.execute(sqlMedia);

            System.out.println("Tables created successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void insertTestData() {
        String sqlInsertManga = "INSERT INTO Manga (language_id, names, descriptions, rating, last_update) VALUES (?, ?, ?, ?, ?)";
        String sqlInsertCategory = "INSERT INTO Categories (names, last_update) VALUES (?, ?)";
        String sqlInsertMangaCategory = "INSERT INTO Manga_categories (category_id, manga_id, last_update) VALUES (?, ?, ?)";
        
        try (Connection conn = CreateDB.connect()) {
            // Insert categories
            PreparedStatement pstmtCategory = conn.prepareStatement(sqlInsertCategory);
            pstmtCategory.setString(1, "Action");
            pstmtCategory.setString(2, "2024-10-01");
            pstmtCategory.executeUpdate();
            
            pstmtCategory.setString(1, "Comedy");
            pstmtCategory.setString(2, "2024-10-01");
            pstmtCategory.executeUpdate();
            
            pstmtCategory.setString(1, "Drama");
            pstmtCategory.setString(2, "2024-10-01");
            pstmtCategory.executeUpdate();
            
            // Insert manga
            PreparedStatement pstmtManga = conn.prepareStatement(sqlInsertManga);
            pstmtManga.setInt(1, 1);  // Assuming language_id 1 exists
            pstmtManga.setString(2, "Manga A");
            pstmtManga.setString(3, "Description A");
            pstmtManga.setInt(4, 5);
            pstmtManga.setString(5, "2024-10-08");
            pstmtManga.executeUpdate();

            pstmtManga.setString(2, "Manga B");
            pstmtManga.setString(3, "Description B");
            pstmtManga.setInt(4, 4);
            pstmtManga.setString(5, "2024-10-08");
            pstmtManga.executeUpdate();
            
            pstmtManga.setString(2, "Manga C");
            pstmtManga.setString(3, "Description C");
            pstmtManga.setInt(4, 3);
            pstmtManga.setString(5, "2024-10-08");
            pstmtManga.executeUpdate();

            // Associate manga with categories (Manga A, B, C)
            PreparedStatement pstmtMangaCategory = conn.prepareStatement(sqlInsertMangaCategory);
            
            pstmtMangaCategory.setInt(1, 1); // Action
            pstmtMangaCategory.setInt(2, 1); // Manga A
            pstmtMangaCategory.setString(3, "2024-10-08");
            pstmtMangaCategory.executeUpdate();
            
            pstmtMangaCategory.setInt(1, 1); // Action
            pstmtMangaCategory.setInt(2, 2); // Manga B
            pstmtMangaCategory.setString(3, "2024-10-08");
            pstmtMangaCategory.executeUpdate();
            
            pstmtMangaCategory.setInt(1, 2); // Comedy
            pstmtMangaCategory.setInt(2, 3); // Manga C
            pstmtMangaCategory.setString(3, "2024-10-08");
            pstmtMangaCategory.executeUpdate();
            
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    
    public static void insertData(Connection conn) throws SQLException {
        insertUsers(conn);
        insertLanguages(conn);
        insertMangas(conn);
        insertAuthors(conn);
        insertAuthorManga(conn);
        insertUserFollows(conn);
        insertChapters(conn);
        insertCategories(conn);
        insertMangaCategories(conn);
        insertAccessLogs(conn);
    }

    private static void insertUsers(Connection conn) throws SQLException {
        String sql = "INSERT INTO User (first_name, last_name, email, roles, password,last_update, create_date) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        	Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        	
            stmt.setString(1, "Kirill");
            stmt.setString(2, "Keleshidi");
            stmt.setString(3, "mr.tired@mail.com");
            stmt.setString(4, "A");
            stmt.setString(5, "adminpassword");
            stmt.setTimestamp(6, currentTimestamp);
            stmt.setDate(7, java.sql.Date.valueOf("2000-07-22"));
            stmt.addBatch();

            stmt.setString(1, "John");
            stmt.setString(2, "Doe");
            stmt.setString(3, "john.doe@gmail.com");
            stmt.setString(4, "U");
            stmt.setString(5, "userpassword");
            stmt.setTimestamp(6, currentTimestamp);
            stmt.setDate(7, java.sql.Date.valueOf("2000-07-22"));
            stmt.addBatch();

           
            stmt.executeBatch();
        }
    }

    private static void insertLanguages(Connection conn) throws SQLException {
        String sql = "INSERT INTO Languages(names, last_update) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        	Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        	
            stmt.setString(1, "English");
            stmt.setTimestamp(2, currentTimestamp);
            stmt.addBatch();
            
            stmt.setString(1, "Japanese");
            stmt.setTimestamp(2, currentTimestamp);
            stmt.addBatch();
            
            stmt.setString(1, "Chinese");
            stmt.setTimestamp(2, currentTimestamp);
            stmt.addBatch();
            
            stmt.executeBatch();
        }
    }
    private static void insertAccessLogs(Connection conn) throws SQLException {
        String sql = "INSERT INTO AccessLog (manga_id, username, access_time) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            
            // Log for user accessing One Piece
            stmt.setInt(1, 1); // manga_id for One Piece
            stmt.setString(2, "Kirill"); // username Kirill
            stmt.setTimestamp(3, currentTimestamp); // current access time
            stmt.addBatch();

            // Log for user accessing Naruto
            stmt.setInt(1, 2); // manga_id for Naruto
            stmt.setString(2, "John"); // username John
            stmt.setTimestamp(3, currentTimestamp); // current access time
            stmt.addBatch();

            // Add more entries as needed
            stmt.setInt(1, 1); // Accessing One Piece again
            stmt.setString(2, "John");
            stmt.setTimestamp(3, currentTimestamp);
            stmt.addBatch();

            // Execute batch insertion
            stmt.executeBatch();
        }
    }


    private static void insertMangas(Connection conn) throws SQLException {
        String sql = "INSERT INTO Manga (language_id, names, rating, descriptions, last_update) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        	Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        	
            stmt.setInt(1, 1); // English
            stmt.setString(2, "One Piece");
            stmt.setInt(3, 1);
            stmt.setString(4, "A legendary adventure manga.");
            stmt.setTimestamp(5, currentTimestamp);
            stmt.addBatch();

            stmt.setInt(1, 2); // Japanese
            stmt.setString(2, "Naruto");
            stmt.setInt(3, 2);
            stmt.setString(4, "A ninjas journey to become Hokage.");
            stmt.setTimestamp(5, currentTimestamp);
            stmt.addBatch();

            stmt.executeBatch();
        }
    }

    private static void insertAuthors(Connection conn) throws SQLException {
        String sql = "INSERT INTO Author (name, last_update) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        	Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            stmt.setString(1, "Eiichiro Oda");
            stmt.setTimestamp(2, currentTimestamp);
            stmt.addBatch();
            
            stmt.setString(1, "Masashi Kishimoto");
            stmt.setTimestamp(2, currentTimestamp);
            stmt.addBatch();
            
            stmt.setString(1, "Hajime Isayama");
            stmt.setTimestamp(2, currentTimestamp);
            stmt.addBatch();
            
            stmt.executeBatch();
        }
    }

    private static void insertAuthorManga(Connection conn) throws SQLException {
        String sql = "INSERT INTO Author_Manga (manga_id, author_id, last_update) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        	Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            stmt.setInt(1, 1);
            stmt.setInt(2, 1); // Eiichiro Oda
            stmt.setTimestamp(3, currentTimestamp);
            stmt.addBatch();

            stmt.setInt(1, 2);
            stmt.setInt(2, 2); // Masashi Kishimoto
            stmt.setTimestamp(3, currentTimestamp);
            stmt.addBatch();

           
            stmt.executeBatch();
        }
    }

    private static void insertUserFollows(Connection conn) throws SQLException {
        String sql = "INSERT INTO User_follow (users_id, manga_id, typ_sledovani, last_update) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        	Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        	
            stmt.setInt(1, 1);
            stmt.setInt(2, 1);
            stmt.setString(3, "h");
            stmt.setTimestamp(4, currentTimestamp);
            stmt.addBatch();

            stmt.setInt(1, 2);
            stmt.setInt(2, 2);
            stmt.setString(3, "h");
            stmt.setTimestamp(4, currentTimestamp);
            stmt.addBatch();

           
            stmt.executeBatch();
        }
    }

    private static void insertChapters(Connection conn) throws SQLException {
        String sql = "INSERT INTO Chapter (chapter_id, manga_id, names, last_update) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        	Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            stmt.setInt(1, 1);
            stmt.setInt(2, 1);
            stmt.setString(3, "First");
            stmt.setTimestamp(4, currentTimestamp);
            stmt.addBatch();

            stmt.setInt(1, 4);
            stmt.setInt(2, 2);
            stmt.setString(3, "First");
            stmt.setTimestamp(4, currentTimestamp);
            stmt.addBatch();

           
            stmt.executeBatch();
        }
    }

    private static void insertCategories(Connection conn) throws SQLException {
        String sql = "INSERT INTO Categories (names, last_update) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        	Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        	
            stmt.setString(1, "Action");
            stmt.setTimestamp(2, currentTimestamp);
            stmt.addBatch();
            
            stmt.setString(1, "Adventure");
            stmt.setTimestamp(2, currentTimestamp);
            stmt.addBatch();
            
            stmt.setString(1, "Comedy");
            stmt.setTimestamp(2, currentTimestamp);
            stmt.addBatch();
            
            
           
            stmt.executeBatch();
        }
    }

    private static void insertMangaCategories(Connection conn) throws SQLException {
        String sql = "INSERT INTO Manga_categories (category_id, manga_id, last_update) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
        	Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            stmt.setInt(1, 1);
            stmt.setInt(2, 1);
            stmt.setTimestamp(3, currentTimestamp);
            stmt.addBatch();

            stmt.setInt(1, 2);
            stmt.setInt(2, 2);
            stmt.setTimestamp(3, currentTimestamp);
            stmt.addBatch();

            
            stmt.executeBatch();
        }
    }
    
    private static void dropTables(Connection conn) {
        String[] dropTableQueries = {
            "DROP TABLE IF EXISTS User_follow;",
            "DROP TABLE IF EXISTS Manga_categories;",
            "DROP TABLE IF EXISTS Author_manga;",
            "DROP TABLE IF EXISTS Chapter;",
            "DROP TABLE IF EXISTS Manga;",
            "DROP TABLE IF EXISTS Author;",
            "DROP TABLE IF EXISTS Categories;",
            "DROP TABLE IF EXISTS Languages;",
            "DROP TABLE IF EXISTS User;",
            "DROP TABLE IF EXISTS AccessLog;"
        };

        try (Statement stmt = conn.createStatement()) {
            for (String query : dropTableQueries) {
                stmt.execute(query);
            }
            System.out.println("Tables dropped successfully.");
        } catch (SQLException e) {
            System.out.println("Error dropping tables: " + e.getMessage());
        }
    }
}

