package com.example.demodigitalocean.repository;


import com.example.demodigitalocean.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;


public interface StudentRepository extends JpaRepository<Student, Long> {
    boolean existsByEmail(String email);
}