package com.projetos.finance.service;

import com.projetos.finance.Model.Balance;
import com.projetos.finance.Model.Expense;
import com.projetos.finance.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.Year;
import java.util.List;


@Service
public class BalanceService{

    @Autowired
    private BalanceRepository repository;
    @Autowired
    private IncomeService incomeService;
    @Autowired
    private ExpenseService expenseService;

    public Balance findByYearAndMonth(int year, int month){
        return repository.findByYearAndMonth(year, month);
    }

    public List<Balance> findBalancesByYear(int year){
        return repository.findBalancesByYear(year);
    }

    public Balance findBalanceByMonth(int month){
        return repository.findBalanceByMonth(month);
    }

    public Double addTotalIncome(Balance balance){
        Double totalIncome = incomeService.getTotalIncome();
        balance.setTotalIncomes(totalIncome);
        return totalIncome;
    }

    public Double addTotalExpense(Balance balance){
        Double totalExpense = expenseService.getTotalExpense();
        balance.setTotalExpenses(totalExpense);
        return totalExpense;
    }

    public Balance addBalance(Balance balance){
        balance.setYear(LocalDate.now().getYear());
        balance.setMonth(LocalDate.now().getMonthValue());
        balance.setBalance(addTotalIncome(balance), addTotalExpense(balance));
        return repository.save(balance);
    }


}
