package dev.moamenhady.libraryapi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Data
public class Patron {

    public static final String ROLE_USER = "ROLE_USER";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name can't be blank")
    @Size(max = 127, message = "Name can't exceed 127 characters")
    private String name;

    @NotBlank(message = "Email can't be blank")
    @Email(message = "Email address is not valid")
    private String email;

    @NotBlank(message = "Password can't be blank")
    private String password;

    @Column(nullable = false, length = 15)
    private String phoneNumber;

    private String role = ROLE_USER;

    @OneToMany(mappedBy = "patron", cascade = CascadeType.ALL, orphanRemoval = true, fetch = LAZY)
    private Set<BorrowingRecord> borrowingRecords;

    @Column(updatable = false)
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;
}
