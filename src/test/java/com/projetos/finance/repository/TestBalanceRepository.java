package com.projetos.finance.repository;

import com.projetos.finance.Model.Balance;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class TestBalanceRepository {

    @Autowired
    private BalanceRepository repository;

    private Balance balance;

    @BeforeEach
    public void setup(){

        balance = new Balance(
                2025,
                1,
                5000.0,
                3750.0
        );
    }

    @Test
    @DisplayName("Given Balance Object When Save Then Return Saved Balance")
    void testGivenBalanceObject_WhenSave_ThenReturnSavedBalance(){

        //Given / Arrange

        //When / Act
        Balance savedBalance = repository.save(balance);

        //Then / Assert
        assertNotNull(savedBalance);
        assertEquals(balance.getBalance(), savedBalance.getBalance());
        assertTrue(savedBalance.getYear() > 0);
    }

    @Test
    @DisplayName("Given Year And Month When FindByYearAndMonth Then Return Balance")
    void testGivenYearAndMonth_WhenFindByYearAndMonth_ThenReturnBalance(){

        //Given / Arrange
        repository.save(balance);

        //When / Act
        repository.findByYearAndMonth(balance.getYear(), balance.getMonth());
        //Then / Assert
        assertNotNull(balance);
        assertEquals(3750.0, balance.getTotalExpenses());
        assertEquals(5000.0, balance.getTotalIncomes());
        assertEquals(1250.0, balance.getBalance());
    }

    @Test
    @DisplayName("Given Year When FindBalancesByYear Then Return Balance List")
    void testGivenYear_WhenFindBalancesByYear_ThenReturnBalanceList(){

        //Given / Arrange
        Balance balance0 = new Balance(
                2025,
                2,
                7500.0,
                5500.0
        );
        repository.save(balance);
        repository.save(balance0);
        //When / Act
        List<Balance> balanceList = repository.findBalancesByYear(2025);
        //Then / Assert
        assertFalse(balanceList.isEmpty());
        assertEquals(2, balanceList.size());
    }

    @Test
    @DisplayName("Given Month When FindBalanceByMonth Then Return Balance")
    void testGivenMonth_WhenFindBalanceByMonth_ThenReturnBalance(){

        //Given / Arrange

        repository.save(balance);
        //When / Act
        Balance balance1 = repository.findBalanceByMonth(1);
        //Then / Assert
        assertNotNull(balance1);
        assertEquals(2025, balance1.getYear());
        assertEquals(1, balance1.getMonth());
        assertEquals(5000.0, balance1.getTotalIncomes());
        assertEquals(3750.0, balance1.getTotalExpenses());
        assertEquals(1250.0, balance1.getBalance());
    }

}
