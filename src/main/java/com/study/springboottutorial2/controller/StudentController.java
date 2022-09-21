package com.study.springboottutorial2.controller;

import com.study.springboottutorial2.dto.StudentDto;
import com.study.springboottutorial2.entities.Student;
import com.study.springboottutorial2.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/student")
public class StudentController {

    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentService.getStudents();
    }

    /**
     * Example:
     * http://localhost:9090/api/v1/student/?id=1
     * @param id
     * @return
     */
    @GetMapping(path = "/{id}")
    public Student getStudent(@RequestParam("id") long id) {
        return studentService.getStudent( id);
    }

    /**
     * Example:
     * GET http://localhost:9090/api/v1/student/page/?pageNo=0&pageSize=10&sortby=id
     * @param pageNo
     * @param pageSize
     * @param sortby
     * @return
     */
    @GetMapping(path = "/page")
    public List<Student> getStudentsPaginated(@RequestParam(defaultValue = "0") Integer pageNo,
                                              @RequestParam(defaultValue = "10") Integer pageSize,
                                              @RequestParam(defaultValue = "id") String sortby) {

        return studentService.getStudents(pageNo, pageSize, sortby);
    }

    @PostMapping
    public Student registerNewStudent(@RequestBody StudentDto studentDto) throws IllegalAccessException {
        return studentService.addNewStudent(studentDto);
    }

    @DeleteMapping(path = "{studentId}")
    public void deleteStudent(@PathVariable("studentId") Long studentId) throws IllegalAccessException {
        studentService.deleteStudent(studentId);
    }

    /**
     * In this case no json is expected but separate values.
     * Example:
     * PUT http://localhost:9090/api/v1/student/2?firstName=Alex
     * PUT http://localhost:9090/api/v1/student/1?name=Rob&email=rob@hotmail.com
     * @param studentId
     * @param firstName
     * @param lastName
     * @param email
     * @return
     */
    @PutMapping(path = "{studentId}")
    public Student updateStudent(@PathVariable("studentId") Long studentId,
                                 @RequestParam(required = false) String firstName,
                                 @RequestParam(required = false) String lastName,
                                 @RequestParam(required = false) String email) {
        return studentService.updateStudent(studentId, firstName, lastName, email);
    }

}
