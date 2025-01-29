package com.workintech.jpa_services.service;

import com.workintech.jpa_services.dto.CourseRequestDto;
import com.workintech.jpa_services.dto.StudentCourseRequest;
import com.workintech.jpa_services.dto.StudentResponseRecord;
import com.workintech.jpa_services.entity.Course;
import com.workintech.jpa_services.entity.Gender;
import com.workintech.jpa_services.entity.Student;
import com.workintech.jpa_services.repository.CourseRepository;
import com.workintech.jpa_services.repository.StudentRepository;
import com.workintech.jpa_services.user.ApplicationUser;
import com.workintech.jpa_services.user.Role;
import com.workintech.jpa_services.validations.StudentValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;
    private final CourseRepository courseRepository;
    private final AuthenticationService authenticationService;
    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository,CourseRepository courseRepository,AuthenticationService authenticationService){
        this.studentRepository=studentRepository;
        this.courseRepository=courseRepository;
        this.authenticationService=authenticationService;
    }

    @Override
    @Transactional
    public StudentResponseRecord save(StudentCourseRequest studentCourseRequest) {

        Student student = studentCourseRequest.getStudent();
        List<CourseRequestDto> requestedCourses = studentCourseRequest.getCourseList();

        // Önce öğrenciyi kaydet
        StudentValidation.studentExist(studentRepository.searchByEmail(studentCourseRequest.getStudent().getEmail()));
        Student savedStudent = studentRepository.save(student);

        //User hesabı oluştur
        String defaultPassword=generateSecurePassword();
        ApplicationUser user=authenticationService.register(
                student.getFirstName() + " " + student.getLastName(),
                student.getEmail(),
                defaultPassword,
                new Role(0, "STUDENT") // STUDENT rolü kullan
        );


        for (CourseRequestDto requestedCourse : requestedCourses) {
            try{
// Var olan kursu bul
                Course existingCourse = courseRepository.findById(requestedCourse.id())
                        .orElseThrow(() -> new RuntimeException("Bu kurs içeriğine erişilemiyor: " + requestedCourse.id()));

                // İlişkiyi kur (var olan kurs ile)
                savedStudent.addCourse(existingCourse);

                // Kursu güncelle
                courseRepository.save(existingCourse);
            }catch (RuntimeException e){
                // Roll-back yapılacak (@Transactional sayesinde)
                throw new RuntimeException("Kurs ekleme işlemi başarısız: " + e.getMessage());
            }

        }

        // Son durumu kaydet
        savedStudent = studentRepository.save(savedStudent);
        return new StudentResponseRecord(savedStudent.getId(),
                savedStudent.getFirstName(),savedStudent.getLastName(),savedStudent.getEmail(),savedStudent.getCourses());
    }

    private String generateSecurePassword() {
        return "student";
    }

    @Override
    public List<Student> findAll() {
        return studentRepository.findAll();
    }

    @Override
    public Student findById(Long id) {
        Optional<Student> studentOptional= studentRepository.findById(id);
        if(studentOptional.isPresent()){
            return studentOptional.get();
        }else{
            throw new RuntimeException("Bu id de kayıt bulunamadı! "+id);
        }

    }




    @Override
    public Student assignCourseForStudent(Long studentId,long courseId) {
        Student student=findById(studentId);
        Course course=courseRepository.findById(courseId).orElseThrow(()->
                new RuntimeException(courseId+": id'sinde kurs bulunamadı!")
                );
        student.addCourse(course);

        return studentRepository.save(student);
    }

    @Override
    @Transactional
    public Student removeCourseForStudent(Long studentId, Long courseId) {
        Student student=studentRepository.findById(studentId).orElseThrow(()->
                new RuntimeException(studentId+":id'li öğrenci bulunamadı!")
                );
        Course course=courseRepository.findById(courseId).orElseThrow(()->
                new RuntimeException(courseId+": id'sinde kurs bulunamadı!")
                );
        student.removeCourse(course);
        return studentRepository.save(student);
    }

    @Override
    public List<Student> searchByFirstName(String firstName) {
        return studentRepository.searchByFirstName(firstName);
    }

    @Override
    public List<Student> searchByGender(Gender gender) {
        return studentRepository.searchByGender(gender);
    }

    @Override
    public Student searchByEmail(String email) {
        return studentRepository.searchByEmail(email);
    }

    @Override
    @Transactional
    public Student delete(Long id) {
        Student student=findById(id);
        studentRepository.delete(student);
        return student;
    }
}
