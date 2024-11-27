package cz.vsb.tuo.kel0060.classes;

import Entities.OrderSystem;
import jakarta.persistence.Entity;

/**
 * Represents a physical order with lazy-loading for additional fields.
 * Implements the Ghost pattern.
 */
@Entity
public class PhysicalOrder extends OrderSystem {
    private String shippingAddress;
    private String trackingNumber;

    private boolean fullyLoaded = false; // Tracks if additional fields are loaded

    // Getters and Setters
    public String getShippingAddress() {
        loadIfNeeded(); // Ensure data is loaded before returning
        return shippingAddress;
    }

    public void setShippingAddress(String shippingAddress) {
        this.shippingAddress = shippingAddress;
    }

    public String getTrackingNumber() {
        loadIfNeeded(); // Ensure data is loaded before returning
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }

    // Simulates loading the additional data
    private void loadIfNeeded() {
        if (!fullyLoaded) {
            System.out.println("Loading additional data for PhysicalOrder...");
            this.shippingAddress = "123 Example St, Cityville";
            this.trackingNumber = "TRACK123456789";
            fullyLoaded = true;
        }
    }
}
