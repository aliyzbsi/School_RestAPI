package com.workintech.jpa_services.service;

import com.workintech.jpa_services.entity.Instructor;

import java.util.List;

public interface InstructorService {

    Instructor save(Instructor instructor);
    List<Instructor> findAll();
    Instructor findById(long id);
    Instructor remove(long id);
}
