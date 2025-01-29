package com.workintech.jpa_services.service;

import com.workintech.jpa_services.dto.CourseRequestDto;
import com.workintech.jpa_services.dto.StudentCourseRequest;
import com.workintech.jpa_services.dto.StudentResponseRecord;
import com.workintech.jpa_services.entity.Course;
import com.workintech.jpa_services.entity.Gender;
import com.workintech.jpa_services.entity.Student;
import com.workintech.jpa_services.repository.CourseRepository;
import com.workintech.jpa_services.repository.StudentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@SpringBootTest                              // Spring Boot test ortamını başlatır
@TestInstance(TestInstance.Lifecycle.PER_CLASS)  // Test instance'ının yaşam döngüsünü belirler
@ExtendWith(MockitoExtension.class)         // Mockito framework'ünü test sınıfına entegre eder
class StudentServiceImplTest {

    @Mock                                   // StudentRepository'nin sahte (mock) versiyonunu oluşturur
    private StudentRepository studentRepository;

    @Mock                                   // CourseRepository'nin sahte (mock) versiyonunu oluşturur
    private CourseRepository courseRepository;

    private StudentService studentService;  // Test edilecek servis
    private AuthenticationService authenticationService;

    @BeforeEach                            // Her test metodundan önce çalışacak metod
    void setUp() {
        // StudentService'i mock repository'ler ile başlatır
        studentService = new StudentServiceImpl(studentRepository, courseRepository,authenticationService);
    }

    @Test
    @DisplayName("Öğrenci ve kurs kaydı başarılı olmalı")  // Test için açıklayıcı isim
    void save() {
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

        // Mock davranışlarını ayarlama
        // Email kontrolünde null dönmesini sağlar (yeni öğrenci)
        when(studentRepository.searchByEmail(student.getEmail()))
                .thenReturn(null);

        // Öğrenci kaydedildiğinde aynı öğrenciyi döndürür
        when(studentRepository.save(any(Student.class)))
                .thenReturn(student);

        // Kurs ID ile arama yapıldığında örnek kursu döndürür
        when(courseRepository.findById(45L))
                .thenReturn(Optional.of(course));

        // Request nesnesini oluşturma
        List<CourseRequestDto> courseRequestDto = new ArrayList<>();
        courseRequestDto.add(new CourseRequestDto(course.getId()));

        StudentCourseRequest studentCourseRequest = new StudentCourseRequest();
        studentCourseRequest.setStudent(student);
        studentCourseRequest.setCourseList(courseRequestDto);

        // 2. Test metodunu çalıştırma (Act)
        StudentResponseRecord result = studentService.save(studentCourseRequest);

        // 3. Sonuçları doğrulama (Assert)
        assertNotNull(result);                              // Sonucun null olmadığını kontrol eder
        assertEquals(student.getEmail(), result.email());   // Email'in doğru kaydedildiğini kontrol eder

        // Repository metodlarının doğru çağrıldığını kontrol etme
        verify(studentRepository).searchByEmail(student.getEmail());    // Email kontrolü yapıldı mı?
        verify(studentRepository, times(2)).save(any(Student.class));   // Save metodu 2 kez çağrıldı mı?
        verify(courseRepository).findById(45L);                         // Kurs arandı mı?
    }

    @Test
    @DisplayName("Should not save when student already exists")
    void canNotSave() {
        // Arrange
        Student student = new Student();
        student.setId(1L);
        student.setFirstName("Ayşe");
        student.setLastName("Yüzbaşı");
        student.setEmail("ayseyzbsi@test.com");
        student.setGender(Gender.FEMALE);
        student.setSalary(3000);

        Course course = new Course();
        course.setId(45L);
        course.setTitle("Test Course");

        List<CourseRequestDto> courseRequestDto = new ArrayList<>();
        courseRequestDto.add(new CourseRequestDto(course.getId()));

        StudentCourseRequest studentCourseRequest = new StudentCourseRequest();
        studentCourseRequest.setStudent(student);
        studentCourseRequest.setCourseList(courseRequestDto);

        // Mock the searchByEmail to return an existing student (simulating student already exists)
        when(studentRepository.searchByEmail(student.getEmail())).thenReturn(student);

        // Act & Assert
        given(studentRepository.searchByEmail("ayseyzbsi@test.com")).willReturn(student);
        assertThatThrownBy(() -> studentService.save(studentCourseRequest))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Bu email adresi başka bir öğrenci tarafından kullanılıyor! ");

        // Verify that save was never called because student already exists
        verify(studentRepository).searchByEmail(student.getEmail());
        verify(studentRepository, never()).save(student);
        verifyNoInteractions(courseRepository);
    }

    @Test
    @DisplayName("Kurs bulunamadığında hata fırlatmalı")
    void save_shouldThrowException_whenCourseNotFound() {
        // 1. Test verilerini hazırlama
        Student student = new Student();
        student.setEmail("ayseyzbsi@test.com");

        // Mock davranışlarını ayarlama
        when(courseRepository.findById(45L)).thenReturn(Optional.empty());  // Kurs bulunamadı
        when(studentRepository.searchByEmail(student.getEmail())).thenReturn(null);

        StudentCourseRequest request = new StudentCourseRequest();
        request.setStudent(student);
        request.setCourseList(List.of(new CourseRequestDto(45L)));

        // 2 ve 3. Hata fırlatmayı test etme
        Exception exception = assertThrows(RuntimeException.class, () -> {
            studentService.save(request);
        });

        // Hata mesajını kontrol etme
        assertEquals("Kurs ekleme işlemi başarısız: Bu kurs içeriğine erişilemiyor: 45",
                exception.getMessage());

        // Repository çağrılarını doğrulama
        verify(studentRepository).searchByEmail(student.getEmail());
        verify(courseRepository).findById(45L);
    }

    @Test
    @DisplayName("Öğrenci zaten varsa hata fırlatmalı")
    void save_shouldThrowException_whenStudentExists() {
        // 1. Test verilerini hazırlama
        Student student = new Student();
        student.setEmail("ayseyzbsi@test.com");

        // Öğrencinin zaten var olduğunu simüle etme
        when(studentRepository.searchByEmail(student.getEmail())).thenReturn(student);

        StudentCourseRequest request = new StudentCourseRequest();
        request.setStudent(student);
        request.setCourseList(List.of(new CourseRequestDto(45L)));

        // 2 ve 3. Hata fırlatmayı test etme
        assertThrows(RuntimeException.class, () -> {
            studentService.save(request);
        });

        // Repository çağrılarını doğrulama
        verify(studentRepository).searchByEmail(student.getEmail());
        verifyNoMoreInteractions(studentRepository);    // Başka repository çağrısı yapılmadığını kontrol
        verifyNoInteractions(courseRepository);         // Course repository'ye hiç erişilmediğini kontrol
    }
    @DisplayName("Bütün öğrencileri buluyor mu ?")
    @Test
    void findAll(){
        studentService.findAll();

    }

    @Test
    void findById() {
    }

    @Test
    void searchByFirstName() {
    }

    @Test
    void searchByGender() {
    }

    @Test
    void searchByEmail() {
    }

    @Test
    void delete() {
    }
}