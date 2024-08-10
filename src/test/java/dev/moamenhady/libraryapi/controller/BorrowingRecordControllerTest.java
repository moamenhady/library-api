package dev.moamenhady.libraryapi.controller;

import dev.moamenhady.libraryapi.entity.BorrowingRecord;
import dev.moamenhady.libraryapi.service.BorrowingRecordService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class BorrowingRecordControllerTest {

    private MockMvc mockMvc;

    @Mock
    private BorrowingRecordService borrowingRecordService;

    @InjectMocks
    private BorrowingRecordController borrowingRecordController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(borrowingRecordController)
                .setControllerAdvice(new ExceptionHandlerExceptionResolver())
                .build();
    }

    @Test
    public void testCreateBorrowingRecord() throws Exception {
        BorrowingRecord record = new BorrowingRecord();
        when(borrowingRecordService.createRecord(1L, 1L)).thenReturn(Optional.of(record));

        mockMvc.perform(post("/api/borrow/1/patron/1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void testCreateBorrowingRecord_BadRequest() throws Exception {
        when(borrowingRecordService.createRecord(1L, 1L)).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/borrow/1/patron/1"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateBorrowingRecord_NotFound() throws Exception {
        when(borrowingRecordService.createRecord(1L, 1L)).thenThrow(new IllegalArgumentException());

        mockMvc.perform(post("/api/borrow/1/patron/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testReturnBorrowedBook() throws Exception {
        BorrowingRecord record = new BorrowingRecord();
        when(borrowingRecordService.returnBook(1L, 1L)).thenReturn(Optional.of(record));

        mockMvc.perform(put("/api/return/1/patron/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void testReturnBorrowedBook_NotFound() throws Exception {
        when(borrowingRecordService.returnBook(1L, 1L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/return/1/patron/1"))
                .andExpect(status().isNotFound());
    }
}
