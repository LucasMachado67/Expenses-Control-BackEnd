package com.projetos.finance.repository;

import com.projetos.finance.Model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<Expense> findExpenseById(Long id);

    @Query("SELECT SUM(amount) FROM Expense")
    Double getAmountAndMakeTotalExpense();
}
