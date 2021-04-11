package org.jewel.model;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Entity
@Table
public class ArticleOrder {

    @Id
    @GeneratedValue
    private long orderId;

    @Column
    private String orderNumberIn1C;

    @Column(unique = true, nullable = false)
    private String orderNumber;

    @ManyToOne
    private Priority priority;

    @OneToMany(fetch = FetchType.EAGER)
    private List<ArticleInOrder> articleInOrder;

    @Enumerated
    private OrderCondition orderCondition;

    @ManyToMany
    @NotNull(message = "Выберите минимум 1 артикул")
    @NotEmpty(message = "Список артикулов не может быть пустым")
    private List<Article> articles;

    @ManyToOne
    private Customer customer;

    @Column
    private String comment;

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
//    @Temporal(TemporalType.DATE)
    private Date lastDateIncome;

    @Column
    private String privateComment;

    @Column
    private LocalDate finishedDate;

    public LocalDate getFinishedDate() {
        return finishedDate;
    }

    public void setFinishedDate(LocalDate finishedDate) {
        this.finishedDate = finishedDate;
    }
    //    @OneToMany
//    private List<DoneArticleFromOrder> doneArticleFromOrders;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
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

   /* public List<DoneArticleFromOrder> getDoneArticleFromOrders() {
        return doneArticleFromOrders;
    }

    public void setDoneArticleFromOrders(List<DoneArticleFromOrder> doneArticleFromOrders) {
        this.doneArticleFromOrders = doneArticleFromOrders;
    }
*/

    public List<ArticleInOrder> getArticleInOrder() {
        return articleInOrder;
    }

    public void setArticleInOrder(List<ArticleInOrder> articleInOrder) {
        this.articleInOrder = articleInOrder;
    }

    public String getOrderNumberIn1C() {
        return orderNumberIn1C;
    }

    public void setOrderNumberIn1C(String orderNumberIn1C) {
        this.orderNumberIn1C = orderNumberIn1C;
    }
}
