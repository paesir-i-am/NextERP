package com.nexterp.employee.service;

import com.nexterp.employee.entity.Department;
import com.nexterp.employee.repository.DepartmentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    // 부서 전체 조회
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }

    // 특정 부서 조회
    public Department getDepartmentById(Integer id) {
        return departmentRepository.findById(id).orElse(null);
    }

    // 부서 생성
    public Department createDepartment(Department department) {
        return departmentRepository.save(department);
    }

    // 부서 수정
    public Department updateDepartment(Integer id, Department updatedDepartment) {
        Department existingDepartment = departmentRepository.findById(id).orElse(null);
        if (existingDepartment != null) {
            existingDepartment.setName(updatedDepartment.getName());
            existingDepartment.setContactEmail(updatedDepartment.getContactEmail());
            return departmentRepository.save(existingDepartment);
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
