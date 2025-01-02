package com.projetos.finance.Model;

import jakarta.persistence.*;

import java.time.LocalDate;

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
    private LocalDate date;
    private String category;

    public Expense(String expenseName, String description, Integer quantity, LocalDate date, String category) {
        this.expenseName = expenseName;
        this.description = description;
        this.quantity = quantity;
        this.date = date;
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

    public LocalDate getDate() {
        return date;
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

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
