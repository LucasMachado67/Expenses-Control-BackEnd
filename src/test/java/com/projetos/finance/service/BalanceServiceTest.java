package com.projetos.finance.service;

import static org.mockito.BDDMockito.*;
import static org.junit.jupiter.api.Assertions.*;
import com.projetos.finance.Model.Balance;
import com.projetos.finance.Model.Expense;
import com.projetos.finance.Model.Income;
import com.projetos.finance.repository.BalanceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class BalanceServiceTest {

    @Mock
    private BalanceRepository repository;

    @InjectMocks
    private BalanceService service;

    @Mock
    private IncomeService incomeService;

    @Mock
    private ExpenseService expenseService;

    private Balance balance;


    @BeforeEach
    public void setup(){
        balance = new Balance(
                LocalDate.now().getYear(),
                LocalDate.now().getMonthValue(),
                4000.0,
                3000.0
        );


    }




    @Test
    @DisplayName("Balance Object When Save Then Return Balance Object")
    void testGivenBalanceObject_WhenSave_ThenReturnBalanceObject(){
        //Given / Arrange
        given(incomeService.getTotalIncome()).willReturn(balance.getTotalIncomes());
        given(expenseService.getTotalExpense()).willReturn(balance.getTotalExpenses());
        given(repository.save(balance)).willReturn(balance);
        //When / Act
        Balance balance1 = service.addBalance(balance);

        //Then / Assert
        assertNotNull(balance1);
        assertEquals(LocalDate.now().getYear(), balance1.getYear());
        assertEquals(LocalDate.now().getMonthValue(), balance1.getMonth());
        assertEquals(4000.0, balance1.getTotalIncomes());
        assertEquals(3000.0, balance1.getTotalExpenses());
        assertEquals(-1000.0, balance1.getBalance());
    }

    @Test
    @DisplayName("given Balance Year And Month When FindByYearAndMonth Then Return Balance Object")
    void testGivenBalanceYearAndMonth_WhenFindByYearAndMonth_ThenReturnBalanceObject(){
        //Given / Arrange

        given(repository.findByYearAndMonth(balance.getYear(), balance.getMonth())).willReturn(Optional.ofNullable(balance));
        //When / Act
        Optional<Balance> balance1 = service.findByYearAndMonth(balance.getYear(), balance.getMonth());

        //Then / Assert
        assertNotNull(balance1);
        assertEquals(LocalDate.now().getYear(), 2025);
        assertEquals(LocalDate.now().getMonthValue(), 2);
        assertTrue(balance1.isPresent());
    }

    @Test
    @DisplayName("given Balance Year When Find By Year Then Return List Balance")
    void testGivenBalanceYear_WhenFindByYear_ThenReturnListBalance(){
        //Given / Arrange
        Balance balance1 = new Balance(
                LocalDate.now().getYear(),
                LocalDate.now().getMonthValue(),
                4700.0,
                5000.0
        );
        List<Balance> listBalance = new ArrayList<>();
        listBalance.add(balance1);
        listBalance.add(balance);

        given(repository.findBalancesByYear(balance.getYear())).willReturn(listBalance);
        //When / Act
        List<Balance> balances = service.findBalancesByYear(balance.getYear());

        //Then / Assert
        assertNotNull(balances);
        assertEquals(2, balances.size());
    }

    @Test
    @DisplayName("Given Balance Month When FindByMonth Then Return Balance Object")
    void testGivenBalanceMonth_WhenFindByMonth_ThenReturnBalanceObject(){
        //Given / Arrange

        given(repository.findBalanceByMonth(balance.getMonth())).willReturn(balance);
        //When / Act
        Balance balance1 = service.findBalanceByMonth(balance.getMonth());

        //Then / Assert
        assertNotNull(balance1);
        assertEquals(LocalDate.now().getYear(), balance1.getYear());
        assertEquals(LocalDate.now().getMonthValue(), balance1.getMonth());
        assertEquals(4000.0, balance1.getTotalIncomes());
        assertEquals(3000.0, balance1.getTotalExpenses());
        assertEquals(1000.0, balance1.getBalance());
    }
}
