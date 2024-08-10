package dev.moamenhady.libraryapi.service;

import dev.moamenhady.libraryapi.entity.Book;
import dev.moamenhady.libraryapi.entity.BorrowingRecord;
import dev.moamenhady.libraryapi.entity.Patron;
import dev.moamenhady.libraryapi.repository.BorrowingRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class BorrowingRecordService {
    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookService bookService;
    private final PatronService patronService;

    @Autowired
    public BorrowingRecordService(BorrowingRecordRepository borrowingRecordRepository,
                                  BookService bookService,
                                  PatronService patronService) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookService = bookService;
        this.patronService = patronService;
    }

    @Transactional
    public Optional<BorrowingRecord> createRecord(Long bookId, Long patronId) {
        Optional<Book> book = bookService.getBookById(bookId);
        Optional<Patron> patron = patronService.getPatronById(patronId);

        if (book.isEmpty()) {
            throw new IllegalArgumentException("Book with ID " + bookId + " not found.");
        }
        if (book.get().isBorrowed()) {
            throw new IllegalArgumentException("Book with ID " + bookId + " is already borrowed.");
        }
        book.get().setBorrowed(true);
        bookService.updateBook(bookId, book.get());
        if (patron.isEmpty()) {
            throw new IllegalArgumentException("Patron with ID " + patronId + " not found.");
        }

        BorrowingRecord borrowingRecord = new BorrowingRecord();
        borrowingRecord.setBorrowDate(LocalDate.now());
        borrowingRecord.setBook(book.get());
        borrowingRecord.setPatron(patron.get());

        return Optional.of(borrowingRecordRepository.save(borrowingRecord));
    }

    @Transactional
    public Optional<BorrowingRecord> returnBook(Long bookId, Long patronId) {
        Optional<Book> book = bookService.getBookById(bookId);
        if (book.isEmpty()) {
            throw new IllegalArgumentException("Book with ID " + bookId + " not found.");
        }
        book.get().setBorrowed(false);
        bookService.updateBook(bookId, book.get());

        return borrowingRecordRepository.findBorrowingRecordByBookIdAndPatronIdAndReturnDateIsNull(bookId, patronId)
                .map(existingRecord -> {
                    existingRecord.setReturnDate(LocalDate.now());
                    return borrowingRecordRepository.save(existingRecord);
                });
    }
}
