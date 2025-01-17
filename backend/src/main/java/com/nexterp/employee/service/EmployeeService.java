package com.nexterp.employee.service;

import com.nexterp.employee.dto.EmployeeDTO;
import com.nexterp.employee.entity.Department;
import com.nexterp.employee.entity.Employee;
import com.nexterp.employee.entity.Position;
import com.nexterp.employee.repository.DepartmentRepository;
import com.nexterp.employee.repository.EmployeeRepository;
import com.nexterp.employee.repository.PositionRepository;
import com.nexterp.member.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PositionRepository positionRepository;
    private final MemberService memberService;

    public EmployeeService(EmployeeRepository employeeRepository,
                           DepartmentRepository departmentRepository,
                           PositionRepository positionRepository,
                           MemberService memberService) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.positionRepository = positionRepository;
        this.memberService = memberService;
    }

    @Transactional
    public Employee saveEmployee(EmployeeDTO employeeDTO) {
        // 부서 조회
        Department department = departmentRepository.findById(employeeDTO.getDepartmentId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid department ID"));

        // 직위 조회
        Position position = positionRepository.findById(employeeDTO.getPositionId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid position ID"));

        // Employee 생성
        Employee employee = new Employee();
        employee.setId(employeeDTO.getId());
        employee.setName(employeeDTO.getName());
        employee.setBirthDate(employeeDTO.getBirthDate());
        employee.setGender(employeeDTO.getGender());
        employee.setPhone(employeeDTO.getPhone());
        employee.setEmail(employeeDTO.getEmail());
        employee.setAddress(employeeDTO.getAddress());
        employee.setDepartment(department);
        employee.setPosition(position);
        employee.setHireDate(employeeDTO.getHireDate());
        employee.setTerminationDate(employeeDTO.getTerminationDate());

        // Employee 저장
        Employee savedEmployee = employeeRepository.save(employee);

        // Member 생성 (MemberService 호출)
        memberService.createMember(savedEmployee);

        return savedEmployee;
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public EmployeeDTO getEmployeeById(Integer id) {
        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found with ID: " + id));
        return convertToDTO(employee);
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getEmployeesByDepartment(Integer departmentId) {
        return employeeRepository.findByDepartmentId(departmentId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getEmployeesByPosition(Integer positionId) {
        return employeeRepository.findByPositionId(positionId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<EmployeeDTO> getEmployeesByDepartmentAndPosition(Integer departmentId, Integer positionId) {
        return employeeRepository.findByDepartmentAndPosition(departmentId, positionId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    private EmployeeDTO convertToDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getId(),
                employee.getName(),
                employee.getBirthDate(),
                employee.getGender(),
                employee.getPhone(),
                employee.getEmail(),
                employee.getAddress(),
                employee.getDepartment().getDepartmentId(), // 수정된 부분
                employee.getPosition().getPositionId(),
                employee.getHireDate(),
                employee.getTerminationDate()
        );
    }
}
