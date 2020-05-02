package org.jewel.model;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

@Entity
@Table
public class Customer {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false, unique = true)
    @NotBlank(message = "Поле обязательно для заполнения")
    private String customerName;

    @Column
    @NotBlank(message = "Поле обязательно для заполнения")
    private String customerCity;

    //add column to customers for finding in upper/lower case?


    public String getCustomerCity() {
        return customerCity;
    }

    public void setCustomerCity(String customerCity) {
        this.customerCity = customerCity;
    }

    public Customer() {
    }


    public Customer(String customerName) {
        this.customerName = customerName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
