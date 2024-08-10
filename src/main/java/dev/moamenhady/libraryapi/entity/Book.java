package dev.moamenhady.libraryapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Data
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Title can't be blank")
    @Size(max = 127, message = "Title can't exceed 127 characters")
    private String title;

    @NotBlank(message = "Author can't be blank")
    @Size(max = 127, message = "Author can't exceed 127 characters")
    private String author;

    private Integer publicationYear;

    private Integer pageCount;

    @Column(nullable = false, unique = true, length = 13)
    private String isbn;

    private boolean isBorrowed;

    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, orphanRemoval = true, fetch = LAZY)
    private Set<BorrowingRecord> borrowingRecords;

    @Column(updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;
}
