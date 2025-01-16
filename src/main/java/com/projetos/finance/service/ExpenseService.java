package com.projetos.finance.service;

import com.projetos.finance.Model.Expense;
import com.projetos.finance.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repository;

    @Autowired
    private ErrorAndValidationService validation;

    Double total;

    public Expense newExpense(Expense expense) {
        validation.validateDataMissingFromExpenseObject(expense);
        return repository.save(expense);
    }

    public List<Expense> allExpenses() {
        return repository.findAll();
    }

    public Expense findExpenseById(Long id){
        return repository.findById(id).orElseThrow();
    }

    public Expense updateExpense(Expense expense){
        var entity = repository.findById(expense.getId()).orElseThrow();
        entity.setExpenseName(expense.getExpenseName());
        entity.setDescription(expense.getDescription());
        entity.setQuantity(expense.getQuantity());
        entity.setAmount(expense.getAmount());
        entity.setYear(expense.getYear());
        entity.setMonth(expense.getMonth());
        entity.setCategory(expense.getCategory());
        return repository.save(expense);

    }

    public void deleteExpense(Long id){
       repository.deleteById(id);
    }

    public Double getTotalExpense(){
        total = repository.getAmountAndMakeTotalExpense();
        return total;
    }
}