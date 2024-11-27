package Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Language {
	@Id
    private int languageId;
    private String names;
    private java.sql.Timestamp lastUpdate;
}
