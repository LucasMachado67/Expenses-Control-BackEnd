package com.projetos.finance.controller;

import com.projetos.finance.Model.Balance;
import com.projetos.finance.service.BalanceService;
import com.projetos.finance.service.ExpenseService;
import com.projetos.finance.service.IncomeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequestMapping("/balance")
@RestController
public class BalanceController {

    @Autowired
    private IncomeService incomeService;

    @Autowired
    private ExpenseService expenseService;

    @Autowired
    private BalanceService service;

    @GetMapping("/yearAndMonth")
    public Balance findBalanceByYearAndMonth(){
        int actualYear = LocalDate.now().getYear();
        int actualMonth = LocalDate.now().getMonthValue();
        return service.findByYearAndMonth(actualYear, actualMonth);
    }

    @GetMapping("/month")
    public Balance findBalanceByMonth(){
        int actualMonth = LocalDate.now().getMonthValue();
        return service.findBalanceByMonth(actualMonth);
    }

    @GetMapping("/year")
    public List<Balance> findBalanceByYear(){
        int actualYear = LocalDate.now().getYear();
        return service.findBalancesByYear(actualYear);
    }

    @PostMapping("/newBalance")
    public ResponseEntity<Balance> addNewBalance(@Valid @RequestBody Balance balance){
        return  ResponseEntity.ok(service.addBalance(balance));
    }

    @GetMapping("/total")
    public Double totalInBalance(){
        return expenseService.getTotalExpense() - incomeService.getTotalIncome();
    }


}
