package org.jewel.model;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Entity
@Table
public class ArticleDoneDateAndCount {


    @Id
    @GeneratedValue
    private long doneId;

    @Column(nullable = false)
    @PastOrPresent(message = "Прием изделий из будущего запрещен")
    private LocalDate doneDate;

    @Column
    private int count;

    public long getDoneId() {
        return doneId;
    }

    public void setDoneId(long doneId) {
        this.doneId = doneId;
    }

    public LocalDate getDoneDate() {
        return doneDate;
    }

    public void setDoneDate(LocalDate doneDate) {
        this.doneDate = doneDate;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
