package com.nexterp.employee.service;

import com.nexterp.employee.dto.DepartmentDTO;
import com.nexterp.employee.entity.Department;
import com.nexterp.employee.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }
    // DTO 변환 메서드
    private DepartmentDTO convertToDTO(Department department) {
        return new DepartmentDTO(
                department.getDepartmentId(),
                department.getName(),
                department.getContactEmail()
        );
    }

    // 부서 전체 조회 (DTO 반환)
    public List<DepartmentDTO> getAllDepartments() {
        List<Department> departments = departmentRepository.findAll();
        return departments.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


  // 특정 부서 조회 (DTO 반환)
    public DepartmentDTO getDepartmentById(Integer id) {
        Department department = departmentRepository.findById(id).orElse(null);
        if (department != null) {
            return convertToDTO(department);
        }
        return null;
    }
    // 부서 생성
    public DepartmentDTO createDepartment(DepartmentDTO departmentDTO) {
        Department department = new Department();
        department.setName(departmentDTO.getName());
        department.setContactEmail(departmentDTO.getContactEmail());
        Department savedDepartment = departmentRepository.save(department);
        return convertToDTO(savedDepartment);
    }

    // 부서 수정
    public DepartmentDTO updateDepartment(Integer id, DepartmentDTO updatedDepartmentDTO) {
        Department existingDepartment = departmentRepository.findById(id).orElse(null);
        if (existingDepartment != null) {
            existingDepartment.setName(updatedDepartmentDTO.getName());
            existingDepartment.setContactEmail(updatedDepartmentDTO.getContactEmail());
            Department savedDepartment = departmentRepository.save(existingDepartment);
            return convertToDTO(savedDepartment);
        }
        return null;
    }

    // 부서 삭제
    public boolean deleteDepartment(Integer id) {
        if (departmentRepository.existsById(id)) {
            departmentRepository.deleteById(id);
            return true;
        }
        return false;
    }
}