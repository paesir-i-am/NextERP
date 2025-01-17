package com.nexterp.employee.controller;

import com.nexterp.employee.dto.EmployeeDTO;
import com.nexterp.employee.entity.Employee;
import com.nexterp.employee.service.EmployeeService;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Employee> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        Employee savedEmployee = employeeService.saveEmployee(employeeDTO);
        return ResponseEntity.ok(savedEmployee);
    }

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable Integer id) {
        EmployeeDTO employee = employeeService.getEmployeeById(id);
        return ResponseEntity.ok(employee);
    }

    @GetMapping("/department/{departmentId}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByDepartment(@PathVariable Integer departmentId) {
        List<EmployeeDTO> employees = employeeService.getEmployeesByDepartment(departmentId);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/position/{positionId}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByPosition(@PathVariable Integer positionId) {
        List<EmployeeDTO> employees = employeeService.getEmployeesByPosition(positionId);
        return ResponseEntity.ok(employees);
    }

    @GetMapping("/department/{departmentId}/position/{positionId}")
    public ResponseEntity<List<EmployeeDTO>> getEmployeesByDepartmentAndPosition(
            @PathVariable Integer departmentId,
            @PathVariable Integer positionId) {
        List<EmployeeDTO> employees = employeeService.getEmployeesByDepartmentAndPosition(departmentId, positionId);
        return ResponseEntity.ok(employees);
    }
}
