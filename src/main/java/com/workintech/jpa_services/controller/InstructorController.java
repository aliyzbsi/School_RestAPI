package com.workintech.jpa_services.controller;

import com.workintech.jpa_services.dto.CourseResponse;
import com.workintech.jpa_services.entity.Course;
import com.workintech.jpa_services.entity.Instructor;
import com.workintech.jpa_services.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/instructor")
public class InstructorController {
    private final InstructorService instructorService;
    @Autowired
    public InstructorController(InstructorService instructorService) {
        this.instructorService = instructorService;
    }

    @GetMapping
    public List<Instructor> findAll(){
        return instructorService.findAll();
    }

    @GetMapping("courses/{id}")
    public List<CourseResponse> findAllCourse(@PathVariable("id") Long id){
        Instructor instructor= instructorService.findById(id);
        List<CourseResponse> courseResponses=new ArrayList<>();
        instructor.getCourses().forEach(course -> {
            courseResponses.add(new CourseResponse(course.getTitle(),course.getGpa()));
        });
        return courseResponses;
    }


    @GetMapping("/{id}")
    public Instructor findById(@PathVariable("id") Long id){
        return instructorService.findById(id);
    }

    @PostMapping("/")
    public Instructor save(@RequestBody Instructor instructor){
        return instructorService.save(instructor);
    }
    @PostMapping("/addCourse/{instructorId}")
    public CourseResponse addCourse(@RequestBody Course course,@PathVariable long instructorId){
        Instructor instructor=instructorService.findById(instructorId);
        course.setInstructor(instructor);
        instructor.addCourse(course);
        instructorService.save(instructor);
        return new CourseResponse(course.getTitle(),course.getGpa());
    }

    @DeleteMapping("/{id}")
    public Instructor delete(@PathVariable("id") Long id){
        return instructorService.remove(id);
    }

}
