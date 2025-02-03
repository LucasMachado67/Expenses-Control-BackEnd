package com.projetos.finance.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.projetos.finance.Model.Expense;
import com.projetos.finance.service.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ExpenseController.class)
public class ExpenseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    @MockitoBean
    private ExpenseService service;

    private Expense expense;

    @BeforeEach
    public void setup(){
        expense = new Expense("broken window",
                "broken car window",
                1,
                500.0,
                2025,
                1,
                "Emergency"
        );
    }

    @Test
    @DisplayName("Given Expense Object When CreateExpense Then Return Saved Expense")
    void testGivenExpenseObject_WhenCreateExpense_ThenReturnSavedExpense() throws Exception{
        //Given / Arrange
        given(service.newExpense(any(Expense.class))).
                willAnswer((invocation) -> invocation.getArgument(0));
        //When / Act
        ResultActions response = mockMvc.perform(post("/Expenses/newExpense")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(expense)));
        //Then / Assert
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.expenseName", is(expense.getExpenseName())))
                .andExpect(jsonPath("$.description", is(expense.getDescription())))
                .andExpect(jsonPath("$.amount", is(expense.getAmount())))
                .andExpect(jsonPath("$.quantity", is(expense.getQuantity())))
                .andExpect(jsonPath("$.year", is(expense.getYear())))
                .andExpect(jsonPath("$.month", is(expense.getMonth())))
                .andExpect(jsonPath("$.category", is(expense.getCategory())));
    }

    @Test
    @DisplayName("test Given All Expense When FindAll Then Return Expense List")
    void testGivenAllExpenses_WhenFindAll_ThenReturnExpenseList() throws Exception{
        //Given / Arrange
        Expense expense0 = new Expense("Monthly Food",
                "Food for the entire month",
                1,
                1250.0,
                2025,
                1,
                "Food");
        List<Expense> expenseList = new ArrayList<>();
        expenseList.add(expense);
        expenseList.add(expense0);
        given(service.allExpenses()).willReturn(expenseList);
        //When / Act
        ResultActions response = mockMvc.perform(get("/Expenses/All")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsString(expense)));
        //Then / Assert
        response.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.size()", is(expenseList.size())));
    }

    @Test
    @DisplayName("test iven Expense Id When FindById Then Return Expense Object")
    void testGivenExpenseId_WhenFindById_ThenReturnExpenseObject() throws Exception{
        //Given / Arrange
        Long expenseId = 1L;
        given(service.findExpenseById(expenseId)).willReturn(expense);
        //When / Act
        ResultActions response = mockMvc.perform(get("/Expenses/All/{id}", expenseId));
        //Then / Assert
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.expenseName", is(expense.getExpenseName())))
                .andExpect(jsonPath("$.year", is(expense.getYear())));
    }

    @Test
    @DisplayName("test Given Invalid Expense Id When FindById Then Return Not Found")
    void testGivenInvalidExpenseId_WhenFindById_ThenReturnNotFound() throws Exception{
        //Given / Arrange
        Long expenseId = 1L;
        given(service.findExpenseById(expenseId)).willThrow(RuntimeException.class);
        //When / Act
        ResultActions response = mockMvc.perform(get("/Expenses/All/{id}", expenseId));
        //Then / Assert
        response.andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("test Given Expense Id When Update Then Return Updated Expense Object")
    void testGivenExpenseId_WhenUpdate_ThenReturnUpdatedExpenseObject() throws Exception{
        //Given / Arrange
        Long expenseId = 1L;
        given(service.findExpenseById(expenseId)).willThrow(RuntimeException.class);
        given(service.updateExpense(any(Expense.class))).willAnswer((invocation -> invocation.getArgument(0)));

        //When / Act
        Expense updatedExpense = new Expense(
                "Monthly Food",
                "Food for the entire month",
                1,
                1250.0,
                2025,
                1,
                "Food"
        );
        ResultActions response = mockMvc.perform(put("/Expenses/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(updatedExpense)));
        //Then / Assert
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.expenseName", is(updatedExpense.getExpenseName())))
                .andExpect(jsonPath("$.year", is(updatedExpense.getYear())));
    }

    @Test
    @DisplayName("test Given Invalid Expense Id When Update Then Return Not Found Updated Expense Object")
    void testGivenInvalidExpenseId_WhenUpdate_ThenReturnNotFoundUpdatedExpenseObject() throws Exception{
        //Given / Arrange
        Long expenseId = 1L;
        given(service.findExpenseById(expenseId)).willThrow(RuntimeException.class);
        given(service.updateExpense(any(Expense.class))).willAnswer((invocation -> invocation.getArgument(1)));
        //When / Act
        Expense updatedExpense = new Expense(
                "Monthly Food",
                "Food for the entire month",
                1,
                1250.0,
                2025,
                1,
                "Food"
        );
        ResultActions response = mockMvc.perform(put("/Expenses/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(mapper.writeValueAsBytes(updatedExpense)));
        //Then / Assert
        response.andExpect(status().isNotFound())
                .andDo(print());
    }

    @Test
    @DisplayName("test Given ExpenseId When Delete Expense Then Return Nothing")
    void testGivenExpenseId_WhenDeleteExpense_ThenReturnNothing() throws Exception{
        //Given / Arrange
        Long expenseId = 1L;
        willDoNothing().given(service).deleteExpense(expenseId);
        //When / Act
        ResultActions response = mockMvc.perform(delete("/Expenses/All/{id}", expenseId));
        //Then / Assert
        response.andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    @DisplayName("test Given Category Name When Select Per Category Then Return List Expenses")
    void testGivenCategoryName_WhenSelectPerCategory_ThenReturnListExpenses() throws Exception{
        //Given / Arrange
        String category = "Food";
        Expense expense0 = new Expense(
                "Monthly Food",
                "Food for the entire month",
                1,
                1250.0,
                2025,
                1,
                "Food"
        );
        List<Expense> expenseList = new ArrayList<>();
        if(expense.getCategory().equals(category)){
            expenseList.add(expense);
        }
        if(expense0.getCategory().equals(category)){
            expenseList.add(expense0);
        }
        given(service.selectExpensesPerCategory(category)).willReturn(expenseList);
        //When / Act
        ResultActions response = mockMvc.perform(get("/Expenses/{category}", category));
        //Then / Assert
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()", is(expenseList.size())));
    }
}
