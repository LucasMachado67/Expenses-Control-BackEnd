package com.projetos.finance.repository;

import com.projetos.finance.Model.Expense;
import com.projetos.finance.Model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

    Optional<Expense> findExpenseById(Long id);

    @Query("SELECT SUM(amount) FROM Expense")
    Double getAmountAndMakeTotalExpense();

    @Query(value = "SELECT * FROM expense e WHERE e.category = :category ", nativeQuery = true)
    List<Expense> selectExpensesPerCategory(@Param("category") String category);
}
