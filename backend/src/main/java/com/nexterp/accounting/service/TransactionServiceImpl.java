package com.nexterp.accounting.service;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.service
 * FileName       : TransactionServiceImpl
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오후 2:47  paesir      최초 생성
 */

import com.nexterp.accounting.dto.TransactionDTO;
import com.nexterp.accounting.entity.Transaction;
import com.nexterp.accounting.repository.TransactionRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class TransactionServiceImpl implements TransactionService {
private final TransactionRepository transactionRepository;

public TransactionServiceImpl(TransactionRepository transactionRepository) {
  this.transactionRepository = transactionRepository;
}

  @Override
  public Page<TransactionDTO> getAllTransactions(Pageable pageable) {
    return transactionRepository.findAll(pageable)
        .map(this::entityToDTO);
  }

  @Override
  public TransactionDTO getTransactionById(Long id) {
    return transactionRepository.findById(id)
        .map(this::entityToDTO)
        .orElseThrow(() -> new IllegalArgumentException("Transaction not found: " + id));
  }

  @Override
  public TransactionDTO createTransaction(TransactionDTO transactionDTO) {
    Transaction transaction = dtoToEntity(transactionDTO);
    Transaction savedTransaction = transactionRepository.save(transaction);
    return entityToDTO(savedTransaction);
  }

  @Override
  public TransactionDTO updateTransaction(Long id, TransactionDTO transactionDTO) {
    Transaction transaction = transactionRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Transaction not found: " + id));

    transaction.setDate(transactionDTO.getDate());
    transaction.setAmount(transactionDTO.getAmount());
    transaction.setType(transactionDTO.getType());
    transaction.setDescription(transactionDTO.getDescription());

    Transaction updatedTransaction = transactionRepository.save(transaction);
    return entityToDTO(updatedTransaction);
  }

  @Override
  public void deleteTransaction(Long id) {
    if (!transactionRepository.existsById(id)) {
      throw new IllegalArgumentException("Transaction not found: " + id);
    }
    transactionRepository.deleteById(id);
  }

  private TransactionDTO entityToDTO(Transaction transaction) {
    TransactionDTO dto = new TransactionDTO();
    dto.setId(transaction.getId());
    dto.setDate(transaction.getDate());
    dto.setAmount(transaction.getAmount());
    dto.setType(transaction.getType());
    dto.setDescription(transaction.getDescription());
    return dto;
  }

  private Transaction dtoToEntity(TransactionDTO dto) {
    Transaction transaction = new Transaction();
    transaction.setDate(dto.getDate());
    transaction.setAmount(dto.getAmount());
    transaction.setType(dto.getType());
    transaction.setDescription(dto.getDescription());
    return transaction;
  }
}
