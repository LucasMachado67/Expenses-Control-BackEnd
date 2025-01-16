package com.projetos.finance.controller;

import com.projetos.finance.Model.Income;
import com.projetos.finance.service.IncomeService;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RequestMapping(value= "Incomes")
@RestController
public class IncomeController {

    @Autowired
    private IncomeService service;

    @GetMapping("/All")
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

    @GetMapping("/All/{id}")
    public ResponseEntity<Income> findById(@PathVariable Long id){

        try {
            return ResponseEntity.ok(service.findIncomeById(id));

        }catch (Exception e){
            return ResponseEntity.notFound().build();
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

    @PutMapping("/update")
    public ResponseEntity<Income> update(@RequestBody Income income){
        try{
            return ResponseEntity.ok(service.updateIncome(income));

        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/All/{id}")
    public void deleteIncome(@PathVariable Long id) {
        service.deleteIncome(id);
    }

    @GetMapping("/number")
    public Double totalIncome(){
       return service.getTotalIncome();
    }
}
