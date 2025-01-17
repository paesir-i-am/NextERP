package com.nexterp;

import com.nexterp.accounting.dto.JournalEntryDTO;
import com.nexterp.accounting.entity.Account;
import com.nexterp.accounting.entity.AccountType;
import com.nexterp.accounting.entity.JournalEntry;
import com.nexterp.accounting.entity.ReportType;
import com.nexterp.accounting.repository.AccountRepository;
import com.nexterp.accounting.repository.JournalEntryRepository;
import com.nexterp.accounting.repository.ReportRepository;
import com.nexterp.accounting.service.JournalEntryService;
import com.nexterp.accounting.service.ReportFileGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class NextErpApplicationTests {

//  @Autowired
//  private AccountRepository accountRepository;
//
//  @Test
//  public void initializeAccounts() {
//    // 부모 계정 생성
//    Account assetAccount = new Account("100", "자산", AccountType.ASSET, null, "자산 계정");
//    accountRepository.save(assetAccount);
//
//    // 자식 계정 생성
//    accountRepository.saveAll(List.of(
//        new Account("101", "현금", AccountType.ASSET, assetAccount, "현금 계정"),
//        new Account("102", "보통예금", AccountType.ASSET, assetAccount, "보통예금 계정"),
//        new Account("103", "당좌예금", AccountType.ASSET, assetAccount, "당좌예금 계정")
//    ));
//
//    // 데이터 검증
//    List<Account> accounts = accountRepository.findAll();
//    assertThat(accounts).isNotEmpty();
//    assertThat(accounts.size()).isEqualTo(4); // 총 4개 계정 (부모 + 자식)
//  }

  @Autowired
  private ReportFileGenerator reportFileGenerator;

  @Test
  public void createReport() {
    ReportType reportType = ReportType.BALANCE_SHEET; // 예제 ReportType
    List<String[]> content = List.of(
        new String[]{"Account", "Debit", "Credit"},
        new String[]{"Cash", "1000", "0"},
        new String[]{"Revenue", "0", "1000"}
    );

    try {
      String filePath = reportFileGenerator.generateReportFile(reportType, content);
      System.out.println("리포트 생성 완료: " + filePath);
    } catch (IOException e) {
      System.err.println("리포트 생성 중 오류 발생: " + e.getMessage());
    }
  }

  @Autowired
  private AccountRepository accountRepository;
  @Autowired
  private ReportRepository reportRepository;
  @Autowired
  private JournalEntryRepository journalEntryRepository;
  @Autowired
  private JournalEntryService journalEntryService;
  @Test
  public void testCreateJournalEntry() {
    JournalEntryDTO dto = new JournalEntryDTO();
    dto.setDate(LocalDate.now());
    dto.setAccountCode("101");
    dto.setDebit(BigDecimal.valueOf(100));
    dto.setCredit(BigDecimal.ZERO);
    dto.setDescription("Test Entry");

    JournalEntryDTO result = journalEntryService.createJournalEntry(dto);

    assertNotNull(result.getId());
    assertEquals(BigDecimal.valueOf(100), result.getDebit());
    assertEquals(BigDecimal.ZERO, result.getCredit());
    assertEquals(BigDecimal.valueOf(100), result.getAmount()); // amount 검증
  }
}
