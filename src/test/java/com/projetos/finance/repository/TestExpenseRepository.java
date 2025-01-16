package com.projetos.finance.repository;


import com.projetos.finance.Model.Expense;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
class TestExpenseRepository {

    @Autowired
    private ExpenseRepository repository;

    private Expense expense0;

    @BeforeEach
    public void setup(){
        expense0 = new Expense(
                "Tv",
                "Smart TV for the bedroom",
                1,
                2350.0,
                2025,
                1,
                "leisure"
        );
    }

    @Test
    @DisplayName("Given Expense Object When Expense Save Then Return Saved Expense")
    void testGivenExpenseObject_WhenExpenseSave_ThenReturnSavedExpense(){

        //Given / Arrange
        //When / Act
        Expense savedExpense = repository.save(expense0);
        //Then / Assert

        assertNotNull(savedExpense);
        assertEquals(expense0.getExpenseName(), savedExpense.getExpenseName());
        assertTrue(savedExpense.getId() > 0);
    }

    @Test
    @DisplayName("Given FindAll When FindAll Expense Then Return List Expense")
    void testGivenFindAll_WhenFindAllExpense_ThenReturnListExpense(){

        //Given / Arrange
        Expense expense1 = new Expense(
                "Sofa",
                "Sofa for the restroom",
                1,
                3000.0,
                2025,
                1,
                "leisure"
        );
        repository.save(expense0);
        repository.save(expense1);
        //When / Act
        List<Expense> expenseList = repository.findAll();
        //Then / Assert

        assertNotNull(expenseList);
        assertEquals(2 ,expenseList.size());

    }

    @Test
    @DisplayName("Given Expense Id When FindById Then Return Expense Object")
    void testGivenExpenseId_WhenFindById_ThenReturnExpenseObject(){

        //Given / Arrange
        repository.save(expense0);
        //When / Act
        Expense expenseId = repository.findById(expense0.getId()).get();
        //Then / Assert
        assertNotNull(expenseId);
        assertEquals(expense0.getId(), expenseId.getId());

    }

    @Test
    @DisplayName("Given Expense Id When Update Expense Then Return Updated Expense")
    void testGivenExpenseId_WhenUpdateExpense_ThenReturnUpdatedExpense(){

        //Given / Arrange
        repository.save(expense0);
        //When / Act
        repository.findById(expense0.getId()).get();

        expense0.setExpenseName("Wardrobe");
        expense0.setDescription("A new one");
        //Then / Assert

        assertEquals("Wardrobe", expense0.getExpenseName());
        assertEquals("A new one", expense0.getDescription());

    }

    @Test
    @DisplayName("Given Expense Id Object When Delete Then Return Nothing")
    void testGivenExpenseId_WhenDelete_ThenReturnNothing(){

        //Given / Arrange
        repository.save(expense0);
        //When / Act
        repository.deleteById(expense0.getId());
        Optional<Expense> expense = repository.findById(expense0.getId());
        //Then / Assert

        assertTrue(expense.isEmpty());

    }

    @Test
    @DisplayName("Given Amount When GetAmountAndMakeTotalExpense Then Return TotalExpense")
    void testGivenAmount_WhenGetAmountAndMakeTotalExpense_ThenReturnTotalExpense(){

        //Given / Arrange
        Expense expense1 = new Expense(
                "Sofa",
                "Sofa for the restroom",
                1,
                3000.0,
                2025,
                1,
                "leisure"
        );
        repository.save(expense0);
        repository.save(expense1);

        //When / Act
        Double total = repository.getAmountAndMakeTotalExpense();
        //Then / Assert

        assertTrue(total > 0);
        assertEquals(5350.0, total);

    }

}
