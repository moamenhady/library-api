package dev.moamenhady.libraryapi.service;

import dev.moamenhady.libraryapi.entity.Patron;
import dev.moamenhady.libraryapi.repository.PatronRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class PatronService {
    private final PatronRepository patronRepository;

    @Autowired
    public PatronService(PatronRepository patronRepository) {
        this.patronRepository = patronRepository;
    }

    public List<Patron> getAllPatrons() {
        return patronRepository.findAll();
    }

    public Optional<Patron> getPatronById(Long id) {
        return patronRepository.findById(id);
    }

    public Patron createPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    @Transactional
    public Optional<Patron> updatePatron(Long id, Patron patron) {
        return patronRepository.findById(id).map(existingPatron -> {
            existingPatron.setName(patron.getName());
            existingPatron.setEmail(patron.getEmail());
            existingPatron.setPhoneNumber(patron.getPhoneNumber());
            return patronRepository.save(existingPatron);
        });
    }

    @Transactional
    public boolean deletePatron(Long id) {
        if (patronRepository.existsById(id)) {
            patronRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
