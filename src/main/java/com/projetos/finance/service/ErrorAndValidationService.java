package com.projetos.finance.service;

import com.projetos.finance.Model.Expense;
import com.projetos.finance.Model.Income;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class ErrorAndValidationService {


    public void validateDataMissingFromExpenseObject(Expense expense) {

        if (expense.getExpenseName() == null || expense.getExpenseName().isEmpty()) {
            throw new IllegalArgumentException("Field 'Expense Name' is not filled");
        }

        if (expense.getDescription() == null || expense.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Field 'Description' is not filled");
        }

        if (expense.getAmount() == null || expense.getAmount() <= 0) {
            throw new IllegalArgumentException("Field 'Amount' needs to be greater than 0 or needs to be filled");
        }

        if (expense.getQuantity() <= 0) {
            throw new IllegalArgumentException("Field 'Quantity' needs to be greater than 0");
        }

        if (expense.getCategory() == null || expense.getCategory().isEmpty()) {
            throw new IllegalArgumentException("Field 'Category' is not filled");
        }

        if(expense.getYear() > LocalDate.now().getYear()){
            throw new IllegalArgumentException("Field 'Year' is invalid");
        }
        if(expense.getMonth() > LocalDate.now().getMonthValue()){
            throw new IllegalArgumentException("Field 'Month' is invalid");
        }
        System.out.println("Data válida");
    }

    public void validateDataMissingFromIncomeObject(Income income) {

        if (income.getIncomeName() == null || income.getIncomeName().isEmpty()) {
            throw new IllegalArgumentException("Field 'Income Name' is not filled");
        }

        if (income.getDescription() == null || income.getDescription().isEmpty()) {
            throw new IllegalArgumentException("Field 'Description' is not filled");
        }

        if (income.getAmount() == null || income.getAmount() <= 0) {
            throw new IllegalArgumentException("Field 'Amount' needs to be greater than 0 or needs to be filled");
        }

        if (income.getQuantity() <= 0) {
            throw new IllegalArgumentException("Field 'Quantity' needs to be greater than 0");
        }

        if (income.getCategory() == null || income.getCategory().isEmpty()) {
            throw new IllegalArgumentException("Field 'Category' is not filled");
        }
        if(income.getYear() > LocalDate.now().getYear()){
            throw new IllegalArgumentException("Field 'Year' is invalid");
        }
        if(income.getMonth() > LocalDate.now().getMonthValue()){
            throw new IllegalArgumentException("Field 'Month' is invalid");
        }

        System.out.println("Data válida");
    }
}
