package com.workintech.jpa_services.service;

import com.workintech.jpa_services.dto.CourseRecord;
import com.workintech.jpa_services.entity.Course;
import com.workintech.jpa_services.entity.Instructor;
import com.workintech.jpa_services.entity.InstructorDetail;
import com.workintech.jpa_services.repository.CourseRepository;
import com.workintech.jpa_services.repository.InstructorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(MockitoExtension.class)
class CourseServiceImplTest {

    @Mock
    private CourseRepository courseRepository;

    @Mock
    private InstructorRepository instructorRepository;

    private CourseService courseService;



    @BeforeEach
    void setUp() {
        courseService = new CourseServiceImpl(courseRepository, instructorRepository);


    }

    @Test
    void findAll() {
        // Act
        courseService.findAll();

        // Assert
        verify(courseRepository).findAll();
    }

    @Test
    void save() {
        // Arrange
        Course course = new Course();
        course.setId(1L);
        course.setTitle("Test Dersi");
        course.setGpa(3.0);

        when(courseRepository.save(any(Course.class))).thenReturn(course);

        // Act
        CourseRecord savedCourse = courseService.save(course);

        // Assert
        assertEquals("Test Dersi", savedCourse.title());
        verify(courseRepository).save(course);

    }

    @Test
    void assignInstructor() {
        // Arrange
        Course course = new Course();
        course.setId(1L);
        course.setTitle("Test Dersi");
        course.setGpa(3.0);

        Instructor instructor = new Instructor();
        instructor.setId(1L);
        instructor.setFirstName("Test eğitmen");
        instructor.setLastName("Test soyad");
        instructor.setEmail("testeğitmen@test.com");
        instructor.setInstructorDetail(
                new InstructorDetail(1L, "Test hobi", "testchannel.url", instructor)
        );

        // Mock davranışlarını tanımla
        when(courseRepository.findById(1L)).thenReturn(Optional.of(course)); // Bu satırı ekledik
        when(instructorRepository.findById(1L)).thenReturn(Optional.of(instructor));
        when(courseRepository.save(any(Course.class))).thenReturn(course);

        // Act
        Course updatedCourse = courseService.assignInstructor(course.getId(), instructor.getId());

        // Assert
        assertNotNull(updatedCourse);
        verify(courseRepository).findById(1L);
        verify(instructorRepository).findById(1L);
        verify(courseRepository).save(course);
        assertEquals(instructor.getId(), updatedCourse.getInstructor().getId());
        assertEquals(instructor.getFirstName(), updatedCourse.getInstructor().getFirstName());

    }

    @Test
    void findById() {
        // Arrange
        Long courseId = 1L;
        Course course = new Course();
        course.setId(courseId);

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        // Act
        courseService.findById(courseId);

        // Assert
        verify(courseRepository).findById(courseId);
    }

    @Test
    void delete() {
        // Arrange
        Long courseId = 1L;
        Course course = new Course();
        course.setId(courseId);
        course.setTitle("Test Course");

        when(courseRepository.findById(courseId)).thenReturn(Optional.of(course));

        // Act
        Course deletedCourse = courseService.delete(courseId);

        // Assert
        assertNotNull(deletedCourse);
        assertEquals(courseId, deletedCourse.getId());
        verify(courseRepository).findById(courseId);
        verify(courseRepository).delete(course);
    }
}