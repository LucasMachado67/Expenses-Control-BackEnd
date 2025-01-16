package com.projetos.finance.repository;

import com.projetos.finance.Model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Query("SELECT SUM(amount) FROM Income")
    Double getAmountAndMakeTotalIncome();
}
