package com.study.springboottutorial2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private LocalDate dateOfBirth;
    private Integer age;
}
