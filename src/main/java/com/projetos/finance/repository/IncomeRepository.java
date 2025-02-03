package com.projetos.finance.repository;

import com.projetos.finance.Model.Income;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IncomeRepository extends JpaRepository<Income, Long> {

    @Query("SELECT SUM(amount) FROM Income")
    Double getAmountAndMakeTotalIncome();

    @Query(value = "SELECT * FROM income i WHERE i.category = :category ", nativeQuery = true)
    List<Income> selectIncomesPerCategory(@Param("category") String category);
}
