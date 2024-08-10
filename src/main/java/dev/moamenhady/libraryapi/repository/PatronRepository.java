package dev.moamenhady.libraryapi.repository;

import dev.moamenhady.libraryapi.entity.Patron;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatronRepository extends JpaRepository<Patron, Long> {
    Optional<Patron> findPatronByEmail(String email);
}
