package com.study.springboottutorial2.entities;

import com.study.springboottutorial2.dto.StudentDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity(name = "Student")
@Table(name = "student",
        uniqueConstraints = {
                @UniqueConstraint(name = "student_email_unique",
                        columnNames = {"email"})
        }
)
@Getter
@Setter
public class Student {
    @Id
    @SequenceGenerator(
            name = "student_sequence",
            sequenceName = "student_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "student_sequence"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "first_name",
            nullable = false
//            , columnDefinition = "TEXT"
    )
    private String firstName;

    @Column(
            name = "last_name",
            nullable = false
//            , columnDefinition = "TEXT"
    )
    private String lastName;

    @Column(
            name = "email",
            nullable = false
//            , columnDefinition = "TEXT"
    )
    private String email;

    @Column(
            name = "date_of_birth",
            nullable = false
    )
    private LocalDate dateOfBirth;

    @Transient
    private Integer age;

    /**
     * This means that Student is owned by StudentIdCard.
     * <p>
     * The problem with bi-directional is that although it works in the java code
     * it causes cross-reference when using it via a REST call.
     * Therefore I have abandoned this approach.
     * See also the README.md.
     */
    @OneToOne(
            mappedBy = "student",
            orphanRemoval = true,
            cascade = CascadeType.ALL
    )
    public StudentIdCard studentIdCard;

    @OneToMany(
            mappedBy = "student",
            orphanRemoval = true,
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE}
    )
    private List<Book> books = new ArrayList<>();

    @OneToMany(
            cascade = {CascadeType.PERSIST, CascadeType.REMOVE},
            /**
             * Mapped by the corresponding instance "student" in class Enrolment.
            */
            mappedBy = "student"
    )
    private List<Enrolment> enrolments = new ArrayList<>();

    public Student() {}

    public Student(String firstName, String lastName, String email, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.dateOfBirth = dateOfBirth;
    }

    public Student(StudentDto studentDto) {
        this.firstName = studentDto.getFirstName();
        this.lastName = studentDto.getLastName();
        this.email = studentDto.getEmail();
        this.dateOfBirth = studentDto.getDateOfBirth();
    }

    public Integer getAge() {
        return Period.between(this.dateOfBirth, LocalDate.now()).getYears();
    }

    public void addBook(Book book) {
        if (!books.contains(book)) {
            books.add(book);
            book.setStudent(this);
        }
    }

    public void removeBook(Book book) {
        if (books.contains(book)) {
            books.remove(book);
            book.setStudent(null);
        }
    }

    public void addEnrolment(Enrolment enrolment) {
        if (!enrolments.contains(enrolment)) {
            enrolments.add(enrolment);
        }
    }

    public void removeEnrolment(Enrolment enrolment) {
        enrolments.remove(enrolment);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Student student = (Student) o;
        return id.equals(student.id) && firstName.equals(student.firstName) && lastName.equals(student.lastName) && email.equals(student.email) && dateOfBirth.equals(student.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, email, dateOfBirth);
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", age=" + age +
                '}';
    }
}
