package com.nexterp.accounting.entity;

/*
 * Description    : 부가세 테이블
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.entity
 * FileName       : VAT
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오전 10:48  paesir      최초 생성
 */

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "vat")
public class VAT {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @OneToOne
  @JoinColumn(name = "transaction_id", nullable = false)
  private Transaction transaction;

  @Column(nullable = false)
  private BigDecimal vatRate;

  @Column(nullable = false)
  private BigDecimal vatAmount;

  @Column(updatable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column
  private LocalDateTime updatedAt;

  @PreUpdate
  public void setUpdatedAt() {
    this.updatedAt = LocalDateTime.now();
  }
}
