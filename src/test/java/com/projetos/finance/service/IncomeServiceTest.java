package com.projetos.finance.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.*;
import com.projetos.finance.Model.Income;
import com.projetos.finance.repository.IncomeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
class IncomeServiceTest {

    @Mock
    private IncomeRepository repository;

    @Mock
    private ErrorAndValidationService validation;

    @InjectMocks
    private IncomeService service;

    private Income income;

    @BeforeEach
    public void setup(){
        income = new Income(
            "Payment",
            "Monthly payment",
            1,
            7000.0,
            2025,
            1,
            "payment"
        );
    }

    @Test
    @DisplayName("Given Income Object When Save Income Then Return Income Object")
    void testGivenIncomeObject_WhenSaveIncome_ThenReturnIncomeObject(){


        //Given / Arrange
        given(repository.save(income)).willReturn(income);

        //When / Act
        Income savedIncome = service.newIncome(income);
        //Then / Assert

        assertNotNull(savedIncome);
        assertEquals(7000.0, savedIncome.getAmount());
        assertEquals("Payment", savedIncome.getIncomeName());
        assertEquals("payment", savedIncome.getCategory());
        assertEquals("Monthly payment", savedIncome.getDescription());

    }

    @Test
    @DisplayName("Given Null Parameters To Income Object When Save Income Then Throw Error ")
    void testGivenNullParametersToIncomeObject_WhenSaveIncome_ThenThrowError(){
        //Given / Arrange
        Income invalidIncome = new Income(
                "",
                "",
                1,
                7000.0,
                2025,
                1,
                "payment"
        );

        doThrow(new IllegalArgumentException("Field 'Income Name' is not filled"))
                .when(validation).validateDataMissingFromIncomeObject(any(Income.class));
        // When / Act & Then / Assert
        Exception exception = assertThrows(
                IllegalArgumentException.class,
                () -> service.newIncome(invalidIncome)
        );
        assertEquals("Field 'Income Name' is not filled", exception.getMessage());
    }

    @Test
    @DisplayName("Given Income List When AllIncomes Then Return ListIncomes")
    void testGivenAllIncomes_WhenAllIncomes_ThenReturnListIncomes(){
        //Given / Arrange
        Income income1 = new Income(
                "Investment",
                "Crypto",
                1,
                12387.0,
                2025,
                1,
                "Investment"
        );
        given(repository.findAll()).willReturn(List.of(income,income1));
        //When / Act
        List<Income> incomeList = service.allIncomes();
        //Then / Assert
        assertNotNull(incomeList);
        assertEquals(2, incomeList.size());
    }

    @Test
    @DisplayName("Given IncomeId When FindById Then Return Income Object")
    void testGivenIncomeId_WhenFindById_ThenReturnIncomeObject(){
        //Given / Arrange

        income.setId(1L);
        given(repository.findById(1L)).willReturn(Optional.of(income));
        //When / Act
        Income incomeOptional = service.findIncomeById(1L);
        //Then / Assert
        assertNotNull(incomeOptional);
        assertEquals(1L, incomeOptional.getId());
        assertEquals("payment", incomeOptional.getCategory());
    }

    @Test
    @DisplayName("Given IncomeId When FindById Then Return Income Object")
    void testGivenIncomeId_WhenUpdateIncome_ThenReturnUpdatedIncomeObject(){
        //Given / Arrange

        income.setId(1L);
        given(repository.findById(1L)).willReturn(Optional.of(income));
        //When / Act
        Income income1 = service.findIncomeById(1L);
        income1.setIncomeName("New Payment");
        income1.setQuantity(21);
        //Then / Assert
        assertNotNull(income1);
        assertEquals(21, income1.getQuantity());
        assertEquals("New Payment", income1.getIncomeName());
    }

    @Test
    @DisplayName("Given IncomeId When DeleteIncome Then Return Nothing")
    void testGivenIncomeId_WhenDeleteIncome_ThenReturnNothing(){
        //Given / Arrange

        Long incomeId = 1L;
        willDoNothing().given(repository).deleteById(incomeId);
        //When / Act
        service.deleteIncome(incomeId);
        //Then / Assert
        verify(repository, times(1)).deleteById(incomeId);
    }

}
