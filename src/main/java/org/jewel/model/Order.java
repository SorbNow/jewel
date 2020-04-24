package org.jewel.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
public class Order {
    @GeneratedValue
    @Id
    private long id;

    @Column
    private int orderNumber;

    @ManyToOne
    private Priority priority;

    @Column
    private Date addOrderDate;

    @Enumerated
    private OrderCondition orderCondition;

    @OneToMany
    private List<Article> articles;

    @Column
    private int count;

    @Column
    private int size;

    @OneToMany
    private List<Mineral> minerals;

    @ManyToOne
    private Customer customer;

    @Column
    private String comment;

    @Column
    private Date expectedDate;

    @Column
    private Date moldedDate;

    @Column
    private Date expectedDateFromMolded;

    @Column
    private int countDaysFromAddOrder;

    @Column
    private int countDaysFromMoldedDate;

    @Column
    private int expiredDays;

    @Column
    private int doneCount;

    @Column
    private int countNotDone;

    @Column
    private Date lastDateIncome;

    @Column
    private String privateComment;

    @OneToMany
    private List<DoneArticleFromOrder> doneArticleFromOrders;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Date getAddOrderDate() {
        return addOrderDate;
    }

    public void setAddOrderDate(Date addOrderDate) {
        this.addOrderDate = addOrderDate;
    }

    public OrderCondition getOrderCondition() {
        return orderCondition;
    }

    public void setOrderCondition(OrderCondition orderCondition) {
        this.orderCondition = orderCondition;
    }

    public List<Article> getArticles() {
        return articles;
    }

    public void setArticles(List<Article> articles) {
        this.articles = articles;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public List<Mineral> getMinerals() {
        return minerals;
    }

    public void setMinerals(List<Mineral> minerals) {
        this.minerals = minerals;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Date getExpectedDate() {
        return expectedDate;
    }

    public void setExpectedDate(Date expectedDate) {
        this.expectedDate = expectedDate;
    }

    public Date getMoldedDate() {
        return moldedDate;
    }

    public void setMoldedDate(Date moldedDate) {
        this.moldedDate = moldedDate;
    }

    public Date getExpectedDateFromMolded() {
        return expectedDateFromMolded;
    }

    public void setExpectedDateFromMolded(Date expectedDateFromMolded) {
        this.expectedDateFromMolded = expectedDateFromMolded;
    }

    public int getCountDaysFromAddOrder() {
        return countDaysFromAddOrder;
    }

    public void setCountDaysFromAddOrder(int countDaysFromAddOrder) {
        this.countDaysFromAddOrder = countDaysFromAddOrder;
    }

    public int getCountDaysFromMoldedDate() {
        return countDaysFromMoldedDate;
    }

    public void setCountDaysFromMoldedDate(int countDaysFromMoldedDate) {
        this.countDaysFromMoldedDate = countDaysFromMoldedDate;
    }

    public int getExpiredDays() {
        return expiredDays;
    }

    public void setExpiredDays(int expiredDays) {
        this.expiredDays = expiredDays;
    }

    public int getDoneCount() {
        return doneCount;
    }

    public void setDoneCount(int doneCount) {
        this.doneCount = doneCount;
    }

    public int getCountNotDone() {
        return countNotDone;
    }

    public void setCountNotDone(int countNotDone) {
        this.countNotDone = countNotDone;
    }

    public Date getLastDateIncome() {
        return lastDateIncome;
    }

    public void setLastDateIncome(Date lastDateIncome) {
        this.lastDateIncome = lastDateIncome;
    }

    public String getPrivateComment() {
        return privateComment;
    }

    public void setPrivateComment(String privateComment) {
        this.privateComment = privateComment;
    }

    public List<DoneArticleFromOrder> getDoneArticleFromOrders() {
        return doneArticleFromOrders;
    }

    public void setDoneArticleFromOrders(List<DoneArticleFromOrder> doneArticleFromOrders) {
        this.doneArticleFromOrders = doneArticleFromOrders;
    }
}
