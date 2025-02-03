package com.projetos.finance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetos.finance.Model.Income;
import com.projetos.finance.service.IncomeService;
import org.springframework.http.MediaType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.mockito.BDDMockito.*;

@WebMvcTest(IncomeController.class)
public class IncomeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private IncomeService service;

    private Income income;

    @BeforeEach
    public void setup(){
        income = new Income("Payment",
                "Monthly payment",
                1,
                7000.0,
                2025,
                1,
                "payment"
        );
    }

    @Test
    @DisplayName("Given Income Object When CreateIncome Then Return Saved Income")
    void testGivenIncomeObject_WhenCreateIncome_ThenReturnSavedIncome() throws Exception{
        //Given / Arrange
        given(service.newIncome(any(Income.class))).
                    willAnswer((invocation) -> invocation.getArgument(0));
        //When / Act
        ResultActions response = mockMvc.perform(post("/Incomes/newIncome")
        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(income)));
        //Then / Assert
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.incomeName", is(income.getIncomeName())))
                .andExpect(jsonPath("$.description", is(income.getDescription())))
                .andExpect(jsonPath("$.amount", is(income.getAmount())))
                .andExpect(jsonPath("$.quantity", is(income.getQuantity())))
                .andExpect(jsonPath("$.year", is(income.getYear())))
                .andExpect(jsonPath("$.month", is(income.getMonth())))
                .andExpect(jsonPath("$.category", is(income.getCategory())));
    }

    @Test
    @DisplayName("test Given All Incomes When FindAll Then Return Income List")
    void testGivenAllIncomes_WhenFindAll_ThenReturnIncomeList() throws Exception{
        //Given / Arrange
        Income income0 = new Income("Investment",
                "Monthly gains from investment",
                1,
                3000.0,
                2025,
                1,
                "investment");
        List<Income> incomeList = new ArrayList<>();
        incomeList.add(income);
        incomeList.add(income0);
        given(service.allIncomes()).willReturn(incomeList);
        //When / Act
        ResultActions response = mockMvc.perform(get("/Incomes/All")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(income)));
        //Then / Assert
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(incomeList.size())));
    }

    @Test
    @DisplayName("test iven Income Id When FindById Then Return Income Object")
    void testGivenIncomeId_WhenFindById_ThenReturnIncomeObject() throws Exception{
        //Given / Arrange
        Long incomeId = 1L;
        given(service.findIncomeById(incomeId)).willReturn(income);
        //When / Act
        ResultActions response = mockMvc.perform(get("/Incomes/All/{id}", incomeId));
        //Then / Assert
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.incomeName", is(income.getIncomeName())))
                .andExpect(jsonPath("$.year", is(income.getYear())));
    }

    @Test
    @DisplayName("test Given Invalid Income Id When FindById Then Return Not Found")
    void testGivenInvalidIncomeId_WhenFindById_ThenReturnNotFound() throws Exception{
        //Given / Arrange
        Long incomeId = 1L;
        given(service.findIncomeById(incomeId)).willThrow(RuntimeException.class);
        //When / Act
        ResultActions response = mockMvc.perform(get("/Incomes/All/{id}", incomeId));
        //Then / Assert
        response.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("test Given Income Id When Update Then Return Updated Income Object")
    void testGivenIncomeId_WhenUpdate_ThenReturnUpdatedIncomeObject() throws Exception{
        //Given / Arrange
        Long incomeId = 1L;
        given(service.findIncomeById(incomeId)).willThrow(RuntimeException.class);
        given(service.updateIncome(any(Income.class))).willAnswer((invocation -> invocation.getArgument(0)));

        //When / Act
        Income updatedIncome = new Income(
                "Investment",
                "Monthly Investment",
                1,
                3000.0,
                2025,
                1,
                "Investment"
        );
        ResultActions response = mockMvc.perform(put("/Incomes/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(updatedIncome)));
        //Then / Assert
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.incomeName", is(updatedIncome.getIncomeName())))
                .andExpect(jsonPath("$.year", is(updatedIncome.getYear())));
    }

    @Test
    @DisplayName("test Given Invalid Income Id When Update Then Return Not Found Updated Income Object")
    void testGivenInvalidIncomeId_WhenUpdate_ThenReturnNotFoundUpdatedIncomeObject() throws Exception{
        //Given / Arrange
        Long incomeId = 1L;
        given(service.findIncomeById(incomeId)).willThrow(RuntimeException.class);
        given(service.updateIncome(any(Income.class))).willAnswer((invocation -> invocation.getArgument(1)));
        //When / Act
        Income updatedIncome = new Income(
                "Investment",
                "Monthly Investment",
                1,
                3000.0,
                2025,
                1,
                "Investment"
        );
        ResultActions response = mockMvc.perform(put("/Incomes/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(updatedIncome)));
        //Then / Assert
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("test Given IncomeId When Delete Income Then Return Nothing")
    void testGivenIncomeId_WhenDeleteIncome_ThenReturnNothing() throws Exception{
        //Given / Arrange
        Long incomeId = 1L;
        willDoNothing().given(service).deleteIncome(incomeId);
        //When / Act
        ResultActions response = mockMvc.perform(delete("/Incomes/All/{id}", incomeId));
        //Then / Assert
        response.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("test Given Total Income When Get Total Income Then Return Total Income")
    void testGivenTotalIncome_WhenGetTotalIncome_ThenReturnTotalIncome() throws Exception{
        //Given / Arrange
        Double totalIncome =100000.0;
        given(service.getTotalIncome()).willReturn(totalIncome);

        //When / Act
        ResultActions response = mockMvc.perform(get("/Incomes/number"));
        //Then / Assert
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().string("100000.0"));
    }

    @Test
    @DisplayName("test Given Category Name When Select Per Category Then Return List Incomes")
    void testGivenCategoryName_WhenSelectPerCategory_ThenReturnListIncomes() throws Exception{
        //Given / Arrange
        String category = "payment";
        Income income0 = new Income(
                "Investment",
                "Monthly Investment",
                1,
                3000.0,
                2025,
                1,
                "payment"
        );
        List<Income> incomeList = new ArrayList<>();
        if(income.getCategory().equals(category)){
            incomeList.add(income);
        }
        if(income0.getCategory().equals(category)){
            incomeList.add(income0);
        }
        given(service.selectIncomesPerCategory(category)).willReturn(incomeList);
        //When / Act
        ResultActions response = mockMvc.perform(get("/Incomes/{category}", category));
        //Then / Assert
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(incomeList.size())));
    }
}
