package com.charan.erp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.charan.erp.entity.Department;
import com.charan.erp.entity.Student;
import com.charan.erp.exception.DuplicateResourceException;
import com.charan.erp.exception.StudentNotFoundException;
import com.charan.erp.repository.DepartmentRepository;

@Service
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    public Department getDepartment(Long id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException("Department not found with id: " + id));
    }

    public Department createDepartment(Department department) {
        if (departmentRepository.findByDepartmentCode(department.getDepartmentCode()).isPresent()) {
            throw new DuplicateResourceException("Department code already exists");
        }
        return departmentRepository.save(department);
    }

    public Department updateDepartment(Long id, Department department) {
        Department existing = getDepartment(id);
        if (!existing.getDepartmentCode().equalsIgnoreCase(department.getDepartmentCode())
                && departmentRepository.findByDepartmentCode(department.getDepartmentCode()).isPresent()) {
            throw new DuplicateResourceException("Department code already exists");
        }
        existing.setDepartmentCode(department.getDepartmentCode());
        existing.setDepartmentName(department.getDepartmentName());
        return departmentRepository.save(existing);
    }

    public void deleteDepartment(Long id) {
        Department department = getDepartment(id);
        departmentRepository.delete(department);
    }

    public List<Student> getStudentsByDepartment(Long id) {
        Department department = getDepartment(id);
        return department.getStudents();
    }
}
