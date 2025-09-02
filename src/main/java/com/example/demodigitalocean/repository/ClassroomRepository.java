package com.example.demodigitalocean.repository;


import com.example.demodigitalocean.entity.Classroom;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.Optional;


public interface ClassroomRepository extends JpaRepository<Classroom, Long> {
    Optional<Classroom> findByName(String name);
}