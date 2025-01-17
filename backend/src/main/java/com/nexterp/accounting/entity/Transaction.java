package com.nexterp.accounting.entity;

/*
 * Description    : 거래 테이블
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.entity
 * FileName       : Transaction
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오전 10:46  paesir      최초 생성
 */

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "transactions")
public class Transaction {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private LocalDateTime date;

  @Column(nullable = false)
  private BigDecimal amount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TransactionType type;

  private String description;

  @OneToMany(mappedBy = "transaction", cascade = CascadeType.ALL)
  private List<JournalEntry> journalEntries;

  @OneToOne(mappedBy = "transaction", cascade = CascadeType.ALL)
  private Invoice invoice;

  @Column(updatable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column
  private LocalDateTime updatedAt;

  @PreUpdate
  public void setUpdatedAt() {
    this.updatedAt = LocalDateTime.now();
  }
}
