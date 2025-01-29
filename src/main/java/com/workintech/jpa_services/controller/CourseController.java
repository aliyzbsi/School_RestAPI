package com.workintech.jpa_services.controller;

import com.workintech.jpa_services.dto.CourseRecord;
import com.workintech.jpa_services.dto.CourseRequest;

import com.workintech.jpa_services.dto.InstructorRecord;
import com.workintech.jpa_services.entity.Course;
import com.workintech.jpa_services.entity.Instructor;
import com.workintech.jpa_services.service.CourseService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    private final CourseService courseService;
    @Autowired
    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getAll(){
        return courseService.findAll();
    }
    @Operation(summary = "Kursa eğitmen ekle", description = "Kursa Eğitmen ekleme")
    @PostMapping("{id}")
    public Course addInstructorForCourse(@PathVariable("id") long id,@RequestBody InstructorRecord instructorRecord){

       return courseService.assignInstructor(id,instructorRecord.id());
    }
    @PostMapping("/addCourse")
    public CourseRecord courseAdd(@RequestBody CourseRequest courseRequest){
        Course course=courseRequest.getCourse();
        return courseService.save(course);
    }


    @GetMapping("/{id}")
    public Course findById(@PathVariable("id") long id){
        return courseService.findById(id);
    }

    @DeleteMapping("/{id}")
    public Course removeCourse(@PathVariable("id") long id){
        return courseService.delete(id);
    }
}
