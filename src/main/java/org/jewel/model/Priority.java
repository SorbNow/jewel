package org.jewel.model;

import javax.persistence.*;

@Entity
@Table
public class Priority {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false, unique = true)
    private String priorityType;

    @Column
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPriorityType() {
        return priorityType;
    }

    public void setPriorityType(String priorityType) {
        this.priorityType = priorityType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
