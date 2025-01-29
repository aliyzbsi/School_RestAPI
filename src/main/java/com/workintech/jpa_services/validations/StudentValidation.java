package com.workintech.jpa_services.validations;

import com.workintech.jpa_services.entity.Student;
import com.workintech.jpa_services.exceptions.StudentException;
import org.springframework.http.HttpStatus;

import java.util.Optional;

public class StudentValidation {
    public static void studentExist(Student student) {
        if(student!=null){
            throw new StudentException("Bu email adresi başka bir öğrenci tarafından kullanılıyor! ",HttpStatus.BAD_REQUEST);
        }
    }
}
