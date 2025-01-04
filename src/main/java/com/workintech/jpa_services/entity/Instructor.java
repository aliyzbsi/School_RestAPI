package com.workintech.jpa_services.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "instructor",schema = "springweb")
public class Instructor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private Long id;

    @Column(name = "first_name")
    @JsonProperty("firstName")
    private String firstName;

    @Column(name = "last_name")
    @JsonProperty("lastName")
    private String lastName;

    @Column(name = "email")
    @JsonProperty("email")
    private String email;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "instructor_detail_id")
    @JsonProperty("instructorDetail")
    private InstructorDetail instructorDetail;


}
