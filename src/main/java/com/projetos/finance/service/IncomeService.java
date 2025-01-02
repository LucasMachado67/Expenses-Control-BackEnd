package com.projetos.finance.service;

import com.projetos.finance.Model.Income;
import com.projetos.finance.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository repository;
    @Autowired
    private ErrorAndValidationSerivce validation;

    public Income newIncome(Income income){

        validation.validateDataMissingFromIncomeObject(income);
        return repository.save(income);
    }

    public List<Income> allIncomes(){
        return repository.findAll();
    }
}
