package com.workintech.jpa_services.service;

import com.workintech.jpa_services.entity.Instructor;
import com.workintech.jpa_services.repository.InstructorRepository;
import com.workintech.jpa_services.user.ApplicationUser;
import com.workintech.jpa_services.user.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorServiceImpl implements InstructorService{

    private final InstructorRepository instructorRepository;
    private final AuthenticationService authenticationService;

    @Autowired
    public InstructorServiceImpl(InstructorRepository instructorRepository,AuthenticationService authenticationService) {
        this.instructorRepository = instructorRepository;
        this.authenticationService=authenticationService;
    }

    @Override
    public Instructor save(Instructor instructor) {

        String defaultPassword=generateSecurePassword();
        ApplicationUser insturtorUser=authenticationService.register(
                instructor.getFirstName()+" "+instructor.getLastName(),
                instructor.getEmail(),
                defaultPassword,
                new Role(0,"INSTRUCTOR")
        );

        return instructorRepository.save(instructor);
    }

    private String generateSecurePassword() {
        return "instructor";
    }

    @Override
    public List<Instructor> findAll() {
        return instructorRepository.findAll();
    }

    @Override
    public Instructor findById(long id) {
        Optional<Instructor> instructorOptional = instructorRepository.findById(id);
        if(instructorOptional.isPresent()){
            return instructorOptional.get();
        }else {
            throw new RuntimeException(id+" bulunamadı burası çalıştı !");
        }
    }

    @Override
    public Instructor remove(long id) {
       Instructor willRemoveInstructor=findById(id);
       instructorRepository.delete(willRemoveInstructor);
       return willRemoveInstructor;

    }
}
