package com.study.springboottutorial2.config;

import com.github.javafaker.Faker;
import com.study.springboottutorial2.entities.*;
import com.study.springboottutorial2.repository.StudentIdCardRepository;
import com.study.springboottutorial2.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * In this config the Faker functionality is used to generate a larger number of students.
 */
@Configuration
@Slf4j
public class StudentConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository,
                                        StudentIdCardRepository studentIdCardRepository) throws ParseException {
        log.info("Creating the two students...");
        return args -> {
            Faker faker = new Faker();
            String firstName = faker.name().firstName();
            String lastName = faker.name().lastName();
            String email = String.format("%s.%s@hotmail.com", firstName, lastName);
            LocalDate dateOfBirth = LocalDate.of(LocalDate.now().getYear() - faker.number().numberBetween(18, 65), 1, 1);
            Student student = new Student(
                    firstName,
                    lastName,
                    email,
                    dateOfBirth
            );

            student.addBook(
                    new Book("Clean Code", LocalDateTime.now().minusDays(4))
            );
            student.addBook(
                    new Book("Think and Grow Rich", LocalDateTime.now())
            );
            student.addBook(
                    new Book("Spring Data JPA", LocalDateTime.now().minusYears(1))
            );

            StudentIdCard studentIdCard = new StudentIdCard("1234567890", student);

            student.setStudentIdCard(studentIdCard);

            /**
             * The determination for EnrolmentId.studentId / .courseId should be improved of course.
             * See:
             * https://stackoverflow.com/questions/5031614/the-jpa-hashcode-equals-dilemma?rq=1
             */
            student.addEnrolment(
                    new Enrolment(
                            new EnrolmentId(1L, 1L),
                            student,
                            new Course("Computer Science", "IT"),
                            LocalDateTime.now()
                    )
            );
            student.addEnrolment(
                    new Enrolment(
                            new EnrolmentId(1L, 2L),
                            student,
                            new Course("Amigoscode Spring Data JPA", "IT"),
                            LocalDateTime.now().minusDays(18)
                    )
            );

            studentRepository.save(student);

            studentRepository.findById(1L)
                    .ifPresent(student1 -> {
                        log.info("Fetch book lazy...");
                        log.info("student1: {}", student.toString());
                        List<Book> books = student.getBooks();
                        books.forEach(book ->
                                log.info(book.getStudent().getFirstName() + " borrowed " + book.getName())
                        );
                    });

            studentIdCardRepository.findById(1L)
                    .ifPresent(studentIdCard1 -> log.info("studentIdCard1 = {}", studentIdCard1));

            /**
             * Because in Student for variable studentIdCard the setting orphanRemoval = true
             * the following will be visible:
             *
             * Hibernate:
             *     delete
             *     from
             *         student_id_card
             *     where
             *         id=?
             * Hibernate:
             *     delete
             *     from
             *         student
             *     where
             *         id=?
             *
             * Meaning:
             * studentRepository.deleteById(1L) will not only remove the student with id = 1L but
             * also the studentIdCard that has a OneOnOne connection with that student.
             */
//            studentRepository.deleteById(1L);
        };
    }
}
