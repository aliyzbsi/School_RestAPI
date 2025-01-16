package com.workintech.jpa_services.repository;

import com.workintech.jpa_services.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseRepository extends JpaRepository<Course,Long> {
}
