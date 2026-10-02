package com.charan.erp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.charan.erp.dto.Studentrequest;
import com.charan.erp.entity.Student;
import com.charan.erp.service.Studentservices;

@RestController
@RequestMapping("/api")
public class Studentcontroller {
    private final Studentservices studentservices;

    public Studentcontroller(Studentservices studentservices) {
        this.studentservices = studentservices;
    }

    @GetMapping("/students")
    public List<Student> getStudents(@RequestParam(required = false) String name,
            @RequestParam(required = false) String registerNo,
            @RequestParam(required = false) String department,
            @RequestParam(required = false) Integer year,
            @RequestParam(required = false) Integer semester) {
        return studentservices.getStudents(name, registerNo, department, year, semester);
    }

    @GetMapping("/students/{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentservices.getStudent(id);
    }

    @PostMapping("/students")
    @ResponseStatus(HttpStatus.CREATED)
    public Student createStudent(@Validated @RequestBody Studentrequest request) {
        return studentservices.createStudent(request);
    }

    @PutMapping("/students/{id}")
    public Student updateStudent(@PathVariable Long id, @Validated @RequestBody Studentrequest request) {
        return studentservices.updateStudent(id, request);
    }

    @DeleteMapping("/students/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Long id) {
        studentservices.deleteStudent(id);
    }

    @GetMapping("/students/statistics")
    public Map<String, Object> getStatistics() {
        return studentservices.getStatistics();
    }
}
