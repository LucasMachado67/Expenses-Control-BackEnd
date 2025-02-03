package com.projetos.finance.controller;

import com.projetos.finance.Model.Balance;
import com.projetos.finance.Model.RequestBalance;
import com.projetos.finance.service.BalanceService;
import com.projetos.finance.service.ExpenseService;
import com.projetos.finance.service.IncomeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public Optional<Balance> findBalanceByYearAndMonth(){
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

    @PostMapping("/new")
    public ResponseEntity<Balance> newBalance(@RequestBody Balance balance){
        return  ResponseEntity.ok(service.addBalance(balance));
    }

    @PostMapping("/update")
    public ResponseEntity<Map<String, String>> updateBalance(@RequestBody RequestBalance request){
        service.updateBalance(request.getYear(), request.getMonth());
        Map<String, String> response = new HashMap<>();
        response.put("message", "Balance updated for " + request.getMonth() + "/" + request.getYear());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/total")
    public Double totalInBalance(){
        return service.getTotalBalance();
    }


}
