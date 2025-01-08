package com.projetos.finance.controller;

import com.projetos.finance.Model.Balance;
import com.projetos.finance.service.BalanceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/balance")
@RestController
public class BalanceController {


    @Autowired
    private BalanceService service;

    @PostMapping("/newBalance")
    public ResponseEntity<?> addNewBalance(@Valid @RequestBody Balance balance){
        return  ResponseEntity.ok(service.addBalance(balance));
    }

    @PutMapping(value = "/{id}")
    public Balance altBalance(@PathVariable Long id ,@RequestBody Balance balance){
        return service.altBalance(id, balance);
    }

    @GetMapping("/allBalances")
    public ResponseEntity<?> allBalances(){
        Iterable<Balance> balances = service.allBalance();
        return ResponseEntity.ok(balances);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteBalance(@PathVariable Long id){
        service.deleteBalance(id);
    }


}
