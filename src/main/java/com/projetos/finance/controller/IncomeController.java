package com.projetos.finance.controller;

import com.projetos.finance.Model.Income;
import com.projetos.finance.service.IncomeService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequestMapping(value= "Incomes")
@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class IncomeController {

    @Autowired
    private IncomeService service;

    @GetMapping("/all")
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

    @GetMapping("/{id}")
    public ResponseEntity<Income> findById(@PathVariable Long id){

        try {
            return ResponseEntity.ok(service.findIncomeById(id));

        }catch (Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @Transactional
    @PostMapping(value="/new")
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

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteIncome(@PathVariable Long id) {

        try {
            service.deleteIncome(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Income not found.");
        }
    }

    @GetMapping("/number")
    public Double totalIncome(){
       return service.getTotalIncome();
    }

    @GetMapping("/{category}")
    public List<Income> selectPerCategory(@PathVariable String category){

        return service.selectIncomesPerCategory(category);
    }
}
