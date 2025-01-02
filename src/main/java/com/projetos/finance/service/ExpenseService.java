package com.projetos.finance.service;

import com.projetos.finance.Model.Expense;
import com.projetos.finance.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository repository;

    @Autowired
    private ErrorAndValidationSerivce validation;


    public Expense newExpense(Expense expense) {

        validation.validateDataMissingFromExpenseObject(expense);
        return repository.save(expense);
    }

    public List<Expense> allExpenses() {
        return repository.findAll();
    }

}