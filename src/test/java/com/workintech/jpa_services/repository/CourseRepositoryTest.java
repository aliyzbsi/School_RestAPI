package com.workintech.jpa_services.repository;

import com.workintech.jpa_services.service.CourseService;
import com.workintech.jpa_services.service.CourseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.Extension;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("Course test")
@ExtendWith(MockitoExtension.class)
class CourseRepositoryTest {
    @Mock
    private CourseRepository courseRepository;
    @Mock
    private InstructorRepository instructorRepository;
    private CourseService courseService;

    @BeforeEach
    void setUp() {
        courseService=new CourseServiceImpl(courseRepository,instructorRepository);
    }


}