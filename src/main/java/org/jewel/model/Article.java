package org.jewel.model;


import javax.persistence.*;
import java.util.List;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"article","metalType"}))
public class Article {

    @Id
    @GeneratedValue
    private long id;

    //can't be unique because same article may be in white or yellow gold. gold may be 585 or 750
    //maybe use uniqueconstraints? 3 columns: article,hallmark,metaltype
    @Column(nullable = false)
    private String article;

    @ManyToOne
    @JoinColumn(name = "metalType")
    private MetalType metalType;

    @Column
    private double averageWeight;

    @Column
    private double cost;

    @Enumerated
    private CollectionType collectionType;

    @ManyToMany
    private List<Mineral> minerals;

    @OneToMany
    private List<ArticleImages> articleImages;

    public List<Mineral> getMinerals() {
        return minerals;
    }

    public void setMinerals(List<Mineral> minerals) {
        this.minerals = minerals;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public MetalType getMetalType() {
        return metalType;
    }

    public void setMetalType(MetalType metalType) {
        this.metalType = metalType;
    }

    public double getAverageWeight() {
        return averageWeight;
    }

    public void setAverageWeight(double averageWeight) {
        this.averageWeight = averageWeight;
    }

    public List<ArticleImages> getArticleImages() {
        return articleImages;
    }

    public void setArticleImages(List<ArticleImages> articleImages) {
        this.articleImages = articleImages;
    }

    public CollectionType getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(CollectionType collectionType) {
        this.collectionType = collectionType;
    }
}
