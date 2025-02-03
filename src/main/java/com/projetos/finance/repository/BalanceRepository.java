package com.projetos.finance.repository;

import com.projetos.finance.Model.Balance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BalanceRepository extends JpaRepository<Balance, Long> {

    @Query(value = "SELECT * FROM balance b WHERE b.year_value = :year AND b.month_value = :month", nativeQuery = true)
    Optional<Balance> findByYearAndMonth(@Param("year") int year, @Param("month") int month);

    @Query(value = "SELECT * FROM balance b WHERE b.year_value = :year", nativeQuery = true)
    List<Balance> findBalancesByYear(@Param("year") int year);

    @Query(value = "SELECT * FROM balance b WHERE b.month_value = :month", nativeQuery = true)
    Balance findBalanceByMonth(@Param("month") int month);
//    @Query("SELECT SUM(b.totalIncomes) FROM Balance b WHERE b.year = :year")
//    Double findTotalIncomesByYear(@Param("year") int year);
}
