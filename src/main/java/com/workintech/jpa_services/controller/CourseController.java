package com.workintech.jpa_services.controller;

import com.workintech.jpa_services.dto.CourseResponse;
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
    public Course addInstructorForCourse(@PathVariable("id") long id,@RequestBody Instructor instructor){
       Course course=courseService.findById(id);

       if(id<0||id>20){

       }
       course.setInstructor(instructor);
       instructor.addCourse(course);
       return courseService.save(course);
    }


    @GetMapping("/{id}")
    public Course findById(@PathVariable("id") long id){
        return courseService.findById(id);
    }


}
