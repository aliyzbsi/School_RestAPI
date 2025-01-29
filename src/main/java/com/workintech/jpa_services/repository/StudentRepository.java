package com.workintech.jpa_services.repository;

import com.workintech.jpa_services.entity.Gender;
import com.workintech.jpa_services.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student,Long> {

    @Query("SELECT s FROM Student s WHERE LOWER(s.firstName) LIKE LOWER (CONCAT ('%', :name, '%'))")
    List<Student> searchByFirstName(String name);
    @Query("SELECT s FROM Student s WHERE s.gender=:gender")
    List<Student> searchByGender(Gender gender);
    @Query("SELECT s FROM Student s WHERE s.email=:email")
    Student searchByEmail(String email);


}
