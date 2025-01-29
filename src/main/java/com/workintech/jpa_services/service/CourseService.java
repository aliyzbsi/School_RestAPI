package com.workintech.jpa_services.service;

import com.workintech.jpa_services.dto.CourseRecord;

import com.workintech.jpa_services.entity.Course;

import java.util.List;

public interface CourseService {
    List<Course> findAll();
    CourseRecord save(Course course);
    Course findById(Long id);
    Course assignInstructor(long courseId, long instructorId);
    Course delete(long id);
}
