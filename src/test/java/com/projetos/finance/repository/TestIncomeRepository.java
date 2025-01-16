package com.projetos.finance.repository;

import com.projetos.finance.Model.Income;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class TestIncomeRepository {

    @Autowired
    private IncomeRepository repository;

    private Income income0;

    @BeforeEach
    public void setup(){
        income0 = new Income(
            "God of War |||",
            "A PC game",
            1,
            200.0,
            2025,
            1,
            "Entertainment"
        );
    }

    @DisplayName("Given Income Object When Save Then Return Saved Person")
    @Test
    void testGivenIncomeObject_WhenSave_ThenReturnSavedPerson(){

        //Given / Arrange

        //When / Act
        Income savedIncome = repository.save(income0);

        //Then / Assert
        assertNotNull(savedIncome);
        assertEquals(income0.getIncomeName(), savedIncome.getIncomeName());
        assertTrue(savedIncome.getId() > 0);
    }

    @DisplayName("Given Income List When FindAll Then Return Income List")
    @Test
    void testGivenIncomeList_WhenFindAll_ThenReturnIncomeList(){

        //Given / Arrange
        Income income1 = new Income(
                "Salary",
                "Payment from the job",
                1,
                3000.00,
                2025,
                1,
                "payment"
        );
        repository.save(income0);
        repository.save(income1);
        //When / Act

        List<Income> incomeList = repository.findAll();

        //Then / Assert
        assertEquals(2, incomeList.size());
        assertFalse(incomeList.isEmpty());
    }

    @DisplayName("Given IncomeId When FindById Then Return Income Object")
    @Test
    void testGivenIncomeId_WhenFindById_ThenReturnIncomeObject(){

        //Given / Arrange

        repository.save(income0);
        //When / Act

        Income income= repository.findById(income0.getId()).get();

        //Then / Assert
        assertNotNull(income);
        assertEquals(income0.getId(), income.getId());
    }

    @DisplayName("Given IncomeId When Update Income Then Return Updated Income")
    @Test
    void testGivenIncomeId_WhenUpdateIncome_ThenReturnUpdatedIncome(){

        //Given / Arrange
        repository.save(income0);
        //When / Act
        Income savedIncome = repository.findById(income0.getId()).get();
        savedIncome.setIncomeName("crypto");
        savedIncome.setCategory("investment");

        Income updatedIncome = repository.save(savedIncome);
        //Then / Assert
        assertNotNull(updatedIncome);
        assertEquals("crypto", updatedIncome.getIncomeName());
        assertEquals("investment", updatedIncome.getCategory());
    }

    @DisplayName("Given IncomeId When DeleteById Then Return Nothing")
    @Test
    void testGivenIncomeId_WhenDeleteById_ThenReturnNothing(){

        //Given / Arrange

        repository.save(income0);
        //When / Act
        repository.deleteById(income0.getId());
        Optional<Income> optionalIncome = repository.findById(income0.getId());
        //Then / Assert
        assertTrue(optionalIncome.isEmpty());

    }

    @DisplayName("Given Amount When GetAmountAndMakeTotalIncome Then Return TotalIncome")
    @Test
    void testGivenAmount_WhenGetAmountAndMakeTotalIncome_ThenReturnTotalIncome(){

        //Given / Arrange
        Income income1 = new Income(
                "Salary",
                "Payment from the job",
                1,
                3000.00,
                2025,
                1,
                "payment"
        );
        repository.save(income0);
        repository.save(income1);
        //When / Act
        Double total = repository.getAmountAndMakeTotalIncome();
        //Then / Assert
        assertTrue(total > 0);
        assertEquals(3200.0, total);

    }
}
