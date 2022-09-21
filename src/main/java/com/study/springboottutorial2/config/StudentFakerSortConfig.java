package com.study.springboottutorial2.config;

import com.github.javafaker.Faker;
import com.study.springboottutorial2.entities.Student;
import com.study.springboottutorial2.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.text.ParseException;
import java.time.LocalDate;

/**
 * In this config the Faker functionality is used to generate a larger number of students.
 */
//@Configuration
@Slf4j
public class StudentFakerSortConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) throws ParseException {
        log.info("Creating the two students...");
        return args -> {
            generateRandomStudents(studentRepository);
            PageRequest pageRequest = PageRequest.of(0, 5, Sort.by("firstName").ascending()); // 5 students per page.
            Page<Student> studentPage = studentRepository.findAll(pageRequest);
            log.info("StudentPage \n{}", studentPage);
        };
    }

    /**
     * Example of the use of Sorting returned content.
     *
     * @param studentRepository
     */
    private void sortingStudents(StudentRepository studentRepository) {
        Sort sort = Sort.by(Sort.Direction.DESC, "firstName").and(Sort.by("dateOfBirth").descending());
        studentRepository.findAll(sort)
                .forEach(student -> log.info(student.getFirstName() + " " + student.getAge()));
    }

    private void generateRandomStudents(StudentRepository studentRepository) {
        Faker faker = new Faker();
        for (int i = 0; i < 20; i++) {
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
            studentRepository.save(student);
        }
    }
}
