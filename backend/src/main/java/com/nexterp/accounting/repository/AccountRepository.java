package com.nexterp.accounting.repository;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.repository
 * FileName       : AccountRepository
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오전 11:20  paesir      최초 생성
 */


import com.nexterp.accounting.entity.Account;
import com.nexterp.accounting.entity.AccountType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {
  // 모든 계정을 페이지네이션으로 조회
  Page<Account> findAll(Pageable pageable);

  // 특정 계정 유형의 계정을 페이지네이션으로 조회
  Page<Account> findByType(AccountType type, Pageable pageable);

  // 특정 부모 계정의 하위 계정을 페이지네이션으로 조회
  Page<Account> findByParentCode(String parentCode, Pageable pageable);

  // 특정 타입의 계정 총합을 구하는 메서드
  @Query("SELECT SUM(a.balance) FROM Account a WHERE a.type = :type")
  BigDecimal getTotalBalanceByType(@Param("type") AccountType type);
}
