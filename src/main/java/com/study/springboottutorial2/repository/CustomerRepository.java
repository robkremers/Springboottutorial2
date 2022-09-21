package com.study.springboottutorial2.repository;

import com.study.springboottutorial2.entities.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    @Query(
            value = "select c.id, c.name, c.phone_number from customer c where c.phone_number = :phone_number",
            nativeQuery = true
    )
    Optional<Customer> selectCustomerByPhoneNumber(@Param("phone_number") String phoneNumber);
}
