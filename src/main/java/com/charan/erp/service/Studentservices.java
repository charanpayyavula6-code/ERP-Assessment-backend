package com.charan.erp.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.charan.erp.dto.Studentrequest;
import com.charan.erp.entity.Department;
import com.charan.erp.entity.Student;
import com.charan.erp.exception.DuplicateResourceException;
import com.charan.erp.exception.StudentNotFoundException;
import com.charan.erp.repository.DepartmentRepository;
import com.charan.erp.repository.Studentrepository;

@Service
public class Studentservices {
    private final Studentrepository studentRepository;
    private final DepartmentRepository departmentRepository;

    public Studentservices(Studentrepository studentRepository, DepartmentRepository departmentRepository) {
        this.studentRepository = studentRepository;
        this.departmentRepository = departmentRepository;
    }

    public Student createStudent(Studentrequest request) {
        if (studentRepository.findByRegisterNo(request.getRegisterNo()).isPresent()) {
            throw new DuplicateResourceException("Register number already exists");
        }
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new StudentNotFoundException("Department not found"));

        Student student = new Student();
        student.setRegisterNo(request.getRegisterNo());
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setDepartment(department);
        student.setYear(request.getYear());
        student.setSemester(request.getSemester());
        return studentRepository.save(student);
    }

    public List<Student> getStudents(String name, String registerNo, String department, Integer year, Integer semester) {
        return studentRepository.search(name, registerNo, department, year, semester);
    }

    public Student getStudent(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new StudentNotFoundException("Student not found with id: " + id));
    }

    public Student updateStudent(Long id, Studentrequest request) {
        Student student = getStudent(id);
        if (!student.getRegisterNo().equalsIgnoreCase(request.getRegisterNo())
                && studentRepository.findByRegisterNo(request.getRegisterNo()).isPresent()) {
            throw new DuplicateResourceException("Register number already exists");
        }
        Department department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new StudentNotFoundException("Department not found"));

        student.setRegisterNo(request.getRegisterNo());
        student.setName(request.getName());
        student.setEmail(request.getEmail());
        student.setPhone(request.getPhone());
        student.setDepartment(department);
        student.setYear(request.getYear());
        student.setSemester(request.getSemester());
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        Student student = getStudent(id);
        studentRepository.delete(student);
    }

    public Map<String, Object> getStatistics() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalStudents", studentRepository.count());
        stats.put("departmentWiseCount", studentRepository.countByDepartment());
        stats.put("yearWiseCount", studentRepository.countByYear());
        return stats;
    }
}

