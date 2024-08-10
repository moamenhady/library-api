package dev.moamenhady.libraryapi.repository;

import dev.moamenhady.libraryapi.entity.BorrowingRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {
    Optional<BorrowingRecord> findBorrowingRecordByBookIdAndPatronId(Long bookId, Long patronId);
}
