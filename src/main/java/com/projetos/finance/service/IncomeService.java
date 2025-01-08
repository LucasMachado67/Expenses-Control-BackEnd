package com.projetos.finance.service;

import com.projetos.finance.Model.Income;
import com.projetos.finance.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository repository;
    @Autowired
    private ErrorAndValidationSerivce validation;

    Double total;

    public Income newIncome(Income income){
        validation.validateDataMissingFromIncomeObject(income);
        return repository.save(income);
    }

    public List<Income> allIncomes(){
        return repository.findAll();
    }

    public Optional<Income> findIncomeById(Long id){
        return repository.findIncomeById(id);
    }

    public Income altIncome(Long id, Income income){
        Optional<Income> op = findIncomeById(id);
        Income inc = op.get();
        inc.setIncomeName(income.getIncomeName());
        inc.setDescription(income.getDescription());
        inc.setQuantity(income.getQuantity());
        inc.setAmount(income.getAmount());
        inc.setCategory(income.getCategory());
        inc.setDate(income.getDate());
        return newIncome(inc);
    }

    public void deleteIncome(Long id){
        repository.deleteById(id);
    }

    public Double getTotalIncome(){
        total = repository.getAmountAndMakeTotalIncome();
        return total;
    }

}
