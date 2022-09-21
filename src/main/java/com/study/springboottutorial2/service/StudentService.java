package com.study.springboottutorial2.service;

import com.study.springboottutorial2.dto.StudentDto;
import com.study.springboottutorial2.entities.Student;
import com.study.springboottutorial2.repository.StudentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class StudentService {

    private final StudentRepository studentRepository;

    @Autowired
    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public List<Student> getStudents() {
        final Sort sortByLastName = Sort.by(Sort.Direction.ASC, "lastName").and(Sort.by("dateOfBirth").descending());
        return studentRepository.findAll(sortByLastName);
    }

    public List<Student> getStudents(Integer pageNo, Integer pageSize, String sortby) {
        PageRequest pageRequest = PageRequest.of(pageNo, pageSize, Sort.by(sortby));
        Page<Student> studentPage = studentRepository.findAll(pageRequest);
        if (studentPage.hasContent()) {
            return studentPage.getContent();
        }
        return new ArrayList<>();
    }

    public Student getStudent(long id) {
        return studentRepository.findById( id).get();
    }

    public Student addNewStudent(StudentDto studentDto) throws IllegalAccessException {
        log.info("POST: {}", studentDto);
        Optional<Student> studentOptional = studentRepository.findStudentByEmail(studentDto.getEmail());
        if (studentOptional.isPresent()) {
            throw new IllegalAccessException("A Student with email " + studentDto.getEmail() + " already exists");
        }
        Student newStudent = studentRepository.save(new Student(studentDto));
        log.info("New student: {}", newStudent);
        return newStudent;
    }

    public void deleteStudent(Long id) throws IllegalAccessException {
        if (!studentRepository.existsById(id)) {
            throw new IllegalAccessException(getErrorMessage(id));
        }
        studentRepository.deleteById(id);
    }

    public Student updateStudent(Student student) throws IllegalAccessException {
        if (!studentRepository.existsById(student.getId())) {
            throw new IllegalAccessException(getErrorMessage(student.getId()));
        }
        return studentRepository.saveAndFlush(student);
    }

    @Transactional
    public Student updateStudent(Long studentId, String firstName, String lastName, String email) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new IllegalStateException(getErrorMessage(studentId)));

        if (firstName != null && firstName.length() > 0 && !Objects.equals(student.getFirstName(), firstName)) {
            student.setFirstName(firstName);
        }
        if (lastName != null && lastName.length() > 0 && !Objects.equals(student.getLastName(), lastName)) {
            student.setLastName(lastName);
        }
        if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
            student.setEmail(email);
        }
        return studentRepository.getById(studentId);
    }

    private String getErrorMessage(long id) {
        return "A student with id " + id + " does not exist";
    }
}
