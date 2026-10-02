package com.charan.erp.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.charan.erp.entity.Student;

public interface Studentrepository extends JpaRepository<Student, Long> {
    Optional<Student> findByRegisterNo(String registerNo);
    List<Student> findByNameContainingIgnoreCase(String name);
    List<Student> findByDepartment_DepartmentNameContainingIgnoreCase(String departmentName);
    List<Student> findByDepartmentIdAndYearAndSemester(Long departmentId, Integer year, Integer semester);

        @Query("SELECT s FROM Student s WHERE (:name IS NULL OR LOWER(s.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND (:registerNo IS NULL OR LOWER(s.registerNo) = LOWER(:registerNo)) AND (:department IS NULL OR LOWER(s.department.departmentName) = LOWER(:department)) AND (:year IS NULL OR s.year = :year) AND (:semester IS NULL OR s.semester = :semester)")
        List<Student> search(@Param("name") String name, @Param("registerNo") String registerNo,
            @Param("department") String department, @Param("year") Integer year,
            @Param("semester") Integer semester);

    @Query("SELECT d.departmentName AS departmentName, COUNT(s.id) AS total FROM Student s RIGHT JOIN s.department d GROUP BY d.departmentName")
    List<DepartmentCount> countByDepartment();

    @Query("SELECT s.year AS year, COUNT(s.id) AS total FROM Student s GROUP BY s.year ORDER BY s.year ASC")
    List<YearCount> countByYear();

    interface DepartmentCount {
        String getDepartmentName();
        Long getTotal();
    }

    interface YearCount {
        Integer getYear();
        Long getTotal();
    }
}
