package org.jewel.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"metalTypeName","hallmark"}))
public class MetalType {
    public MetalType() {
    }

    public MetalType(String metalType, int hallmark) {
        this.metalTypeName = metalType;
        this.hallmark = hallmark;
    }

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String metalTypeName;

    @Column
    private int hallmark;

    @OneToMany
    private List<Article> articles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMetalTypeName() {
        return metalTypeName;
    }

    public void setMetalTypeName(String metalType) {
        this.metalTypeName = metalType;
    }

    public int getHallmark() {
        return hallmark;
    }

    public void setHallmark(int hallmark) {
        this.hallmark = hallmark;
    }
}
