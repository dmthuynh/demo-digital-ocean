package com.example.demodigitalocean.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.*;


import java.util.LinkedHashSet;
import java.util.Set;


@Entity
@Table(name = "classrooms")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Classroom {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String name;


    private String description;


    // EAGER for demo simplicity so studentCount works out of the box
    @OneToMany(mappedBy = "classroom", fetch = FetchType.EAGER)
    @Builder.Default
    @JsonIgnore // avoid infinite recursion; we expose only a count below
    private Set<Student> students = new LinkedHashSet<>();


    @Transient
    @JsonProperty("studentCount")
    public int getStudentCount() {
        return students == null ? 0 : students.size();
    }
}