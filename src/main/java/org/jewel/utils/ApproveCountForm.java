package org.jewel.utils;

import org.jewel.model.MetalType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ApproveCountForm {
    private String articleName;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate date;
    private int count;
    private MetalType metalType;

    public String getArticleName() {
        return articleName;
    }

    public void setArticleName(String articleName) {
        this.articleName = articleName;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ApproveCountForm() {
    }

    public MetalType getMetalType() {
        return metalType;
    }

    public void setMetalType(MetalType metalType) {
        this.metalType = metalType;
    }

    public ApproveCountForm(String articleName, LocalDate date, int count) {
        this.articleName = articleName;
        this.date = date;
        this.count = count;
    }
}
