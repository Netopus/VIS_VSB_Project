package cz.vsb.tuo.kel0060;

import java.sql.Timestamp;

public class AccessLog {
    private int logId;
    private int mangaId;
    private String username;
    private Timestamp accessTime;

    // Default Constructor
    public AccessLog() {
    }

    // Parameterized Constructor
    public AccessLog(int logId, int mangaId, String username, Timestamp accessTime) {
        this.logId = logId;
        this.mangaId = mangaId;
        this.username = username;
        this.accessTime = accessTime;
    }

    // Getters and Setters
    public int getLogId() {
        return logId;
    }

    public void setLogId(int logId) {
        this.logId = logId;
    }

    public int getMangaId() {
        return mangaId;
    }

    public void setMangaId(int mangaId) {
        this.mangaId = mangaId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Timestamp getAccessTime() {
        return accessTime;
    }

    public void setAccessTime(Timestamp accessTime) {
        this.accessTime = accessTime;
    }

    @Override
    public String toString() {
        return "AccessLog{" +
                "logId=" + logId +
                ", mangaId=" + mangaId +
                ", username='" + username + '\'' +
                ", accessTime=" + accessTime +
                '}';
    }
}
