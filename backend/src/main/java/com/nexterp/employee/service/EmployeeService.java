package com.nexterp.employee.service;

import com.nexterp.employee.entity.Employee;
import com.nexterp.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public Employee saveEmployee(Employee employee) {
        // 사원 번호가 8자리인지 확인
        if (employee.getId() == null || employee.getId().toString().length() != 8) {
            throw new IllegalArgumentException("사원 번호는 8자리여야 합니다.");
        }
        Optional<Employee> existingEmployee = employeeRepository.findById(employee.getId());
        if (existingEmployee.isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 사원 번호입니다: " + employee.getId());
        }
        // 다른 검증 로직 추가 가능
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Integer id) {
        return employeeRepository.findById(id).orElse(null);
    }

    public List<Employee> getEmployeesByDepartment(Integer departmentId) {
        return employeeRepository.findByDepartmentId(departmentId);
    }

    public List<Employee> getEmployeesByPosition(Integer positionId) {
        return employeeRepository.findByPositionId(positionId);
    }

    public List<Employee> getEmployeesByDepartmentAndPosition(Integer departmentId, Integer positionId) {
        return employeeRepository.findByDepartmentAndPosition(departmentId, positionId);
    }
}

