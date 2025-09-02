package com.example.demodigitalocean.controller;


import com.example.demodigitalocean.entity.Classroom;
import com.example.demodigitalocean.entity.Student;
import com.example.demodigitalocean.repository.ClassroomRepository;
import com.example.demodigitalocean.repository.StudentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {


    private final StudentRepository studentRepo;
    private final ClassroomRepository classroomRepo;


    public StudentController(StudentRepository studentRepo, ClassroomRepository classroomRepo) {
        this.studentRepo = studentRepo;
        this.classroomRepo = classroomRepo;
    }


    // Create
    @PostMapping
    public Student create(@RequestBody Student s) {
        if (s.getEmail() != null && studentRepo.existsByEmail(s.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }
// if payload contains classroom id only, resolve it
        if (s.getClassroom() != null && s.getClassroom().getId() != null) {
            Classroom c = classroomRepo.findById(s.getClassroom().getId())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Classroom not found"));
            s.setClassroom(c);
        }
        return studentRepo.save(s);
    }


    // Read one
    @GetMapping("/{id}")
    public Student get(@PathVariable Long id) {
        return studentRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
    }


    // List all
    @GetMapping
    public List<Student> list() {
        return studentRepo.findAll();
    }


    // Update
    @PutMapping("/{id}")
    public Student update(@PathVariable Long id, @RequestBody Student s) {
        Student existing = studentRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
        existing.setFullName(s.getFullName());
        existing.setEmail(s.getEmail());
        existing.setAge(s.getAge());
        if (s.getClassroom() != null) {
            if (s.getClassroom().getId() == null) {
                existing.setClassroom(null);
            } else {
                Classroom c = classroomRepo.findById(s.getClassroom().getId())
                        .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Classroom not found"));
                existing.setClassroom(c);
            }
        }
        return studentRepo.save(existing);
    }


    // Delete
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Student existing = studentRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
        studentRepo.delete(existing);
    }
}