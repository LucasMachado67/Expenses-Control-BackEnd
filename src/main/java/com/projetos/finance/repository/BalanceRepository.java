package com.projetos.finance.repository;

import com.projetos.finance.Model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BalanceRepository extends JpaRepository<Balance, Long> {

    Optional<Balance> findBalanceById(Long id);
//    List<Balance> findByYearAndMonth(int year, int month);

//    @Query("SELECT SUM(b.totalIncomes) FROM Balance b WHERE b.year = :year")
//    Double findTotalIncomesByYear(@Param("year") int year);
}
