package com.study.springboottutorial2.repository;

import com.study.springboottutorial2.entities.Customer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Documentation:
 * - https://reflectoring.io/spring-boot-data-jpa-test/
 *   @ExtendWith
 *   The code examples in this tutorial use the @ExtendWith annotation to tell JUnit 5 to enable Spring support.
 *   As of Spring Boot 2.1, we no longer need to load the SpringExtension because it's included as a meta annotation
 *   in the Spring Boot test annotations like @DataJpaTest, @WebMvcTest, and @SpringBootTest.
 * - https://www.bezkoder.com/spring-boot-unit-test-jpa-repo-datajpatest/
 *  - This uses a H2-database for test purposes.
 */

@DataJpaTest(
        properties = {
                "spring.jpa.properties.javax.persistence.validation.mode=none"
        }
)
//@TestPropertySource(properties = {
//        "spring.jpa.hibernate.ddl-auto=validate"
//})
//@DataJpaTest
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository testCustomerRepository;

    @Test
    void testName() {
        // Given
        // When
        // Then
    }

    @Test
    void itNotShouldSelectCustomerByPhoneNumberWhenNumberDoesNotExists() {
        // Given
        String phoneNumber = "0000";

        // When
        Optional<Customer> optionalCustomer = testCustomerRepository.selectCustomerByPhoneNumber(phoneNumber);

        // Then
        assertThat(optionalCustomer).isNotPresent();
    }

    @Test
    void itShouldSelectCustomerByPhoneNumber() {
        // Given
        UUID id = UUID.randomUUID();
        String phoneNumber = "0000";
        Customer customer = new Customer(id, "Abel", phoneNumber);

        // When
        testCustomerRepository.save(customer);

        // Then
        Optional<Customer> optionalCustomer = testCustomerRepository.selectCustomerByPhoneNumber(phoneNumber);
        assertThat(optionalCustomer)
                .isPresent()
                .hasValueSatisfying(c -> {
                    assertThat(c).hasFieldOrPropertyWithValue("name", "Abel");
//                    .isEqualToComparingFieldByField(customer);
                });
    }
}