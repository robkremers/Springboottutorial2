package com.study.springboottutorial2.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Objects;

@Getter
@Setter
@Entity(name = "StudentIdCard")
@Table(name = "student_id_card", indexes = {
        @Index(name = "idx_student_id_card_student_id", columnList = "student_id")
}, uniqueConstraints = {
        @UniqueConstraint(name = "student_id_card_id_card_number_unique",
                columnNames = {"card_number"})
}
)
public class StudentIdCard {
    @Id
    @SequenceGenerator(
            name = "student_card_id_sequence",
            sequenceName = "student_card_id_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_card_id_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "card_number",
            nullable = false,
            length = 15
    )
    private String cardNumber;

    /**
     * If FetchType.LAZY is choosen the equals(), hashCode() and toString() methods should be adapted.
     * Reason:
     * The Student will now not automatically be fetched. Only if an explicit query is calling the Student instance.
     * Note:
     * For a One to One relationship the default is FetchType.EAGER.
     */
    @OneToOne(
            cascade = CascadeType.ALL
//            , fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "student_id",
            referencedColumnName = "id"
            , foreignKey = @ForeignKey(
                    name = "student_id_fk"
            )
    )
    private Student student;

    public StudentIdCard(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public StudentIdCard(String cardNumber, Student student) {
        this.cardNumber = cardNumber;
        this.student = student;
    }

    public StudentIdCard() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentIdCard that = (StudentIdCard) o;
        return id.equals(that.id) && cardNumber.equals(that.cardNumber)
                && student.equals(that.student);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardNumber
                , student
        );
    }

    @Override
    public String toString() {
        return "StudentIdCard{" +
                "id=" + id +
                ", cardNumber='" + cardNumber + '\'' +
                ", student=" + student.toString() +
                '}';
    }
}
