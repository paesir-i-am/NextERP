package com.nexterp.accounting.repository;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.repository
 * FileName       : JournalEntryRepository
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오후 12:03  paesir      최초 생성
 */


import com.nexterp.accounting.entity.Account;
import com.nexterp.accounting.entity.JournalEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {
  // 특정 날짜 범위의 분개를 페이지네이션으로 조회
  Page<JournalEntry> findByDateBetween(LocalDate startDate, LocalDate endDate, Pageable pageable);

  // 특정 계정 코드의 분개를 페이지네이션으로 조회
  Page<JournalEntry> findByAccount_Code(String accountCode, Pageable pageable);

  // 특정 계정 코드와 날짜 범위의 분개를 페이지네이션으로 조회
  Page<JournalEntry> findByAccount_CodeAndDateBetween(String accountCode, LocalDate startDate, LocalDate endDate, Pageable pageable);

  // 모든 분개를 페이지네이션으로 조회
  Page<JournalEntry> findAll(Pageable pageable);

  //특정 계정의 총 금액 합계
  @Query("SELECT SUM(j.amount) FROM JournalEntry j WHERE j.account.code = :accountCode")
  BigDecimal getTotalAmountByAccountCode(@Param("accountCode") String accountCode);

  List<JournalEntry> findByTransactionId(Long transactionId);
}
