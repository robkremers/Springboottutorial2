package com.study.springboottutorial2.config;

import com.study.springboottutorial2.entities.Student;
import com.study.springboottutorial2.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

import static java.time.Month.JANUARY;
import static java.time.Month.MARCH;

//@Configuration
@Slf4j
public class StudentExamplesConfig {

    @Bean
    CommandLineRunner commandLineRunner(StudentRepository studentRepository) throws ParseException {
        log.info("Creating the two students...");
        return args -> {
            Student alex = new Student(
                    "Alex",
                    "Barns",
                    "alex@hotmail.com",
                    LocalDate.of(2004, MARCH, 7));
            Student alex2 = new Student(
                    "Alex2",
                    "Barns2",
                    "alex2@hotmail.com",
                    LocalDate.of(2004, MARCH, 7));
            Student mariam = new Student(
                    "Mariam",
                    "Jamal",
                    "mariam.jamal@gmail.com",
                    LocalDate.of(2000, JANUARY, 5));
            List<Student> students = studentRepository.saveAll(
                    List.of(alex, alex2, mariam)
            );
            students.forEach((Student student) -> log.info(student.toString()));

            log.info("Trying to find a student with first name {} and last name {}", mariam.getFirstName(), mariam.getLastName());
            studentRepository.findStudentByFirstNameAndLastName(mariam.getFirstName(), mariam.getLastName())
                    .ifPresent(students1 -> students1.forEach(student ->
                            log.info(student.toString())
                    ));

            log.info("Trying to find a student with first name {} and last name {}", alex.getFirstName(), alex.getLastName());
            studentRepository.findStudentByFirstNameAndLastName(alex.getFirstName(), alex.getLastName())
                    .ifPresent(students1 -> students1.forEach(student ->
                            log.info(student.toString())
                    ));

            log.info("Trying to find a student with a first name that contains {}", "Alex");
            studentRepository.findStudentByFirstNameContaining("Alex")
                    .ifPresent(students1 -> students1.forEach(student ->
                            log.info(student.toString())
                    ));

            log.info("Trying to find a student with first name {} and date of birth {}", mariam.getFirstName(), LocalDate.of(2000, JANUARY, 5));
            studentRepository.findStudentByFirstNameEqualsAndDateOfBirthEquals(mariam.getFirstName(), LocalDate.of(2000, JANUARY, 5))
                    .ifPresent(students1 -> students1.forEach(student ->
                        log.info(student.toString())
                    ));

            log.info("Trying again to find a student with first name {}", "Alex");
            studentRepository.findStudentByFirstNameEquals("Alex")
                    .ifPresent(students1 -> students1.forEach(student ->
                        log.info(student.toString())
                    ));

            LocalDate dateOfBirthYear = LocalDate.of(2004, 01, 01);
            log.info("Trying to find a student with date of birth after {}", dateOfBirthYear);
            studentRepository.findStudentByDateOfBirthAfter(dateOfBirthYear)
                    .ifPresent(students1 -> students1.forEach(student ->
                        log.info(student.toString())
                    ));

//            log.info("Deleting student with id 1");
//            int nrOfRowsDeleted = studentRepository.deleteStudentById(1L);
//            log.info("Number of rows deleted: {}", nrOfRowsDeleted);
        };
    }
}
