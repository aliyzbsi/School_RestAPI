package com.workintech.jpa_services.repository;

import com.workintech.jpa_services.dto.CourseRequestDto;
import com.workintech.jpa_services.entity.Course;
import com.workintech.jpa_services.entity.Gender;
import com.workintech.jpa_services.entity.Student;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("Student test")

class StudentRepositoryTest {

    private final StudentRepository studentRepository;

    private final CourseRepository courseRepository;
    @Autowired
    public StudentRepositoryTest(StudentRepository studentRepository,CourseRepository courseRepository) {
        this.studentRepository = studentRepository;
        this.courseRepository=courseRepository;
    }

    @BeforeEach
    void setUp() {
        Student student=new Student();
        student.setFirstName("Ayşe");
        student.setLastName("Yüzbaşı");
        student.setEmail("ayseyzbsi@test.com");
        student.setGender(Gender.FEMALE);
        student.setSalary(3000);
        Optional<Course> course = courseRepository.findById(4L);
        student.addCourse(course.get());

    }

    @AfterEach
    void tearDown() {
        studentRepository.deleteAll();
    }

    @DisplayName("Can find student by first name ? ")
    @Test
    void searchByFirstName() {
        List<Student> foundStudent=studentRepository.searchByFirstName("Ayşe");
        assertNotNull(foundStudent);
        assertFalse(foundStudent.isEmpty());
        assertEquals("Ayşe",foundStudent.get(1).getFirstName());
    }


    @DisplayName("Can find student by gender ? ")
    @Test
    void searchByGender() {
    }
    @DisplayName("Can find student by email ?")
    @Test
    void searchByEmail(){

        Student foundStudent=studentRepository.searchByEmail("ali@test.com");
        assertNotNull(foundStudent);
        assertEquals("Ali",foundStudent.getFirstName());
        assertEquals("ali@test.com",foundStudent.getEmail());

    }

}