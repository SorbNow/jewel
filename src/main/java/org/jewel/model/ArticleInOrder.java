package org.jewel.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.Map;

@Entity
@Table
        //(uniqueConstraints = @UniqueConstraint(columnNames = {"articleOrder","article"}))
public class ArticleInOrder {

    @Id
    @GeneratedValue
    private long articleInOrderId;

    @Column(nullable = false)
    @NotBlank
    private String articleOrder;

    @ManyToOne(fetch = FetchType.EAGER)
    private Article article;

//    @Column(unique = true)
//    private String dummyForUnique;

    @Column
    @Positive(message = "Значение должно быть положительным")
    private int count;

    @Column
    private int doneCount;

    @Column
    private int size;

    @ElementCollection
    @CollectionTable(name = "article_in_order_done",
    joinColumns = {@JoinColumn(name = "article_in_order_done_id") })
    @MapKeyColumn(name = "done_date")
    @Column(name="done_count")
    private Map<LocalDate, Integer> dateAndCountDone;

    @Column
    private LocalDate lastDate;
    @Column
//    @Temporal(TemporalType.DATE)
    private LocalDate addOrderDate;

    @Column
//    @Temporal(TemporalType.DATE)
    private LocalDate expectedDate;

    @Column
//    @Temporal(TemporalType.DATE)
    private LocalDate moldedDate;

    @Column
//    @Temporal(TemporalType.DATE)
    private LocalDate expectedDateFromMolded;

    public long getArticleInOrderId() {
        return articleInOrderId;
    }

    public void setArticleInOrderId(long articleInOrderId) {
        this.articleInOrderId = articleInOrderId;
    }

    public String getArticleOrder() {
        return articleOrder;
    }

    public void setArticleOrder(String articleOrder) {
        this.articleOrder = articleOrder;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getDoneCount() {
        return doneCount;
    }

    public void setDoneCount(int doneCount) {
        this.doneCount = doneCount;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public Map<LocalDate, Integer> getDateAndCountDone() {
        return dateAndCountDone;
    }

    public void setDateAndCountDone(Map<LocalDate, Integer> dateAndCountDone) {
        this.dateAndCountDone = dateAndCountDone;
    }

    public LocalDate getLastDate() {
        return lastDate;
    }

    public void setLastDate(LocalDate lastDate) {
        this.lastDate = lastDate;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

//    public String getDummyForUnique() {
//        return dummyForUnique;
//    }
//
//    public void setDummyForUnique(String dummyForUnique) {
//        this.dummyForUnique = dummyForUnique;
//    }


    public LocalDate getAddOrderDate() {
        return addOrderDate;
    }

    public void setAddOrderDate(LocalDate addOrderDate) {
        this.addOrderDate = addOrderDate;
    }

    public LocalDate getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(LocalDate expectedDate) {
        this.expectedDate = expectedDate;
    }

    public LocalDate getMoldedDate() {
        return moldedDate;
    }

    public void setMoldedDate(LocalDate moldedDate) {
        this.moldedDate = moldedDate;
    }

    public LocalDate getExpectedDateFromMolded() {
        return expectedDateFromMolded;
    }

    public void setExpectedDateFromMolded(LocalDate expectedDateFromMolded) {
        this.expectedDateFromMolded = expectedDateFromMolded;
    }
}
