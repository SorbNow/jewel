package org.jewel.model;

import javax.persistence.*;

@Entity
@Table
public class ArticleImages {

    public ArticleImages() {
    }

    public ArticleImages(String imagePath) {
        this.imagePath = imagePath;
    }

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String imagePath;

    @ManyToOne
    private Article articles;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
