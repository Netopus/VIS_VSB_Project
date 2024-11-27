package Entities;


import jakarta.persistence.Entity;

/**
 * Represents an Admin user.
 * Class Table Inheritance
 * Fields specific to Admins are stored in a separate `Admin` table.
 */
@Entity
public class Admin extends User {
    private boolean hasGlobalPermissions; // Whether the admin has global system permissions

    // Getters and setters
}