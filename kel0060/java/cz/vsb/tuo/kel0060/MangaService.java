package cz.vsb.tuo.kel0060;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;
import java.util.Optional;


import Entities.Chapter;
import Entities.Manga;
import Entities.OrderSystem;
import Entities.User;
import cz.vsb.tuo.kel0060.classes.DigitalOrder;
import cz.vsb.tuo.kel0060.classes.MangaServiceInterface;
import cz.vsb.tuo.kel0060.classes.OrderRepository;
import cz.vsb.tuo.kel0060.repository.ChapterRepository;
import cz.vsb.tuo.kel0060.repository.MangaRepository;
import cz.vsb.tuo.kel0060.repository.UserRepository;
import jakarta.transaction.Transactional;



/**
 * Real Implementation of MangaService:
 * Simulates fetching manga details from a real database or API.
 */

@Service
public class MangaService implements MangaServiceInterface {
    private final MangaRepository mangaRepository;
    private final AccessLogRepository accessLogRepository;
    
    private final UserRepository userRepository;
    private final ChapterRepository chapterRepository;
    private final OrderRepository orderRepository;

    public MangaService(MangaRepository mangaRepository, AccessLogRepository accessLogRepository) {
        this.mangaRepository = mangaRepository;
        this.accessLogRepository = accessLogRepository;
    }

    public void logAccess(int mangaId, String username, Connection conn) throws SQLException {
        mangaRepository.getMangaById(mangaId); // Ensure the manga exists
        accessLogRepository.logAccess(conn, mangaId, username); // Log the access
    }

    public int getAccessCount(int mangaId, Connection conn) throws SQLException {
        return accessLogRepository.getAccessCountByMangaId(conn, mangaId);
    }

    public List<AccessLog> getRecentAccessLogs(int mangaId, Connection conn) throws SQLException {
        return accessLogRepository.getRecentAccessLogsByMangaId(conn, mangaId);
    }
    
    @Override
    public Manga getMangaDetails(String title) {
        // Simulate a database or API call
        return new Manga(0, 0, title, "AuthorName", 0, null);
    }
    
    @Transactional
    public OrderSystem placeOrder(Long userId, Long mangaId, Long chapterId) {
        // Fetch User from the database
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            // User not found, handle error without adding new exception class
            System.out.println("Error: User with ID " + userId + " not found");
            return null;  // You can return a response or throw a runtime exception here if preferred
        }

        // Fetch Chapter from the database
        Chapter chapter = chapterRepository.findById(chapterId).orElse(null);
        if (chapter == null) {
            // Chapter not found, handle error without adding new exception class
            System.out.println("Error: Chapter with ID " + chapterId + " not found");
            return null;  // You can return a response or throw a runtime exception here if preferred
        }

        // Create DigitalOrder entity
        DigitalOrder order = new DigitalOrder(chapterId, 50);  // Assuming the amount is 50 for now
        order.setUser(user);
        order.setChapter(chapterId);
        order.setAmountPaid(chapter.getPrice());  // Assuming no discount for simplicity

        // Save the order to the database
        return orderRepository.save(order);
    }


}
