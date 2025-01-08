package com.projetos.finance.service;

import com.projetos.finance.Model.Balance;
import com.projetos.finance.Model.Expense;
import com.projetos.finance.repository.BalanceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class BalanceService {

    @Autowired
    private BalanceRepository repository;
    @Autowired
    private IncomeService incomeService;
    @Autowired
    private ExpenseService expenseService;

    public List<Balance> allBalance(){
        return repository.findAll();
    }

    public Optional<Balance> findBalanceById(Long id){
        return repository.findBalanceById(id);
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
        Double totalIncome = addTotalIncome(balance);
        Double totalExpense = addTotalExpense(balance);

        balance.setYear(LocalDate.now().getYear());
        balance.setMonth(LocalDate.now().getMonthValue());
        balance.setBalance(totalIncome,totalExpense);
        return repository.save(balance);
    }

    public Balance altBalance(Long id, Balance balance){
        Optional<Balance> op = findBalanceById(id);
        Balance exp = op.get();
        exp.setYear(balance.getYear());
        exp.setMonth(balance.getMonth());
        exp.setTotalIncomes(balance.getTotalIncomes());
        exp.setTotalExpenses(balance.getTotalExpenses());
        exp.setBalance(balance.getTotalExpenses(), balance.getTotalIncomes());
        return addBalance(exp);
    }

    public void deleteBalance(Long id){
        repository.deleteById(id);
    }
}
