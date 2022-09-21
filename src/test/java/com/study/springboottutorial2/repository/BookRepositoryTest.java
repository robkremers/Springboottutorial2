package com.study.springboottutorial2.repository;

import com.study.springboottutorial2.entities.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=validate"
})
public class BookRepositoryTest {

    @Autowired
    BookRepository bookRepository;

    @Test
    void testRepository() {

        Book book = new Book("A Book", LocalDateTime.now().minusYears(1L));

        bookRepository.save(book);

        assertThat(book).hasFieldOrPropertyWithValue("name", "A Book");
    }
}
