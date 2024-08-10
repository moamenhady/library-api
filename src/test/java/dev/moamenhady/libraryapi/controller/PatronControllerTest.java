package dev.moamenhady.libraryapi.controller;

import dev.moamenhady.libraryapi.entity.Patron;
import dev.moamenhady.libraryapi.service.PatronService;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PatronControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PatronService patronService;

    @InjectMocks
    private PatronController patronController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(patronController)
                .setControllerAdvice(new ExceptionHandlerExceptionResolver())
                .build();
    }

    @Test
    public void testGetAllPatrons() throws Exception {
        List<Patron> patrons = new ArrayList<>();
        patrons.add(new Patron());

        when(patronService.getAllPatrons()).thenReturn(patrons);

        mockMvc.perform(get("/api/patrons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetAllPatrons_NoContent() throws Exception {
        when(patronService.getAllPatrons()).thenReturn(new ArrayList<>());

        mockMvc.perform(get("/api/patrons"))
                .andExpect(status().isNoContent());
    }

    @Test
    public void testGetPatronById() throws Exception {
        Patron patron = new Patron();
        when(patronService.getPatronById(1L)).thenReturn(Optional.of(patron));

        mockMvc.perform(get("/api/patrons/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void testGetPatronById_NotFound() throws Exception {
        when(patronService.getPatronById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/patrons/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreatePatron() throws Exception {
        Patron patron = new Patron();
        when(patronService.createPatron(any(Patron.class))).thenReturn(patron);

        mockMvc.perform(post("/api/patrons")
                        .contentType("application/json")
                        .content("{\"name\": \"John Doe\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void testUpdatePatron() throws Exception {
        Patron patron = new Patron();
        when(patronService.updatePatron(eq(1L), any(Patron.class))).thenReturn(Optional.of(patron));

        mockMvc.perform(put("/api/patrons/1")
                        .contentType("application/json")
                        .content("{\"name\": \"Jane Doe\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());
    }

    @Test
    public void testUpdatePatron_NotFound() throws Exception {
        when(patronService.updatePatron(eq(1L), any(Patron.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/patrons/1")
                        .contentType("application/json")
                        .content("{\"name\": \"Jane Doe\"}"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testDeletePatron() throws Exception {
        when(patronService.deletePatron(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/patrons/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testDeletePatron_NotFound() throws Exception {
        when(patronService.deletePatron(1L)).thenReturn(false);

        mockMvc.perform(delete("/api/patrons/1"))
                .andExpect(status().isNotFound());
    }
}
