package cz.vsb.tuo.kel0060;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/manga")
public class MangaController {
    private final MangaService mangaService;
    private final DataSource dataSource; // Injecting DataSource

    @Autowired
    public MangaController(MangaService mangaService, DataSource dataSource) {
        this.mangaService = mangaService;
        this.dataSource = dataSource;
    }

    @PostMapping("/{mangaId}/access")
    public ResponseEntity<String> logAccess(@PathVariable int mangaId, @RequestParam String username) {
        try (Connection conn = dataSource.getConnection()) { // Using the injected DataSource to get connection
            mangaService.logAccess(mangaId, username, conn);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error logging access");
        }
        return ResponseEntity.ok("Access logged successfully");
    }

    @GetMapping("/{mangaId}/statistics")
    public ResponseEntity<Map<String, Object>> getStatistics(@PathVariable int mangaId) {
        Map<String, Object> response = new HashMap<>();
        try (Connection conn = dataSource.getConnection()) { // Using the injected DataSource to get connection
            int accessCount = mangaService.getAccessCount(mangaId, conn);
            List<AccessLog> recentLogs = mangaService.getRecentAccessLogs(mangaId, conn);

            response.put("totalAccessCount", accessCount);
            response.put("recentAccessLogs", recentLogs);
        } catch (SQLException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        return ResponseEntity.ok(response);
    }
    
    
}