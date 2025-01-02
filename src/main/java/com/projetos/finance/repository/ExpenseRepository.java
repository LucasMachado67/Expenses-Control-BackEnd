package com.projetos.finance.repository;

import com.projetos.finance.Model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {

//    public newExpense();
}
