package Entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("MANGA")
public class Manga extends Media {
    private int mangaId;
    private int languageId;
    private String names;
    private String descriptions;
    private int rating;
    private java.sql.Timestamp lastUpdate;
    
    

    private int accessCount;
    
    

    // Default Constructor
    public Manga() {
    }

    // Parameterized Constructor
    public Manga(int mangaId, int languageId, String names, String descriptions, int rating, java.sql.Timestamp lastUpdate) {
        this.mangaId = mangaId;
        this.languageId = languageId;
        this.names = names;
        this.descriptions = descriptions;
        this.rating = rating;
        this.lastUpdate = lastUpdate;
    }

    // Getters and Setters
    public int getMangaId() {
        return mangaId;
    }
    
    public void incrementAccessCount() {
        this.accessCount++;
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

    public java.sql.Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(java.sql.Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    @Override
    public String toString() {
        return "Manga{" +
                "mangaId=" + mangaId +
                ", languageId=" + languageId +
                ", names='" + names + '\'' +
                ", descriptions='" + descriptions + '\'' +
                ", rating=" + rating +
                ", lastUpdate=" + lastUpdate +
                '}';
    }
}
