package com.workintech.jpa_services.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "student",schema = "springweb")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private Long id;

    @JsonProperty("firstName")
    @Column(name = "first_name",nullable = false)
    @NotNull
    @NotBlank
    @Size(min = 3,max = 45)
    private String firstName;

    @JsonProperty("lastName")
    @Column(name = "last_name",nullable = false)
    @NotNull
    @NotBlank
    @Size(min = 3,max = 45)
    private String lastName;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender",nullable = false)
    @JsonProperty("gender")
    private Gender gender;

    @JsonProperty("email")
    @Column(name = "email",nullable = false)
    @Size(min = 10,max = 50)
    //@Email
    private String email;

    @JsonProperty("salary")
    @Column(name = "salary",nullable = false)
    @Min(value = 2000)
    private int salary;

    @ManyToMany(cascade = {CascadeType.DETACH,CascadeType.PERSIST,CascadeType.REFRESH,CascadeType.MERGE})
    @JoinTable(name = "student_course",schema = "springweb",
    joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "courses_id")
    )
    @JsonIgnore
    private List<Course> courses=new ArrayList<>();


    public void addCourse(Course course){
        if(courses==null){
            courses=new ArrayList<>();
        }
        if(courses.size() >= 3) {
            throw new RuntimeException("Bir öğrenci en fazla 3 kurs alabilir! Mevcut kurs sayısı: " + courses.size());
        }

        if(courses.contains(course)) {
            throw new RuntimeException("Bu kurs zaten eklenmiş: " + course.getTitle());
        }
        courses.add(course);
    }

    public void removeCourse(Course course){
        if (!courses.contains(course)){
            throw new RuntimeException("Öğrencinin kursları arasında bu kurs bulunamadı!");
        }
        courses.remove(course);
    }

    @Override
    public String toString() {
        return "Student{" +
                "email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", gender=" + gender +
                ", id=" + id +
                ", lastName='" + lastName + '\'' +
                ", salary=" + salary +
                '}';
    }
}
