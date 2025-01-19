package com.nexterp.accounting.entity;

/*
 * Description    : 계정과목 테이블
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.entity
 * FileName       : Account
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오전 10:43  paesir      최초 생성
 */

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
public class Account {
  @Id
  @Column(length = 10)
  private String code;

  @Column(nullable = false)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private AccountType type;

  @ManyToOne
  @JoinColumn(name = "parent_code")
  private Account parent;

  @OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
  private List<Account> children;

  private String description;

  @Column(nullable = false, precision = 19, scale = 2) // 소수점 포함
  private BigDecimal balance = BigDecimal.ZERO;


  @Column(updatable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column
  private LocalDateTime updatedAt;

  @PreUpdate
  public void setUpdatedAt() {
    this.updatedAt = LocalDateTime.now();
  }

  public Account(String code, String name, AccountType type, Account parent, String description, BigDecimal balance) {
    this.code = code;
    this.name = name;
    this.type = type;
    this.parent = parent;
    this.description = description;
    this.balance = balance;
  }

  // 잔액 증가
  public void addBalance(BigDecimal amount) {
    this.balance = this.balance.add(amount);
  }

  // 잔액 감소
  public void subtractBalance(BigDecimal amount) {
    this.balance = this.balance.subtract(amount);
  }
}
