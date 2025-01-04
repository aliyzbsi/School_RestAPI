package com.workintech.jpa_services.service;

import com.workintech.jpa_services.entity.Gender;
import com.workintech.jpa_services.entity.Student;
import com.workintech.jpa_services.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService{

    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository){
        this.studentRepository=studentRepository;
    }

    @Override
    public Student save(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student cannot be null");
        }
        return studentRepository.save(student);
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
    public List<Student> searchByFirstName(String firstName) {
        return studentRepository.searchByFirstName(firstName);
    }

    @Override
    public List<Student> searchByGender(Gender gender) {
        return studentRepository.searchByGender(gender);
    }

    @Override
    public Student delete(Long id) {
        Student student=findById(id);
        studentRepository.delete(student);
        return student;
    }
}
