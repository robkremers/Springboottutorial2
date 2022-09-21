package com.study.springboottutorial2.controller;

import com.study.springboottutorial2.service.CustomerRegistrationRequest;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(path = "api/v1/customer-registration")
public class CustomerRegistrationController {

    @PutMapping
    public void registrationNewCustomer(
            // TODO: check the connection between @Valid and @NotBlank in class Customer.
            @Valid @RequestBody CustomerRegistrationRequest request) {

    }
}
