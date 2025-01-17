package com.nexterp.accounting.service;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.service
 * FileName       : JournalEntryService
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오후 12:45  paesir      최초 생성
 */


import com.nexterp.accounting.dto.JournalEntryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface JournalEntryService {
  Page<JournalEntryDTO> getAllJournalEntries(Pageable pageable);
  Page<JournalEntryDTO> getJournalEntriesByDateRange(LocalDate startDate, LocalDate endDate, Pageable pageable);
  JournalEntryDTO createJournalEntry(JournalEntryDTO journalEntryDTO);
  JournalEntryDTO updateJournalEntry(Long id, JournalEntryDTO journalEntryDTO);
  void deleteJournalEntry(Long id);
}
