package com.workintech.jpa_services.controller;

import com.workintech.jpa_services.dto.StudentCourseRequest;
import com.workintech.jpa_services.dto.StudentResponseRecord;
import com.workintech.jpa_services.entity.Course;
import com.workintech.jpa_services.entity.Gender;
import com.workintech.jpa_services.entity.Instructor;
import com.workintech.jpa_services.entity.Student;
import com.workintech.jpa_services.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Tag(name = "Student management", description = "Student management APIs")
@Validated
@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    @Autowired
    public StudentController(StudentService studentService){
        this.studentService=studentService;
    }
    @Operation(summary = "Get all students", description = "Returns a list of all students")
    @GetMapping
    public List<Student> findAll(){
        return studentService.findAll();
    }

    @PostMapping
    public StudentResponseRecord save(@Validated @RequestBody StudentCourseRequest studentCourseRequest){
            Student student=studentCourseRequest.getStudent();
            List<Course> courses=studentCourseRequest.getCourseList();

          //if(courses.get(hangi kurstan hangi öğrenci ise onun indexi).getStudents().contains(student))
            for(Course course:courses){
                student.addCourse(course);

            }

            return studentService.save(student);
    }

    @GetMapping("/{id}")
    public StudentResponseRecord findById(@Positive @PathVariable("id") Long id){
        Student student=studentService.findById(id);
        return new StudentResponseRecord(student.getId(),student.getFirstName(),student.getLastName(),student.getEmail());
    }

    @GetMapping("/search/{name}")
    public List<StudentResponseRecord> findByName(@PathVariable("name") String firstName){
        List<Student> students=studentService.searchByFirstName(firstName);
        return students.stream()
                .map(student -> new StudentResponseRecord(student.getId(),
                        student.getFirstName(),
                        student.getLastName(),
                        student.getEmail())
                ).collect(Collectors.toList());
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
