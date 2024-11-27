package cz.vsb.tuo.kel0060.classes;

import jakarta.persistence.*;
import java.sql.Timestamp;

@MappedSuperclass
public abstract class BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "create_date", nullable = false)
    private Timestamp createDate;

    @Column(name = "last_update", nullable = false)
    private Timestamp lastUpdate;

    // Constructors
    public BaseEntity() {}

    public BaseEntity(Timestamp createDate, Timestamp lastUpdate) {
        this.createDate = createDate;
        this.lastUpdate = lastUpdate;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Timestamp lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
