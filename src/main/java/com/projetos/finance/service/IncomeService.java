package com.projetos.finance.service;

import com.projetos.finance.Model.Income;
import com.projetos.finance.repository.IncomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class IncomeService {

    @Autowired
    private IncomeRepository repository;
    @Autowired
    private ErrorAndValidationService validation;

    Double total;

    public Income newIncome(Income income){
        validation.validateDataMissingFromIncomeObject(income);
        return repository.save(income);
    }

    public List<Income> allIncomes(){
        return repository.findAll();
    }

    public Income findIncomeById(Long id){
        return repository.findById(id).orElseThrow();
    }

    public Income updateIncome(Income income){
        var entity = repository.findById(income.getId()).orElseThrow();
        entity.setIncomeName(income.getIncomeName());
        entity.setDescription(income.getDescription());
        entity.setQuantity(income.getQuantity());
        entity.setAmount(income.getAmount());
        entity.setYear(income.getYear());
        entity.setMonth(income.getMonth());
        entity.setCategory(income.getCategory());
        return repository.save(income);

    }

    public void deleteIncome(Long id){
        repository.deleteById(id);
    }

    public List<Income> selectIncomesPerCategory(String category){

        List<Income> incomes = repository.selectIncomesPerCategory(category);

        if (incomes.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No incomes found for the given category");
        }

        return incomes;
    }

    public Double getTotalIncome(){
        total = repository.getAmountAndMakeTotalIncome();
        return total;
    }

}
