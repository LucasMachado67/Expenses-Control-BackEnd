package com.projetos.finance.repository;

import com.projetos.finance.Model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface IncomeRepository extends JpaRepository<Income, Long> {

    Optional<Income> findIncomeById(Long id);

    @Query("SELECT SUM(amount) FROM Income")
    Double getAmountAndMakeTotalIncome();
}
