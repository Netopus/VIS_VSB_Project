package cz.vsb.tuo.kel0060.classes;

import Entities.OrderSystem;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

/**
 * Represents a digital order with lazy-loading for the download link and other fields.
 * Implements the Value Holder pattern for lazy-loading.
 */
@Entity
public class DigitalOrder extends OrderSystem {
    private ValueHolder<String> downloadLinkHolder;
    private ValueHolder<Long> chapterIdHolder;
    private ValueHolder<Object> amountPaidHolder;

    public DigitalOrder(Long chapterId, Object amountPaid) {
        // Initialize the ValueHolders
        this.downloadLinkHolder = new ValueHolder<>(this::loadDownloadLink);
        this.chapterIdHolder = new ValueHolder<>(() -> chapterId);
        this.amountPaidHolder = new ValueHolder<>(() -> amountPaid); // Lazily load amount paid
    }

    // Getters and Setters for download link
    public String getDownloadLink() {
        return downloadLinkHolder.getValue();
    }

    public void setDownloadLink(String downloadLink) {
        this.downloadLinkHolder = new ValueHolder<>(() -> downloadLink);
    }

    // Getters and Setters for chapter ID
    public Long getChapter() {
        return chapterIdHolder.getValue();
    }

    public void setChapter(Long chapterId) {
        this.chapterIdHolder = new ValueHolder<>(() -> chapterId);
    }

    // Getters and Setters for amount paid
    public Object getAmountPaid() {
        return amountPaidHolder.getValue();
    }

    public void setAmountPaid(Object price) {
        this.amountPaidHolder = new ValueHolder<>(() -> price);
    }

    // Simulates loading the download link
    private String loadDownloadLink() {
        System.out.println("Loading download link for DigitalOrder...");
        return "https://example.com/download/digital-order";
    }
}
