package com.projetos.finance.Model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name="balance")
public class Balance{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="year_value")
    private int year;
    @Column(name="month_value")
    private int month;
    private Double totalIncomes;
    private Double totalExpenses;
    private Double balance;

    public Balance() {}

//    public Balance(Double totalIncomes, Double totalExpenses) {
//        this.year = LocalDate.now().getYear();
//        this.month = LocalDate.now().getMonth().ordinal();
//        this.totalIncomes = totalIncomes;
//        this.totalExpenses = totalExpenses;
//        this.balance = totalIncomes - totalExpenses;
//    }

    public Balance(int year, int month, Double totalIncomes, Double totalExpenses) {
        this.year = year;
        this.month = month;
        this.totalExpenses = totalExpenses;
        this.totalIncomes = totalIncomes;
        this.balance = totalIncomes - totalExpenses;
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

    public Double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(Double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public Double getTotalIncomes() {
        return totalIncomes;
    }

    public void setTotalIncomes(Double totalIncomes) {
        this.totalIncomes = totalIncomes;
    }


    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double totalExpenses, Double totalIncomes){
        this.balance = totalIncomes - totalExpenses;
    }
}
