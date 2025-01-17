package com.nexterp.employee.controller;

import com.nexterp.employee.entity.Employee;
import com.nexterp.employee.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.saveEmployee(employee);
    }

    @GetMapping
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Integer id) {
        return employeeService.getEmployeeById(id);
    }

    // 부서별 조회
    @GetMapping("/department/{departmentId}")
    public List<Employee> getEmployeesByDepartment(@PathVariable Integer departmentId) {
        return employeeService.getEmployeesByDepartment(departmentId);
    }

    // 직급별 조회
    @GetMapping("/position/{positionId}")
    public List<Employee> getEmployeesByPosition(@PathVariable Integer positionId) {
        return employeeService.getEmployeesByPosition(positionId);
    }

    // 부서별 + 직급별 조회
    @GetMapping("/department/{departmentId}/position/{positionId}")
    public List<Employee> getEmployeesByDepartmentAndPosition(
            @PathVariable Integer departmentId,
            @PathVariable Integer positionId) {
        return employeeService.getEmployeesByDepartmentAndPosition(departmentId, positionId);
    }
}

