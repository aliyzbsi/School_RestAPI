package com.workintech.jpa_services.service;

import com.workintech.jpa_services.dto.CourseResponse;
import com.workintech.jpa_services.entity.Course;
import com.workintech.jpa_services.repository.CourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService  {

    private CourseRepository courseRepository;
    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    public Course save(Course course) {
        return courseRepository.save(course);
    }

    @Override
    public Course findById(Long id) {
        Optional<Course> courseOptional=courseRepository.findById(id);
        if(courseOptional.isPresent()){
            return courseOptional.get();
        }else{
            throw new IllegalArgumentException(id+"' li kurs bulunamadı!");
        }
    }
}
