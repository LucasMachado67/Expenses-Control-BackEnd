package com.projetos.finance.Model;

import jakarta.persistence.*;

@Entity
@Table(name = "expense")
public class Expense {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String expenseName;
    private String description;
    private int quantity;
    private Double amount;
    @Column(name = "year_value")
    private int year;
    @Column(name = "month_value")
    private int month;
    private String category;

    public Expense(String expenseName, String description, int quantity, Double amount, int year, int month, String category) {
        this.expenseName = expenseName;
        this.description = description;
        this.quantity = quantity;
        this.amount = amount;
        this.month = month;
        this.year = year;
        this.category = category;
    }
    public Expense() {

    }

    public String getExpenseName() {
        return expenseName;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public Double getAmount() {
        return amount;
    }

    public String getCategory() {
        return category;
    }

    public Long getId() {
        return id;
    }

    public void setExpenseName(String expenseName) {
        this.expenseName = expenseName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }
}
