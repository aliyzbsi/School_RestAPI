package com.workintech.jpa_services.dto;

import com.workintech.jpa_services.entity.Course;

import java.util.List;

public record StudentResponseRecord(long id, String firstName, String lastName, String email, List<Course> courses) {
}
