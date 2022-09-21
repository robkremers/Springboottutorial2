package com.study.springboottutorial2.repository;

import com.study.springboottutorial2.entities.Student;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Based on the following example:
 * - https://www.bezkoder.com/spring-boot-unit-test-jpa-repo-datajpatest/
 *
 */
@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=validate"
})
@AutoConfigureTestDatabase
public class StudentRepositoryTest {

    @Autowired
    StudentRepository studentRepository;

    @Test
    void testRepository() {
        Student student = new Student("Rob", "Kremers", "robkremers@edu.com", LocalDate.now().minusYears(59L));

        studentRepository.save(student);

        assertThat(student).hasFieldOrPropertyWithValue("firstName", "Rob");
    }
}
