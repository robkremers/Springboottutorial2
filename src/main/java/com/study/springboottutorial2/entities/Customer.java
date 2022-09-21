package com.study.springboottutorial2.entities;

import lombok.*;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;

/**
 * java.util.UUID:
 * - A class that represents an immutable universally unique identifier (UUID). A UUID represents a 128-bit value.
 * https://www.baeldung.com/java-bean-validation-not-null-empty-blank
 *
 *
 */
@Getter
@Setter
@ToString
//@NoArgsConstructor
//@AllArgsConstructor
@Entity(name = "customer")
public class Customer {

    @Id
    private UUID id;

    @NotBlank
    @Column(
            name = "name",
            nullable = false
    )
    private String name;

    @NotBlank
    @Column(
            name = "phone_number",
            nullable = false
    )
    private String phoneNumber;

    public Customer(UUID id, String name, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public Customer() {
    }
}
