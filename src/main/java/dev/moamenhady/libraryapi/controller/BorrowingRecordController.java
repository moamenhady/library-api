package dev.moamenhady.libraryapi.controller;

import dev.moamenhady.libraryapi.entity.BorrowingRecord;
import dev.moamenhady.libraryapi.service.BorrowingRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class BorrowingRecordController {
    private final BorrowingRecordService borrowingRecordService;

    @Autowired
    public BorrowingRecordController(BorrowingRecordService borrowingRecordService) {
        this.borrowingRecordService = borrowingRecordService;
    }

    @PostMapping("borrow/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> createBorrowingRecord(@PathVariable Long bookId,
                                                                 @PathVariable Long patronId) {
        try {
            Optional<BorrowingRecord> addedRecord = borrowingRecordService.createRecord(bookId, patronId);
            return addedRecord.map(record -> new ResponseEntity<>(record, HttpStatus.CREATED))
                    .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("return/{bookId}/patron/{patronId}")
    public ResponseEntity<BorrowingRecord> returnBorrowedBook(@PathVariable Long bookId,
                                                              @PathVariable Long patronId) {
        return borrowingRecordService.returnBook(bookId, patronId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
