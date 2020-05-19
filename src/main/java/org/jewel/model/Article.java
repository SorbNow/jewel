package org.jewel.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.util.List;

@Entity
@Table
//(uniqueConstraints = @UniqueConstraint(columnNames = {"articleName","metalType"}))
public class Article {

    @Id
    @GeneratedValue
    private long articleId;

    //can't be unique because same article may be in white or yellow gold. gold may be 585 or 750
    //maybe use uniqueconstraints? 3 columns: article,hallmark,metaltype
    @Column(nullable = false)
    @NotBlank(message = "Поле не может быть пустым")
    private String articleName;


    @Column(unique = true, nullable = false)
    private String dummyArticleName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "metalType")
    private MetalType metalType;

    @Column
    @PositiveOrZero(message = "Значение не может быть отрицательным")
    private double averageWeight;

    @Column
    private double cost;

    @Column
    @PositiveOrZero(message = "Поле не может иметь отрицательное значение")
    private int productionTime;
    @Column
    @PositiveOrZero(message = "Поле не может иметь отрицательное значение")
    private int productionTimeFromMolded;

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

    public long getArticleId() {
        return articleId;
    }

    public void setArticleId(long articleId) {
        this.articleId = articleId;
    }

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String article) {
        this.articleName = article;
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

    public String getDummyArticleName() {
        return dummyArticleName;
    }

    public void setDummyArticleName(String dummyArticleName) {
        this.dummyArticleName = dummyArticleName;
    }

    public int getProductionTime() {
        return productionTime;
    }

    public void setProductionTime(int productionTime) {
        this.productionTime = productionTime;
    }

    public int getProductionTimeFromMolded() {
        return productionTimeFromMolded;
    }

    public void setProductionTimeFromMolded(int productionTimeFromMolded) {
        this.productionTimeFromMolded = productionTimeFromMolded;
    }
}
