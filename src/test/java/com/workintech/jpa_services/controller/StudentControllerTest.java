package com.workintech.jpa_services.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.workintech.jpa_services.config.SecurityConfig;
import com.workintech.jpa_services.dto.CourseRequestDto;
import com.workintech.jpa_services.dto.StudentCourseRequest;
import com.workintech.jpa_services.dto.StudentResponseRecord;
import com.workintech.jpa_services.entity.Course;
import com.workintech.jpa_services.entity.Gender;
import com.workintech.jpa_services.entity.Student;
import com.workintech.jpa_services.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

// Controller için yazılan testler Integration Test => Entegrasyon testi olarak geçer.
//Birden fazla parçanın birlikte çalıştığında yaptığı işlemleri test etmeye Integration test denir.

@WebMvcTest(StudentController.class)
@Import(SecurityConfig.class)
public class StudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private StudentService studentService;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findAll() {
    }

    @Test
    void save() throws Exception {
        // 1. Test verilerini hazırlama (Arrange)
        Student student = new Student();    // Test için örnek öğrenci oluşturma
        student.setId(1L);
        student.setFirstName("Ayşe");
        student.setLastName("Yüzbaşı");
        student.setEmail("ayseyzbsi@test.com");
        student.setGender(Gender.FEMALE);
        student.setSalary(3000);

        Course course = new Course();       // Test için örnek kurs oluşturma
        course.setId(45L);
        course.setTitle("Test Course");
        StudentCourseRequest studentCourseRequest=new StudentCourseRequest();
        List<CourseRequestDto> courseRequestDto = new ArrayList<>();
        courseRequestDto.add(new CourseRequestDto(course.getId()));

        studentCourseRequest.setStudent(student);
        studentCourseRequest.setCourseList(courseRequestDto);
        StudentResponseRecord result = new StudentResponseRecord(student.getId(),student.getFirstName(),
                student.getLastName(),student.getEmail(),student.getCourses());

        //Stubbing
        when(studentService.save(any(StudentCourseRequest.class))).thenReturn(result);
        mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(studentCourseRequest))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print()) // Response'u yazdır
                .andExpect(status().isCreated())
                .andExpect(header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE))
                .andExpect(jsonPath("$.id").value(student.getId()))
                .andExpect(jsonPath("$.firstName").value(student.getFirstName()))
                .andExpect(jsonPath("$.lastName").value(student.getLastName()))
                .andExpect(jsonPath("$.email").value(student.getEmail()));

        verify(studentService).save(any(StudentCourseRequest.class));

    }

    public static String jsonToString(Object object){
        try {
            return new ObjectMapper().writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void addCourseForStudent() {
    }

    @Test
    void removeCourseForStudent() {
    }

    @Test
    void findById() {
    }

    @Test
    void findByName() {
    }

    @Test
    void findByGender() {
    }

    @Test
    void delete() {
    }
}

