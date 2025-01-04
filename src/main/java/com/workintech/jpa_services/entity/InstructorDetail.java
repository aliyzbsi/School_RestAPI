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
@Table(name = "instructor_detail",schema = "springweb")
public class InstructorDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    @JsonProperty("id")
    private Long id;

    @Column(name = "hobby")
    @JsonProperty("hobby")
    private String hobby;

    @Column(name = "channel_url")
    @JsonProperty("channelUrl")
    private String channelUrl;

    // Eğer hem Instructor hemde InstructorDetail içerisinde bağlarsak Bi-directional
    @OneToOne(cascade = {CascadeType.DETACH,CascadeType.MERGE,CascadeType.PERSIST,CascadeType.REFRESH},mappedBy ="instructorDetail" )
    private Instructor instructor;

}
