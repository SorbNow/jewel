package org.jewel.model;

import javax.persistence.*;
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
    private String orderNumber;

    @ManyToOne
    private Priority priority;

    @OneToMany(fetch = FetchType.EAGER)
    private List<ArticleInOrder> articleInOrder;

    @Column
//    @Temporal(TemporalType.DATE)
    private LocalDate addOrderDate;

    @Enumerated
    private OrderCondition orderCondition;

    @ManyToMany
    @NotNull(message = "Выберите минимум 1 артикул")
    private List<Article> articles;

    @ManyToOne
    private Customer customer;

    @Column
    private String comment;

    @Column
//    @Temporal(TemporalType.DATE)
    private LocalDate expectedDate;

    @Column
//    @Temporal(TemporalType.DATE)
    private LocalDate moldedDate;

    @Column
//    @Temporal(TemporalType.DATE)
    private LocalDate expectedDateFromMolded;

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

    public LocalDate getAddOrderDate() {
        return addOrderDate;
    }

    public void setAddOrderDate(LocalDate addOrderDate) {
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
}
