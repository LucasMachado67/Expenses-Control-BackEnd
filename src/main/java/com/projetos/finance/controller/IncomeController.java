package com.projetos.finance.controller;

import com.projetos.finance.Model.Expense;
import com.projetos.finance.Model.Income;
import com.projetos.finance.service.IncomeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class IncomeController {

    @Autowired
    private IncomeService service;

    @GetMapping("/allIncomes")
    public ResponseEntity<?> allIncomes(){
        try {
            Iterable<Income> incomes = service.allIncomes();
            return ResponseEntity.ok(incomes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Unexpected error: " + e.getMessage());
        }
    }

    @PostMapping(value="/newIncome")
    public ResponseEntity<?> addNewIncome(@Valid @RequestBody Income income){
        try {
            return ResponseEntity.ok(service.newIncome(income));

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        }
    }
}
