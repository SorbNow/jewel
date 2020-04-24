package org.jewel.model;

import javax.persistence.*;

@Entity
@Table
public class Customer {
    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false, unique = true)
    private String customerName;

    //add column to customers for finding in upper/lower case?

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
