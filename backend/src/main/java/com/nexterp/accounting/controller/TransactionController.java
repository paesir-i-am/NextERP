package com.nexterp.accounting.controller;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.controller
 * FileName       : TransactionController
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오후 2:52  paesir      최초 생성
 */

import com.nexterp.accounting.dto.TransactionDTO;
import com.nexterp.accounting.service.TransactionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
  private final TransactionService transactionService;

  public TransactionController(TransactionService transactionService) {
    this.transactionService = transactionService;
  }

  // 거래 목록 조회 (페이지네이션 지원)
  @GetMapping
  public Page<TransactionDTO> getAllTransactions(
      @RequestParam(defaultValue = "0") int page,    // 기본 페이지 번호
      @RequestParam(defaultValue = "10") int size    // 기본 페이지 크기
  ) {
    Pageable pageable = PageRequest.of(page, size);
    return transactionService.getAllTransactions(pageable);
  }

  // 특정 거래 조회
  @GetMapping("/{id}")
  public TransactionDTO getTransactionById(@PathVariable Long id) {
    return transactionService.getTransactionById(id);
  }

  // 거래 생성
  @PostMapping
  public TransactionDTO createTransaction(@RequestBody TransactionDTO transactionDTO) {
    return transactionService.createTransaction(transactionDTO);
  }

  // 거래 수정
  @PutMapping("/{id}")
  public TransactionDTO updateTransaction(
      @PathVariable Long id,
      @RequestBody TransactionDTO transactionDTO
  ) {
    return transactionService.updateTransaction(id, transactionDTO);
  }

  // 거래 삭제
  @DeleteMapping("/{id}")
  public void deleteTransaction(@PathVariable Long id) {
    transactionService.deleteTransaction(id);
  }

}
