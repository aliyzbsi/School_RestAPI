package com.workintech.jpa_services.service;

import com.workintech.jpa_services.dto.StudentResponseRecord;
import com.workintech.jpa_services.entity.Gender;
import com.workintech.jpa_services.entity.Student;

import java.util.List;

public interface StudentService {

    StudentResponseRecord save(Student student);
    List<Student> findAll();
    Student findById(Long id);

    List<Student> searchByFirstName(String firstName);

    List<Student> searchByGender(Gender gender);

    Student delete(Long id);
}
