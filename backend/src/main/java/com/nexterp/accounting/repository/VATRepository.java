package com.nexterp.accounting.repository;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.repository
 * FileName       : VATRepository
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오후 5:29  paesir      최초 생성
 */

import com.nexterp.accounting.entity.VAT;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VATRepository extends JpaRepository<VAT, Long> {
  // 트랜잭션 ID로 VAT 조회
  VAT findByTransactionId(Long transactionId);
}
