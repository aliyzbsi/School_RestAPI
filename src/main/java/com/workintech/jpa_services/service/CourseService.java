package com.workintech.jpa_services.service;

import com.workintech.jpa_services.dto.CourseResponse;
import com.workintech.jpa_services.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> findAll();
    Course save(Course course);
    Course findById(Long id);
}
