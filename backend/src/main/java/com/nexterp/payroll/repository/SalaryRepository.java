package com.nexterp.payroll.repository;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.payroll.repository
 * FileName       : SalaryRepository
 * Author         : paesir
 * Date           : 25. 1. 19.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 19.오후 11:06  paesir      최초 생성
 */


import com.nexterp.payroll.entity.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SalaryRepository extends JpaRepository<Salary, Long> {
  List<Salary> findByEmployeeId(Long employeeId);
  List<Salary> findByStatus(String status); // Pending, Paid 상태별 검색
}
