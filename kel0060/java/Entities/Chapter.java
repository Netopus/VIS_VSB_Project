package Entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;

@Entity
@DiscriminatorValue("Chapter")
public class Chapter {
	private int chapterId;
	private int mangaId;
	private String names;
	private String descriptions;
	private java.sql.Timestamp lastUpdate;

}

