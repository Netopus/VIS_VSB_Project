package Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.DiscriminatorValue;

/**
 * This class represents a content creator in the system.
 * In Single Table Inheritance, this is identified by "CREATOR" in the "user_type" column.
 */
@Entity
@DiscriminatorValue("AUTHOR")

public class Author extends User {
	private int mangasPublished;
	
}
