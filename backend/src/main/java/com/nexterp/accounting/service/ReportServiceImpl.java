package com.nexterp.accounting.service;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.service
 * FileName       : ReportServiceImpl
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오후 4:14  paesir      최초 생성
 */

import com.nexterp.accounting.dto.ReportDTO;
import com.nexterp.accounting.entity.Account;
import com.nexterp.accounting.entity.AccountType;
import com.nexterp.accounting.entity.JournalEntry;
import com.nexterp.accounting.entity.Report;
import com.nexterp.accounting.repository.AccountRepository;
import com.nexterp.accounting.repository.JournalEntryRepository;
import com.nexterp.accounting.repository.ReportRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {
  private final ReportRepository reportRepository;
  private final ReportFileGenerator reportFileGenerator;
  private final AccountRepository accountRepository;
  private final JournalEntryRepository journalEntryRepository;

  public ReportServiceImpl(ReportRepository reportRepository,
                           ReportFileGenerator reportFileGenerator,
                           AccountRepository accountRepository,
                           JournalEntryRepository journalEntryRepository) {
    this.reportRepository = reportRepository;
    this.reportFileGenerator = reportFileGenerator;
    this.accountRepository = accountRepository;
    this.journalEntryRepository = journalEntryRepository;
  }

  @Override
  public Page<ReportDTO> getAllReports(Pageable pageable) {
    return reportRepository.findAll(pageable).map(this::entityToDTO);
  }

  @Override
  public ReportDTO getReportById(Long id) {
    return reportRepository.findById(id)
        .map(this::entityToDTO)
        .orElseThrow(() -> new IllegalArgumentException("Report not found: " + id));
  }

  @Override
  public ReportDTO createReport(ReportDTO reportDTO, Pageable pageable) {
    // 1. DTO를 Entity로 변환
    Report report = dtoToEntity(reportDTO);

    // 2. 데이터베이스 또는 다른 소스에서 데이터 조회
    List<String[]> csvData = fetchReportData(report, pageable);

    // 3. CSV 파일 생성
    try {
      // CSV 파일 생성 및 경로 반환
      String filePath = reportFileGenerator.generateReportFile(report.getType(), csvData);
      report.setFileUrl(filePath); // 생성된 파일 경로를 Report 엔터티에 저장
    } catch (IOException e) {
      throw new RuntimeException("Failed to generate CSV report file", e);
    }

    // 4. 데이터베이스에 저장
    Report savedReport = reportRepository.save(report);

    // 5. Entity를 DTO로 변환하여 반환
    return entityToDTO(savedReport);
  }

  @Override
  public ReportDTO updateReport(Long id, ReportDTO reportDTO) {
    Report existingReport = reportRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("Report not found: " + id));

    existingReport.setType(reportDTO.getType());
    existingReport.setPeriodStart(reportDTO.getPeriodStart());
    existingReport.setPeriodEnd(reportDTO.getPeriodEnd());
    existingReport.setFileUrl(reportDTO.getFileUrl());
    existingReport.setUpdatedAt(LocalDateTime.now());

    Report updatedReport = reportRepository.save(existingReport);
    return entityToDTO(updatedReport);
  }

  @Override
  public void deleteReport(Long id) {
    if (!reportRepository.existsById(id)) {
      throw new IllegalArgumentException("Report not found: " + id);
    }
    reportRepository.deleteById(id);
  }

  private Report dtoToEntity(ReportDTO dto) {
    Report entity = new Report();
    entity.setType(dto.getType());
    entity.setPeriodStart(dto.getPeriodStart());
    entity.setPeriodEnd(dto.getPeriodEnd());
    entity.setFileUrl(dto.getFileUrl());
    return entity;
  }

  private ReportDTO entityToDTO(Report entity) {
    ReportDTO dto = new ReportDTO();
    dto.setId(entity.getId());
    dto.setType(entity.getType());
    dto.setPeriodStart(entity.getPeriodStart());
    dto.setPeriodEnd(entity.getPeriodEnd());
    dto.setFileUrl(entity.getFileUrl());
    dto.setCreatedAt(entity.getCreatedAt());
    dto.setUpdatedAt(entity.getUpdatedAt());
    return dto;
  }


  //데이터 조회 메서드

  /**
   * 보고서 데이터를 가져오는 메서드
   */
  private List<String[]> fetchReportData(Report report, Pageable pageable) {
    switch (report.getType()) {
      case BALANCE_SHEET:
        return fetchBalanceSheetData(report, pageable);
      case INCOME_STATEMENT:
        return fetchIncomeStatementData(report, pageable);
      case CASH_FLOW:
        return fetchCashFlowData(report, pageable);
      default:
        throw new IllegalArgumentException("Unsupported report type: " + report.getType());
    }
  }


  private List<String[]> fetchBalanceSheetData(Report report, Pageable pageable) {
    Page<Account> accountsPage = accountRepository.findAll(pageable); // Page로 조회
    List<String[]> csvData = new ArrayList<>();

    // CSV 헤더 추가
    csvData.add(new String[]{"Account", "Debit", "Credit"});

    // 데이터 추가
    accountsPage.forEach(account -> {
      BigDecimal totalDebit = journalEntryRepository.getTotalAmountByAccountCode(account.getCode())
          .max(BigDecimal.ZERO);

      BigDecimal totalCredit = journalEntryRepository.getTotalAmountByAccountCode(account.getCode())
          .min(BigDecimal.ZERO).abs();

      csvData.add(new String[]{
          account.getName(),
          totalDebit.toString(),
          totalCredit.toString()
      });
    });

    return csvData;
  }

  private List<String[]> fetchIncomeStatementData(Report report, Pageable pageable) {
    LocalDate startDate = report.getPeriodStart().toLocalDate();
    LocalDate endDate = report.getPeriodEnd().toLocalDate();

    Page<JournalEntry> entriesPage = journalEntryRepository.findByDateBetween(
        startDate,
        endDate,
        pageable
    );

    List<String[]> csvData = new ArrayList<>();

    // CSV 헤더 추가
    csvData.add(new String[]{"Account", "Amount"});

    // 데이터 추가
    entriesPage.forEach(entry -> {
      csvData.add(new String[]{entry.getAccount().getName(), entry.getAmount().toString()});
    });

    return csvData;
  }

  private List<String[]> fetchCashFlowData(Report report, Pageable pageable) {
    LocalDate startDate = report.getPeriodStart().toLocalDate();
    LocalDate endDate = report.getPeriodEnd().toLocalDate();

    Page<JournalEntry> entriesPage = journalEntryRepository.findByDateBetween(
        startDate,
        endDate,
        pageable
    );

    List<String[]> csvData = new ArrayList<>();

    // CSV 헤더 추가
    csvData.add(new String[]{"Date", "Description", "Cash Inflow", "Cash Outflow"});

    // 데이터 추가
    entriesPage.forEach(entry -> {
      String cashInflow = entry.getAmount().compareTo(BigDecimal.ZERO) > 0
          ? entry.getAmount().toString()
          : "0";

      String cashOutflow = entry.getAmount().compareTo(BigDecimal.ZERO) < 0
          ? entry.getAmount().abs().toString()
          : "0";

      csvData.add(new String[]{
          entry.getDate().toString(),
          entry.getDescription(),
          cashInflow,
          cashOutflow
      });
    });

    return csvData;
  }
}
