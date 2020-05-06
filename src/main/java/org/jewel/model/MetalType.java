package org.jewel.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
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
    @NotBlank(message = "Поле обязательно для заполнения")
    private String metalTypeName;

    @Column
    @Positive(message = "Поле обязательно для заполнения. Может иметь только положительные значения")
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
