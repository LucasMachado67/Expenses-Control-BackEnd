package com.projetos.finance.Model;

import jakarta.persistence.*;

@Entity
@Table(name="Balance")
public class Balance{

    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;
    private int year;
    private int month;
    private double totalIncomes;
    private double totalExpenses;
    private double balance;

    public Balance() {}

    public Balance(int year, int month ,double totalIncomes, double totalExpenses) {
        this.year = year;
        this.month = month;
        this.totalIncomes = totalIncomes;
        this.totalExpenses = totalExpenses;
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

    public double getTotalExpenses() {
        return totalExpenses;
    }

    public void setTotalExpenses(double totalExpenses) {
        this.totalExpenses = totalExpenses;
    }

    public double getTotalIncomes() {
        return totalIncomes;
    }

    public void setTotalIncomes(double totalIncomes) {
        this.totalIncomes = totalIncomes;
    }


    public double getBalance() {
        return balance;
    }

    public void setBalance(double totalExpenses, double totalIncomes){
        this.balance = totalIncomes - totalExpenses;
    }
}
