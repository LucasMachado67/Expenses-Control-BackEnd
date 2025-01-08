package com.projetos.finance.service;

import com.projetos.finance.Model.Expense;
import com.projetos.finance.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;


@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repository;

    @Autowired
    private ErrorAndValidationSerivce validation;

    Double total;

    public Expense newExpense(Expense expense) {
        validation.validateDataMissingFromExpenseObject(expense);
        return repository.save(expense);
    }

    public List<Expense> allExpenses() {
        return repository.findAll();
    }

    public Optional<Expense> findExpenseById(Long id){
        return repository.findExpenseById(id);
    }

    public Expense altExpense(Long id, Expense expense){
        Optional<Expense> op = findExpenseById(id);
        Expense exp = op.get();
        exp.setExpenseName(expense.getExpenseName());
        exp.setDescription(expense.getDescription());
        exp.setQuantity(expense.getQuantity());
        exp.setAmount(expense.getAmount());
        exp.setCategory(expense.getCategory());
        exp.setDate(expense.getDate());
        return newExpense(exp);
    }

    public void deleteExpense(Long id){
       repository.deleteById(id);
    }

    public Double getTotalExpense(){
        total = repository.getAmountAndMakeTotalExpense();
        return total;
    }
}