package com.nexterp.employee;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.employee
 * FileName       : EmployeeDataInitializer
 * Author         : paesir
 * Date           : 25. 1. 17.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 17.오후 4:20  paesir      최초 생성
 */

import com.nexterp.employee.dto.EmployeeDTO;
import com.nexterp.employee.entity.Department;
import com.nexterp.employee.entity.Position;
import com.nexterp.employee.repository.DepartmentRepository;
import com.nexterp.employee.repository.PositionRepository;
import com.nexterp.employee.service.EmployeeService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Configuration
public class EmployeeDataInitializer {
  @Bean
  public ApplicationRunner initializeDepartments(DepartmentRepository departmentRepository) {
    return args -> {
      createDepartmentIfNotExists(departmentRepository, "영업팀", "sales@company.com");
      createDepartmentIfNotExists(departmentRepository, "회계팀", "accounting@company.com");
      createDepartmentIfNotExists(departmentRepository, "인사팀", "hr@company.com");
    };
  }

  private void createDepartmentIfNotExists(DepartmentRepository departmentRepository, String name, String contactEmail) {
    if (departmentRepository.findAll().stream().noneMatch(dept -> dept.getName().equals(name))) {
      Department department = new Department();
      department.setName(name);
      department.setContactEmail(contactEmail);
      departmentRepository.save(department);
    }
  }

  @Bean
  public ApplicationRunner initializePositions(PositionRepository positionRepository) {
    return args -> {
      createPositionIfNotExists(positionRepository, "사원", "User");
      createPositionIfNotExists(positionRepository, "대리", "User");
      createPositionIfNotExists(positionRepository, "과장", "User");
      createPositionIfNotExists(positionRepository, "차장", "Admin");
      createPositionIfNotExists(positionRepository, "부장", "Admin");
    };
  }

  private void createPositionIfNotExists(PositionRepository positionRepository, String title, String role) {
    if (positionRepository.findAll().stream().noneMatch(position -> position.getTitle().equals(title))) {
      Position position = new Position();
      position.setTitle(title);
      position.setRole(role);
      positionRepository.save(position);
    }
  }

  @Bean
  public ApplicationRunner initializeAdminEmployee(EmployeeService employeeService) {
    return args -> {
      // 관리자 초기 데이터 설정
      EmployeeDTO adminEmployee = new EmployeeDTO();
      adminEmployee.setId(12341234);
      adminEmployee.setName("관리자");
      adminEmployee.setBirthDate(LocalDate.of(1990, 1, 1));
      adminEmployee.setGender(true);
      adminEmployee.setPhone("010-0000-0000");
      adminEmployee.setEmail("admin@admin.com");
      adminEmployee.setAddress("서울 강남");

      // 부서 및 직위 ID를 설정 (DB에 기본 값이 있어야 함)
      adminEmployee.setDepartmentId(3); // 기본 부서 ID
      adminEmployee.setPositionId(5); // 기본 직위 ID

      // 입사일 및 퇴사일 설정
      adminEmployee.setHireDate(LocalDate.now());
      adminEmployee.setTerminationDate(null); // 퇴사일 없음

      // Employee 생성 및 저장
      employeeService.saveEmployee(adminEmployee);
    };
  }
}
