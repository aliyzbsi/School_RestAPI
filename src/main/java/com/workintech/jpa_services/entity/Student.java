package com.workintech.jpa_services.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
