package com.workintech.jpa_services.service;

import com.workintech.jpa_services.dto.CourseRecord;

import com.workintech.jpa_services.entity.Course;
import com.workintech.jpa_services.entity.Instructor;
import com.workintech.jpa_services.entity.Student;
import com.workintech.jpa_services.repository.CourseRepository;
import com.workintech.jpa_services.repository.InstructorRepository;
import com.workintech.jpa_services.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService  {

    private final CourseRepository courseRepository;
    private final InstructorRepository instructorRepository;

    @Autowired
    public CourseServiceImpl(CourseRepository courseRepository, InstructorRepository instructorRepository) {
        this.courseRepository = courseRepository;
        this.instructorRepository = instructorRepository;

    }
    @Override
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    @Override
    @Transactional
    public CourseRecord save(Course course) {
        Course savedCourse=courseRepository.save(course);
        return new CourseRecord(savedCourse.getTitle(),savedCourse.getGpa());
    }

    @Override
    @Transactional
    public Course assignInstructor(long courseId, long instructorId) {
        Course course=findById(courseId);
        Instructor instructor=instructorRepository.findById(instructorId).orElseThrow(()->
                new RuntimeException("Instructor not found with id: " + instructorId));
        course.setInstructor(instructor);
        instructor.addCourse(course);
        return courseRepository.save(course);
    }

    @Override
    public Course findById(Long id) {
        Optional<Course> courseOptional=courseRepository.findById(id);
        if(courseOptional.isPresent()){
            return courseOptional.get();
        }else{
            throw new IllegalArgumentException(id+":id'li kurs bulunamadı!");
        }
    }

    @Override
    public Course delete(long id) {
       Course course=findById(id);
       courseRepository.delete(course);

        return course;
    }
}
