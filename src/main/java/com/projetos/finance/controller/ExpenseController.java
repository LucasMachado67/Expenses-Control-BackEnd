package com.projetos.finance.controller;

import com.projetos.finance.Model.ErrorResponse;
import com.projetos.finance.Model.Expense;
import com.projetos.finance.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ExpenseController {

    @Autowired
    private ExpenseService service;

    @Autowired
    private ErrorResponse response;

    @GetMapping("/allExpenses")
    public ResponseEntity<?> allExpenses(){
        try {
            Iterable<Expense> expenses = service.allExpenses();
            return ResponseEntity.ok(expenses);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + e.getMessage());
        }
    }

    @PostMapping(value="/newExpense")
    public ResponseEntity<?> addNewExpense(@Valid  @RequestBody Expense expense){
        try {
            return ResponseEntity.ok(service.newExpense(expense));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        }
    }
}
