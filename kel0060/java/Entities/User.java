package Entities;

import jakarta.persistence.*;
import java.sql.Timestamp;

import cz.vsb.tuo.kel0060.classes.BaseEntity;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class User extends BaseEntity {
	
	

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "roles", nullable = false)
    private char roles;

    @Column(name = "password", nullable = false)
    private String password;

    // Default Constructor
    public User() {}

    // Parameterized Constructor
    public User(String firstName, String lastName, String email, char roles, String password, Timestamp createDate, Timestamp lastUpdate) {
        super(createDate, lastUpdate); // Call BaseEntity constructor
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.roles = roles;
        this.password = password;
    }

    // Getters and Setters
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char getRoles() {
        return roles;
    }

    public void setRoles(char roles) {
        this.roles = roles;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
