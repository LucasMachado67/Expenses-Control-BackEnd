package com.projetos.finance.service;

import com.projetos.finance.Model.Balance;
import com.projetos.finance.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class BalanceService{

    @Autowired
    private BalanceRepository repository;
    @Autowired
    private IncomeService incomeService;
    @Autowired
    private ExpenseService expenseService;

    public Optional<Balance> findByYearAndMonth(int year, int month){
        return repository.findByYearAndMonth(year, month);
    }

    public List<Balance> findBalancesByYear(int year){
        return repository.findBalancesByYear(year);
    }

    public Balance findBalanceByMonth(int month){
        return repository.findBalanceByMonth(month);
    }

    public Balance addBalance(Balance balance){

        return repository.save(balance);
    }

    public void updateBalance(int year, int month){

        double totalIncome = incomeService.getTotalIncome();
        double totalExpense = expenseService.getTotalExpense();

        Optional<Balance> existingBalance = repository.findByYearAndMonth(year, month);

        if(existingBalance.isPresent()){
            Balance balance = existingBalance.get();
            balance.setTotalIncomes(totalIncome);
            balance.setTotalExpenses(totalExpense);
            balance.setBalance(totalIncome, totalExpense);
            repository.save(balance);
        }else {
            Balance newBalance = new Balance();
            newBalance.setYear(year);
            newBalance.setMonth(month);
            newBalance.setTotalIncomes(totalIncome);
            newBalance.setTotalExpenses(totalExpense);
            newBalance.setBalance(totalIncome, totalExpense);
            repository.save(newBalance);
        }
    }


    public double getTotalBalance(){

        double totalIncome = incomeService.getTotalIncome();
        double totalExpense = expenseService.getTotalExpense();
        return totalIncome - totalExpense;
    }

}
