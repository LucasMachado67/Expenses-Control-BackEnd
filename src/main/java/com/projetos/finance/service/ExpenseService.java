package com.projetos.finance.service;
import com.projetos.finance.Model.Expense;
import com.projetos.finance.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


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

    public List<Expense> selectExpensesPerCategory(String category){
        List<Expense> expenses = repository.selectExpensesPerCategory(category);

        if (expenses.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No expenses found for the given category");
        }

        return expenses;
    }
    public Double getTotalExpense(){
        total = repository.getAmountAndMakeTotalExpense();
        return total;
    }
}