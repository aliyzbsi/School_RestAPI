package com.workintech.jpa_services.service;

import com.workintech.jpa_services.entity.Instructor;
import com.workintech.jpa_services.repository.InstructorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class InstructorServiceImpl implements InstructorService{

    private InstructorRepository instructorRepository;

    @Autowired
    public InstructorServiceImpl(InstructorRepository instructorRepository) {
        this.instructorRepository = instructorRepository;
    }

    @Override
    public Instructor save(Instructor instructor) {
        return instructorRepository.save(instructor);
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
            throw new RuntimeException(id+" bulunamadı !");
        }
    }

    @Override
    public Instructor remove(long id) {
       Instructor willRemoveInstructor=findById(id);
       instructorRepository.delete(willRemoveInstructor);
       return willRemoveInstructor;

    }
}
