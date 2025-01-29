package com.workintech.jpa_services.service;

import com.workintech.jpa_services.dto.StudentCourseRequest;
import com.workintech.jpa_services.dto.StudentResponseRecord;
import com.workintech.jpa_services.entity.Gender;
import com.workintech.jpa_services.entity.Student;

import java.util.List;

public interface StudentService {

    StudentResponseRecord save(StudentCourseRequest studentCourseRequest);
    List<Student> findAll();
    Student findById(Long id);
    Student assignCourseForStudent(Long studentId,long courseId);

    List<Student> searchByFirstName(String firstName);
    Student searchByEmail(String email);
    List<Student> searchByGender(Gender gender);

    Student delete(Long id);
    Student removeCourseForStudent(Long studentId,Long courseId);
}
