package com.workintech.jpa_services.controller;

import com.workintech.jpa_services.entity.Instructor;
import com.workintech.jpa_services.service.InstructorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/{id}")
    public Instructor findById(@PathVariable("id") Long id){
        return instructorService.findById(id);
    }

    @PostMapping("/")
    public Instructor save(@RequestBody Instructor instructor){
        return instructorService.save(instructor);
    }

    @DeleteMapping("/{id}")
    public Instructor delete(@PathVariable("id") Long id){
        return instructorService.remove(id);
    }

}
