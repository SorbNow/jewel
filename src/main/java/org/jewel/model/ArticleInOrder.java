package org.jewel.model;

import javax.persistence.*;
import javax.validation.constraints.Positive;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Entity
@Table
public class ArticleInOrder {

    @Id
    @GeneratedValue
    private long articleInOrderId;

    @Column
    private String articleOrder;

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
    private Map<Date, Integer> dateAndCountDone;

    @Column
    private Date lastDate;

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

    public Map<Date, Integer> getDateAndCountDone() {
        return dateAndCountDone;
    }

    public void setDateAndCountDone(Map<Date, Integer> dateAndCountDone) {
        this.dateAndCountDone = dateAndCountDone;
    }

    public Date getLastDate() {
        return lastDate;
    }

    public void setLastDate(Date lastDate) {
        this.lastDate = lastDate;
    }
}
