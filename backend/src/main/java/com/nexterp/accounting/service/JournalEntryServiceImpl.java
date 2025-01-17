package com.nexterp.accounting.service;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.service
 * FileName       : JournalEntryServiceImpl
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오후 12:47  paesir      최초 생성
 */


import com.nexterp.accounting.dto.JournalEntryDTO;
import com.nexterp.accounting.entity.Account;
import com.nexterp.accounting.entity.JournalEntry;
import com.nexterp.accounting.repository.AccountRepository;
import com.nexterp.accounting.repository.JournalEntryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
@Transactional
public class JournalEntryServiceImpl implements JournalEntryService {

  private final JournalEntryRepository journalEntryRepository;
  private final AccountRepository accountRepository;
  private final AccountService accountService;

  public JournalEntryServiceImpl(JournalEntryRepository journalEntryRepository,
                                 AccountRepository accountRepository,
                                 AccountService accountService) {
    this.journalEntryRepository = journalEntryRepository;
    this.accountRepository = accountRepository;
    this.accountService = accountService;
  }

  @Override
  public Page<JournalEntryDTO> getAllJournalEntries(Pageable pageable) {
    return journalEntryRepository.findAll(pageable).map(this::entityToDto);
  }

  @Override
  public Page<JournalEntryDTO> getJournalEntriesByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable) {
    return journalEntryRepository.findByDateBetween(startDate, endDate, pageable).map(this::entityToDto);
  }

  @Override
  public JournalEntryDTO createJournalEntry(JournalEntryDTO journalEntryDTO) {
    JournalEntry journalEntry = dtoToEntity(journalEntryDTO);

    // amount 설정
    if (journalEntry.getDebit().compareTo(BigDecimal.ZERO) > 0) {
      journalEntry.setAmount(journalEntry.getDebit());
    } else if (journalEntry.getCredit().compareTo(BigDecimal.ZERO) > 0) {
      journalEntry.setAmount(journalEntry.getCredit().negate());
    } else {
      throw new IllegalArgumentException("Both debit and credit cannot be zero.");
    }

    JournalEntry savedJournalEntry = journalEntryRepository.save(journalEntry);

    // 잔액 업데이트 (차변 또는 대변에 따라)
    if (journalEntry.getDebit().compareTo(BigDecimal.ZERO) > 0) {
      accountService.updateBalance(journalEntry.getAccount().getCode(), journalEntry.getDebit());
    } else if (journalEntry.getCredit().compareTo(BigDecimal.ZERO) > 0) {
      accountService.updateBalance(journalEntry.getAccount().getCode(), journalEntry.getCredit().negate());
    }

    // DTO로 변환하며 amount 설정
    JournalEntryDTO resultDto = entityToDto(savedJournalEntry);
    resultDto.setAmount(savedJournalEntry.getAmount());

    return entityToDto(savedJournalEntry);
  }

  @Override
  public JournalEntryDTO updateJournalEntry(Long id, JournalEntryDTO journalEntryDTO) {
    JournalEntry existingEntry = journalEntryRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Journal Entry not found: " + id));

    Account account = accountRepository.findById(journalEntryDTO.getAccountCode())
        .orElseThrow(() -> new IllegalArgumentException("Invalid account code: " + journalEntryDTO.getAccountCode()));

    existingEntry.setDate(journalEntryDTO.getDate());
    existingEntry.setAccount(account);
    existingEntry.setDebit(journalEntryDTO.getDebit());
    existingEntry.setCredit(journalEntryDTO.getCredit());
    existingEntry.setDescription(journalEntryDTO.getDescription());

    JournalEntry updatedEntry = journalEntryRepository.save(existingEntry);
    return entityToDto(updatedEntry);
  }

  @Override
  public void deleteJournalEntry(Long id) {
    JournalEntry entry = journalEntryRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Journal Entry not found: " + id));

    // 잔액 복구 (삭제 시)
    if (entry.getDebit().compareTo(BigDecimal.ZERO) > 0) {
      accountService.updateBalance(entry.getAccount().getCode(), entry.getDebit().negate());
    } else if (entry.getCredit().compareTo(BigDecimal.ZERO) > 0) {
      accountService.updateBalance(entry.getAccount().getCode(), entry.getCredit());
    }

    journalEntryRepository.delete(entry);
  }

  private JournalEntryDTO entityToDto(JournalEntry journalEntry) {
    return new JournalEntryDTO(
        journalEntry.getId(),
        journalEntry.getDate(),
        journalEntry.getAccount().getCode(),
        journalEntry.getAccount().getName(),
        journalEntry.getDebit(),
        journalEntry.getCredit(),
        journalEntry.getDescription(),
        journalEntry.getAmount()
    );
  }

  private JournalEntry dtoToEntity(JournalEntryDTO journalEntryDTO) {
    Account account = accountRepository.findById(journalEntryDTO.getAccountCode())
        .orElseThrow(() -> new IllegalArgumentException("Invalid account code: " + journalEntryDTO.getAccountCode()));

    JournalEntry journalEntry = new JournalEntry();
    journalEntry.setId(journalEntryDTO.getId());
    journalEntry.setDate(journalEntryDTO.getDate());
    journalEntry.setAccount(account);
    journalEntry.setDebit(journalEntryDTO.getDebit());
    journalEntry.setCredit(journalEntryDTO.getCredit());
    journalEntry.setDescription(journalEntryDTO.getDescription());

    // amount 설정
    if (journalEntryDTO.getDebit().compareTo(BigDecimal.ZERO) > 0) {
      journalEntry.setAmount(journalEntryDTO.getDebit());
    } else if (journalEntryDTO.getCredit().compareTo(BigDecimal.ZERO) > 0) {
      journalEntry.setAmount(journalEntryDTO.getCredit().negate());
    } else {
      throw new IllegalArgumentException("Both debit and credit cannot be zero.");
    }
    return journalEntry;
  }
}