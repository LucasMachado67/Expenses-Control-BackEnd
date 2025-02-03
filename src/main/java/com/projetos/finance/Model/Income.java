package com.projetos.finance.Model;

import jakarta.persistence.*;


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
    @Column(name="year_value")
    private int year;
    @Column(name="month_value")
    private int month;
    private String category;

    public Income(String incomeName, String description,int quantity, Double amount, int year, int month, String category) {
        this.incomeName = incomeName;
        this.description = description;
        this.quantity = quantity;
        this.amount = amount;
        this.year = year;
        this.month = month;
        this.category = category;
    }

    public Income(Long id,String incomeName, String description,int quantity, Double amount, int year, int month, String category) {
        this.id = id;
        this.incomeName = incomeName;
        this.description = description;
        this.quantity = quantity;
        this.amount = amount;
        this.year = year;
        this.month = month;
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

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
