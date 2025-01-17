package com.nexterp.accounting.service;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.service
 * FileName       : TransactionService
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오후 2:44  paesir      최초 생성
 */


import com.nexterp.accounting.dto.TransactionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TransactionService {
  Page<TransactionDTO> getAllTransactions(Pageable pageable);
  TransactionDTO getTransactionById(Long id);
  TransactionDTO createTransaction(TransactionDTO transactionDTO);
  TransactionDTO updateTransaction(Long id, TransactionDTO transactionDTO);
  void deleteTransaction(Long id);
}
