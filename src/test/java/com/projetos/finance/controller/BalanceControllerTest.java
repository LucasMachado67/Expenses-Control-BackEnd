package com.projetos.finance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetos.finance.Model.Balance;

import com.projetos.finance.Model.Expense;
import com.projetos.finance.Model.Income;
import com.projetos.finance.service.BalanceService;
import com.projetos.finance.service.ExpenseService;
import com.projetos.finance.service.IncomeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest(BalanceController.class)
public class BalanceControllerTest {

    @MockitoBean
    private IncomeService incomeService;

    @MockitoBean
    private ExpenseService expenseService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private BalanceService service;

    private Balance balance;
    private Income income;
    private Expense expense;
    @BeforeEach
    public void setup(){
        balance = new Balance(
                LocalDate.now().getYear(),
                LocalDate.now().getMonthValue(),
                4000.0,
                3000.0
        );

        income = new Income(
                "Payment",
                "Monthly payment",
                1,
                7000.0,
                2025,
                1,
                "payment"
        );

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
    @DisplayName("test Given Year And Month When Select By Year And Month Then Return Balance")
    void testGivenYearAndMonth_WhenSelectByYearAndMonth_ThenReturnBalance() throws Exception{

        //Given / Arrange
        int year = 2025;
        int month = 2;
        given(service.findByYearAndMonth(year,month)).willReturn(Optional.of(balance));
        //When / Act
        ResultActions response = mockMvc.perform(get("/balance/yearAndMonth")
                .param("year", String.valueOf(year))
                .param("month", String.valueOf(month))
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(balance)));
        //Then / Assert
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.totalIncomes").value(balance.getTotalIncomes()))
                .andExpect(jsonPath("$.totalExpenses").value(balance.getTotalIncomes()))
                .andExpect(jsonPath("$.year").value(balance.getYear()))
                .andExpect(jsonPath("$.month").value(balance.getMonth()))
                .andExpect(jsonPath("$.balance").value(balance.getBalance()));
    }

    @Test
    @DisplayName("test Given Month When Select By Month Then Return Balance")
    void testGivenMonth_WhenSelectByMonth_ThenReturnBalance() throws Exception{

        //Given / Arrange
        int month = 1;
        given(service.findBalanceByMonth(month)).willReturn(balance);
        //When / Act
        ResultActions response = mockMvc.perform(get("/balance/month")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(balance)));
        //Then / Assert
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.totalIncomes", is(balance.getTotalIncomes())))
                .andExpect(jsonPath("$.totalExpenses", is(balance.getTotalExpenses())))
                .andExpect(jsonPath("$.year", is(balance.getYear())))
                .andExpect(jsonPath("$.month", is(balance.getMonth())))
                .andExpect(jsonPath("$.balance", is(balance.getBalance())));
    }

    @Test
    @DisplayName("test Given Year When Select By Year Then Return Balance")
    void testGivenYear_WhenSelectByYear_ThenReturnListBalance() throws Exception{

        //Given / Arrange
        int year = 2025;
        Balance balance1 = new Balance(
                LocalDate.now().getYear(),
                LocalDate.now().getMonthValue(),
                7500.0,
                3000.0
        );
        List<Balance> balances = new ArrayList<>();
        balances.add(balance);
        balances.add(balance1);
        given(service.findBalancesByYear(year)).willReturn(balances);
        //When / Act
        ResultActions response = mockMvc.perform(get("/balance/year")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(balance)));
        //Then / Assert
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()" , is(balances.size())));
    }

    @Test
    @DisplayName("test Given Balance Object When Create Balance Then Return Balance")
    void testGivenBalanceObject_WhenCreateBalance_ThenReturnBalance() throws Exception{

        //Given / Arrange
        given(service.addBalance(any(Balance.class)))
                .willAnswer((invocation -> invocation.getArgument(0)));
        //When / Act
        ResultActions response = mockMvc.perform(post("/balance/newBalance")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(balance)));
        //Then / Assert
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.totalIncomes", is(balance.getTotalIncomes())))
                .andExpect(jsonPath("$.totalExpenses", is(balance.getTotalExpenses())))
                .andExpect(jsonPath("$.year", is(balance.getYear())))
                .andExpect(jsonPath("$.month", is(balance.getMonth())))
                .andExpect(jsonPath("$.balance", is(balance.getBalance())));
    }

    @Test
    @DisplayName("test Given Total Balance When Get Total In Balance Then Return Total Balance")
    void testGivenTotalBalance_WhenGetTotalInBalance_ThenReturnTotalBalance() throws Exception{
        //Given / Arrange
        balance.setBalance(incomeService.getTotalIncome(), expenseService.getTotalExpense());
        given(incomeService.getTotalIncome() - expenseService.getTotalExpense()).willReturn(balance.getBalance());
        //When / Act
        ResultActions response = mockMvc.perform(get("/balance/total")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(balance)));
        //Then / Assert
        response.andExpect(status().isOk())
                .andDo(print());
    }
}
