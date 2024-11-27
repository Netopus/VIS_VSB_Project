package cz.vsb.tuo.kel0060;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Service;

import Entities.Manga;
import Entities.OrderSystem;
import cz.vsb.tuo.kel0060.classes.MangaServiceInterface;
import cz.vsb.tuo.kel0060.repository.MangaRepository;
import jakarta.transaction.Transactional;



/**
 * Real Implementation of MangaService:
 * Simulates fetching manga details from a real database or API.
 */

@Service
public class MangaService implements MangaServiceInterface {
    private final MangaRepository mangaRepository;
    private final AccessLogRepository accessLogRepository;

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
    public OrderSystem placeOrder(Long userId, Long chapterId) {
        // Fetch User and Chapter from the database
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserNotFoundException("User not found"));
        Chapter chapter = chapterRepository.findById(chapterId)
            .orElseThrow(() -> new ChapterNotFoundException("Chapter not found"));

        // Create Order entity
        OrderSystem order = new OrderSystem();
        order.setUser(user);
        order.setChapter(chapter);
        order.setAmountPaid(chapter.getPrice()); // Assuming no discount for simplicity


        // Save the order to the database
        return orderRepository.save(order);
    }
}
