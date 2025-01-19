package com.nexterp.employee.repository;

import com.nexterp.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

        boolean existsById(int id);

        // 부서별 조회
        @Query("SELECT e FROM Employee e WHERE e.department.id = :departmentId")
        List<Employee> findByDepartmentId(@Param("departmentId") Integer departmentId);

        // 직급별 조회
        @Query("SELECT e FROM Employee e WHERE e.position.id = :positionId")
        List<Employee> findByPositionId(@Param("positionId") Integer positionId);

        // 부서와 직급별 조회
        @Query("SELECT e FROM Employee e WHERE e.department.id = :departmentId AND e.position.id = :positionId")
        List<Employee> findByDepartmentAndPosition(@Param("departmentId") Integer departmentId,
                                                   @Param("positionId") Integer positionId);
    }

