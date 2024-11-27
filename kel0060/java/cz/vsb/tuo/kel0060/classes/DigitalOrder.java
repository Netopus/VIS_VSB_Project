package cz.vsb.tuo.kel0060.classes;

import Entities.OrderSystem;
import jakarta.persistence.Entity;

/**
 * Represents a digital order with lazy-loading for the download link.
 * Implements the Value Holder pattern.
 */
@Entity
public class DigitalOrder extends OrderSystem {
    private ValueHolder<String> downloadLinkHolder;

    public DigitalOrder() {
        this.downloadLinkHolder = new ValueHolder<>(this::loadDownloadLink);
    }

    public String getDownloadLink() {
        return downloadLinkHolder.getValue(); // Use the Value Holder to retrieve the value
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLinkHolder = new ValueHolder<>(() -> downloadLink); // Override the loader
    }

    // Simulates loading the download link
    private String loadDownloadLink() {
        // Simulated loading process (e.g., database or API call)
        System.out.println("Loading download link for DigitalOrder...");
        return "https://example.com/download/digital-order";
    }
}
