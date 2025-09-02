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
@RequestMapping("/api/classes")
public class ClassroomController {

    private final ClassroomRepository classRepo;
    private final StudentRepository studentRepo;


    public ClassroomController(ClassroomRepository classRepo, StudentRepository studentRepo) {
        this.classRepo = classRepo;
        this.studentRepo = studentRepo;
    }


    // Create class
    @PostMapping
    public Classroom create(@RequestBody Classroom c) {
        return classRepo.save(c);
    }


    // Read one class (returns studentCount)
    @GetMapping("/{id}")
    public Classroom get(@PathVariable Long id) {
        return classRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Classroom not found"));
    }


    // List classes
    @GetMapping
    public List<Classroom> list() {
        return classRepo.findAll();
    }


    // Update class
    @PutMapping("/{id}")
    public Classroom update(@PathVariable Long id, @RequestBody Classroom c) {
        Classroom existing = classRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Classroom not found"));
        existing.setName(c.getName());
        existing.setDescription(c.getDescription());
        return classRepo.save(existing);
    }


    // Delete class (unassign students first to avoid FK issues)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        Classroom existing = classRepo.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Classroom not found"));
        existing.getStudents().forEach(s -> {
            s.setClassroom(null);
            studentRepo.save(s);
        });
        classRepo.delete(existing);
    }


    // Assign a student to a class
    @PostMapping("/{classId}/students/{studentId}")
    public Student assignStudent(@PathVariable Long classId, @PathVariable Long studentId) {
        Classroom c = classRepo.findById(classId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Classroom not found"));
        Student s = studentRepo.findById(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
        s.setClassroom(c);
        return studentRepo.save(s);
    }


    // Remove a student from a class (set classroom to null)
    @DeleteMapping("/{classId}/students/{studentId}")
    public Student unassignStudent(@PathVariable Long classId, @PathVariable Long studentId) {
        classRepo.findById(classId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Classroom not found"));
        Student s = studentRepo.findById(studentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
        s.setClassroom(null);
        return studentRepo.save(s);
    }
}