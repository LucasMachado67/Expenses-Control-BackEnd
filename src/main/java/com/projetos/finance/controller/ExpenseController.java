package com.projetos.finance.controller;

import com.projetos.finance.Model.Expense;
import com.projetos.finance.service.ExpenseService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;


@RestController
@RequestMapping(value ="Expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService service;

    @GetMapping("/All")
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

    @GetMapping("/All/{id}")
    public Expense findById(@PathVariable Long id){
            return service.findExpenseById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Expense Not Found"));
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


    @PutMapping(value = "/{id}")
    public Expense altExpense(@PathVariable Long id ,@RequestBody Expense expense){
        return service.altExpense(id, expense);
    }

    @DeleteMapping("/All/{id}")
    public void deleteExpense(@PathVariable Long id) {
        service.deleteExpense(id);
    }
}
