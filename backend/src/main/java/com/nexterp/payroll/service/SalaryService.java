package com.nexterp.payroll.service;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.payroll.service
 * FileName       : SalaryService
 * Author         : paesir
 * Date           : 25. 1. 19.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 19.오후 11:08  paesir      최초 생성
 */


import com.nexterp.payroll.dto.SalaryDTO;

public interface SalaryService {
    SalaryDTO createSalary(SalaryDTO salaryDTO);
}
