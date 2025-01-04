package com.workintech.jpa_services.controller;

import com.workintech.jpa_services.dto.StudentResponseRecord;
import com.workintech.jpa_services.entity.Gender;
import com.workintech.jpa_services.entity.Student;
import com.workintech.jpa_services.service.StudentService;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Validated
@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService=studentService;
    }
    @GetMapping
    public List<Student> findAll(){
        return studentService.findAll();
    }

    @PostMapping
    public Student save(@Validated @RequestBody Student student){
        System.out.println(student);
        return studentService.save(student);
    }

    @GetMapping("/{id}")
    public StudentResponseRecord findById(@Positive @PathVariable("id") Long id){
        Student student=studentService.findById(id);
        return new StudentResponseRecord(student.getFirstName(),student.getLastName());
    }

    @GetMapping("/search/{name}")
    public List<StudentResponseRecord> findByName(@PathVariable("name") String firstName){
        List<Student> students=studentService.searchByFirstName(firstName);
        return students.stream()
                .map(student -> new StudentResponseRecord(student.getFirstName(),student.getLastName())).collect(Collectors.toList());
    }

    @GetMapping("/byGender/{gender}")
    public List<Student> findByGender(@PathVariable("gender") Gender gender){
        return studentService.searchByGender(gender);
    }

    @DeleteMapping("/{id}")
    public Student delete(@PathVariable("id") Long id){
        return studentService.delete(id);
    }
}
