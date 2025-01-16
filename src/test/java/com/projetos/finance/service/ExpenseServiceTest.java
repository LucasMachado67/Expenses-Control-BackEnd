package com.projetos.finance.service;

import com.projetos.finance.Model.Expense;
import com.projetos.finance.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExpenseServiceTest {


    @Mock
    private ExpenseRepository repository;

    @Mock
    private ErrorAndValidationService validation;

    @InjectMocks
    private ExpenseService service;

    private Expense expense;

    @BeforeEach
    public void setup(){
        expense = new Expense(
                "Car",
                "broken part",
                1,
                120.0,
                2025,
                1,
                "emergency"
        );
    }

    @Test
    @DisplayName("Given Expense Object When Save Expense Then Return Expense Object")
    void testGivenExpenseObject_WhenSaveExpense_ThenReturnExpenseObject(){


        //Given / Arrange
        given(repository.save(expense)).willReturn(expense);

        //When / Act
        Expense savedExpense = service.newExpense(expense);
        //Then / Assert

        assertNotNull(savedExpense);
        assertEquals(120.0, savedExpense.getAmount());
        assertEquals("Car", savedExpense.getExpenseName());
        assertEquals("emergency", savedExpense.getCategory());
        assertEquals("broken part", savedExpense.getDescription());

    }

    @Test
    @DisplayName("Given Null Parameters To Expense Object When Save Expense Then Throw Error ")
    void testGivenNullParametersToExpenseObject_WhenSaveExpense_ThenThrowError(){
        //Given / Arrange
        Expense invalidExpense = new Expense(
                "",
                "",
                1,
                7000.0,
                2025,
                1,
                "payment"
        );

        doThrow(new IllegalArgumentException("Field 'Expense Name' is not filled"))
                .when(validation).validateDataMissingFromExpenseObject(any(Expense.class));
        // When / Act & Then / Assert
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.newExpense(invalidExpense)
        );
        assertEquals("Field 'Expense Name' is not filled", exception.getMessage());
    }

    @Test
    @DisplayName("Given Expense List When AllExpenses Then Return ListExpenses")
    void testGivenAllExpense_WhenAllExpense_ThenReturnListExpense(){
        //Given / Arrange
        Expense expense1 = new Expense(
                "pizza",
                "eating",
                1,
                90.0,
                2025,
                1,
                "Food"
        );
        given(repository.findAll()).willReturn(List.of(expense,expense1));
        //When / Act
        List<Expense> expenseList = service.allExpenses();
        //Then / Assert
        assertNotNull(expenseList);
        assertEquals(2, expenseList.size());
    }

    @Test
    @DisplayName("Given ExpenseId When FindById Then Return Expense Object")
    void testGivenExpenseId_WhenFindById_ThenReturnExpenseObject(){
        //Given / Arrange

        expense.setId(1L);
        given(repository.findById(1L)).willReturn(Optional.of(expense));
        //When / Act
        Expense expenseOptional = service.findExpenseById(1L);
        //Then / Assert
        assertNotNull(expenseOptional);
        assertEquals(1L, expenseOptional.getId());
        assertEquals("emergency", expenseOptional.getCategory());
    }

    @Test
    @DisplayName("Given ExpenseId When updateExpense Then Return Expense Object")
    void testGivenExpenseId_WhenUpdateExpense_ThenReturnUpdatedExpenseObject(){
        //Given / Arrange

        expense.setId(1L);
        given(repository.findById(1L)).willReturn(Optional.of(expense));
        //When / Act
        Expense expense1 = service.findExpenseById(1L);
        expense1.setExpenseName("Bike");
        expense1.setQuantity(21);
        //Then / Assert
        assertNotNull(expense1);
        assertEquals(21, expense1.getQuantity());
        assertEquals("Bike", expense1.getExpenseName());
    }

    @Test
    @DisplayName("Given ExpenseId When DeleteExpense Then Return Nothing")
    void testGivenExpenseId_WhenDeleteExpense_ThenReturnNothing(){
        //Given / Arrange

        Long expenseId = 1L;
        willDoNothing().given(repository).deleteById(expenseId);
        //When / Act
        service.deleteExpense(expenseId);
        //Then / Assert
        verify(repository, times(1)).deleteById(expenseId);
    }
}
