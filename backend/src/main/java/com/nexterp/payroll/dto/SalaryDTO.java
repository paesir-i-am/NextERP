package com.nexterp.payroll.dto;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.payroll.dto
 * FileName       : SalaryDTO
 * Author         : paesir
 * Date           : 25. 1. 19.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 19.오후 10:57  paesir      최초 생성
 */

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SalaryDTO {
  private Long id;
  private Integer employeeId; // Employee ID
  private BigDecimal baseSalary; // 기본급
  private BigDecimal bonus; // 보너스
  private BigDecimal deductions; // 공제액
  private BigDecimal totalSalary; // 총 급여
  private String status; // 상태 (예: PENDING, PAID)
  private String paymentDate; // 지급일 (YYYY-MM-DD 포맷)
}
