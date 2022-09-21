package com.study.springboottutorial2.service;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.study.springboottutorial2.entities.Customer;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomerRegistrationRequest {

    private final Customer customer;

    public CustomerRegistrationRequest(
            // TODO: check what this does exactly.
            @JsonProperty(value = "customer") Customer customer) {
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    @Override
    public String toString() {
        return "CustomerRegistrationRequest{" +
                "customer=" + customer +
                '}';
    }
}
