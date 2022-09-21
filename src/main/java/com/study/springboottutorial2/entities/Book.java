package com.study.springboottutorial2.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@Entity(name = "Book")
@Table(name = "book", indexes = {
        @Index(name = "idx_book_student_id", columnList = "student_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "book_name_unique",
                columnNames = {"book_name"})
})
public class Book {

    @Id
    @SequenceGenerator(
            name = "book_id_sequence",
            sequenceName = "book_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "book_id_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "book_name",
            nullable = false
//            , columnDefinition = "TEXT"
    )
    private String name;

    @Column(
            name = "created_at",
            nullable = false,
            columnDefinition = "TIMESTAMP WITHOUT TIME ZONE"
    )
    private LocalDateTime createdAt;

    @ManyToOne(
            cascade = CascadeType.ALL
            , fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "student_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(
                    name = "student_id_fk"
            )
    )
    private Student student;

    public Book() {
    }

    public Book(String name,
                LocalDateTime createdAt,
                Student student) {
        this.createdAt = createdAt;
        this.name = name;
        this.student = student;
    }

    public Book(String name, LocalDateTime createdAt) {
        this.createdAt = createdAt;
        this.name = name;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Book book = (Book) o;
//        return name.equals(book.name) && createdAt.equals(book.createdAt) && student.equals(book.student);
//    }
//
//    @Override
//    public int hashCode() {
//        return Objects.hash(name, createdAt, student);
//    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", createdAt=" + createdAt +
                ", student=" + student +
                '}';
    }
}
