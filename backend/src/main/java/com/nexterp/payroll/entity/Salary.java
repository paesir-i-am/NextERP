package com.nexterp.payroll.entity;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.payroll.entity
 * FileName       : salary
 * Author         : paesir
 * Date           : 25. 1. 19.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 19.오후 2:13  paesir      최초 생성
 */


import com.nexterp.accounting.entity.Transaction;
import com.nexterp.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "salaries")
public class Salary {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "employee_id", nullable = false)
  private Employee employee; // 직원 정보와 연관

  @Column(nullable = false)
  private BigDecimal baseSalary; // 기본급

  @Column
  private BigDecimal bonus; // 보너스

  @Column
  private BigDecimal deductions; // 공제액

  @Column(nullable = false)
  private BigDecimal totalSalary; // 총 급여 (기본급 + 보너스 - 공제액)

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private PaymentStatus status; // 급여 상태 (예: PENDING, PAID)

  @Column
  private LocalDate paymentDate; // 지급일

  @Column(updatable = false)
  private LocalDateTime createdAt = LocalDateTime.now(); // 생성일시

  @Column
  private LocalDateTime updatedAt; // 수정일시

  @PreUpdate
  public void setUpdatedAt() {
    this.updatedAt = LocalDateTime.now();
  }
}