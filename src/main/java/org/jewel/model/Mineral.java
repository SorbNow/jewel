package org.jewel.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
@Table
public class Mineral {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Поле не может быть пустым")
    private String insert;

    @Column
    private String letterMikhailov;

    @Column
    private String letterChemyakin;

    @Column
    private String letterBallet;

    @ManyToMany
    private List<Article> articles;

    public List<Article> getArticle() {
        return articles;
    }

    public void setArticle(List<Article> article) {
        this.articles = article;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInsert() {
        return insert;
    }

    public void setInsert(String insert) {
        this.insert = insert;
    }

    public String getLetterMikhailov() {
        return letterMikhailov;
    }

    public void setLetterMikhailov(String letterMikhailov) {
        this.letterMikhailov = letterMikhailov;
    }

    public String getLetterChemyakin() {
        return letterChemyakin;
    }

    public void setLetterChemyakin(String letterChemyakin) {
        this.letterChemyakin = letterChemyakin;
    }

    public String getLetterBallet() {
        return letterBallet;
    }

    public void setLetterBallet(String letterBallet) {
        this.letterBallet = letterBallet;
    }

    @Override
    public String toString() {
        return insert;
    }
}
