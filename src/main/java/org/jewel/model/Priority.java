package org.jewel.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table
public class Priority {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Поле не может быть пустым")
    private String priorityType;

    @Column
    @NotBlank(message = "Поле не может быть пустым")
    @Size(min = 4, message = "Поле не можеть быть короче 4 символов")
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
