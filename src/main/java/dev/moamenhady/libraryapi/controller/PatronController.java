package dev.moamenhady.libraryapi.controller;

import dev.moamenhady.libraryapi.entity.Patron;
import dev.moamenhady.libraryapi.service.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patrons")
public class PatronController {
    private final PatronService patronService;

    @Autowired
    public PatronController(PatronService patronService) {
        this.patronService = patronService;
    }

    @GetMapping()
    public ResponseEntity<List<Patron>> getAllPatrons() {
        List<Patron> patrons = patronService.getAllPatrons();
        if (patrons.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(patrons);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patron> getPatronById(@PathVariable Long id) {
        return patronService.getPatronById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<Patron> createPatron(@RequestBody Patron patron) {
        Patron addedPatron = patronService.createPatron(patron);
        return new ResponseEntity<>(addedPatron, HttpStatus.CREATED);
    }

    @PutMapping("{id}")
    public ResponseEntity<Patron> updatePatron(@PathVariable Long id, @RequestBody Patron patron) {
        return patronService.updatePatron(id, patron)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable Long id) {
        if (patronService.deletePatron(id)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
