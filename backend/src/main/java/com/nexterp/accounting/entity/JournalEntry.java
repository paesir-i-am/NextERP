package com.nexterp.accounting.entity;

/*
 * Description    : 분개 테이블
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.entity
 * FileName       : JournalEntry
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오전 10:45  paesir      최초 생성
 */

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "journal_entries")
public class JournalEntry {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private LocalDate date;

  @ManyToOne
  @JoinColumn(name = "account_code", nullable = false)
  private Account account;

  @Column(nullable = false)
  private BigDecimal debit = BigDecimal.ZERO;

  @Column(nullable = false)
  private BigDecimal credit = BigDecimal.ZERO;

  private String description;

  @ManyToOne
  @JoinColumn(name = "transaction_id")
  private Transaction transaction;

  @Column(nullable = false, precision = 19, scale = 2) // 금액 필드
  private BigDecimal amount;

  @Column(updatable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column
  private LocalDateTime updatedAt;

  @PreUpdate
  public void setUpdatedAt() {
    this.updatedAt = LocalDateTime.now();
  }
}
