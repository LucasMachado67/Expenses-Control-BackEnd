package com.projetos.finance.Model;

import jakarta.persistence.*;

import java.time.LocalDate;
@Entity
@Table(name = "income")
public class Income {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String incomeName;
    private String description;
    private int quantity;
    private Double amount;
    private LocalDate date;
    private String category;

    public Income(String incomeName, String description,int quantity, Double amount, LocalDate date, String category) {
        this.incomeName = incomeName;
        this.description = description;
        this.quantity = quantity;
        this.amount = amount;
        this.date = date;
        this.category = category;
    }
    public Income(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIncomeName() {
        return incomeName;
    }

    public void setIncomeName(String incomeName) {
        this.incomeName = incomeName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
