package com.projetos.finance.controller;

import com.projetos.finance.Model.Expense;
import com.projetos.finance.service.ExpenseService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping(value ="Expenses")
public class ExpenseController {

    @Autowired
    private ExpenseService service;

    @GetMapping("/all")
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

    @GetMapping("/number")
    public Double totalExpense(){
        return service.getTotalExpense();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Expense> findById(@PathVariable Long id){
            try{
                return ResponseEntity.ok(service.findExpenseById(id));
            } catch (Exception e) {
                return ResponseEntity.notFound().build();
            }
    }

    @Transactional
    @PostMapping(value="/new")
    public ResponseEntity<?> addNewExpense(@Valid  @RequestBody Expense expense){
        try {
            return ResponseEntity.ok(service.newExpense(expense));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        }
    }


    @PutMapping("/update")
    public ResponseEntity<Expense> update(@RequestBody Expense expense){
        try{
            return ResponseEntity.ok(service.updateExpense(expense));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public void deleteExpense(@PathVariable Long id) {
        service.deleteExpense(id);
    }

    @GetMapping("/{category}")
    public List<Expense> selectPerCategory(@PathVariable String category){

        return service.selectExpensesPerCategory(category);
    }
}
