package com.nexterp.accounting.controller;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.controller
 * FileName       : ReportController
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오후 4:15  paesir      최초 생성
 */

import com.nexterp.accounting.dto.ReportDTO;
import com.nexterp.accounting.service.ReportService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reports")
public class ReportController {
  private final ReportService reportService;

  public ReportController(ReportService reportService) {
    this.reportService = reportService;
  }

  @GetMapping
  public Page<ReportDTO> getAllReports(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size
  ) {
    Pageable pageable = PageRequest.of(page, size);
    return reportService.getAllReports(pageable);
  }

  @GetMapping("/{id}")
  public ReportDTO getReportById(@PathVariable Long id) {
    return reportService.getReportById(id);
  }

  @PostMapping
  public ReportDTO createReport(
      @RequestBody ReportDTO reportDTO,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size
  ) {
    Pageable pageable = PageRequest.of(page, size);
    return reportService.createReport(reportDTO, pageable); // Pageable 추가
  }

  @PutMapping("/{id}")
  public ReportDTO updateReport(
      @PathVariable Long id,
      @RequestBody ReportDTO reportDTO
  ) {
    return reportService.updateReport(id, reportDTO);
  }

  @DeleteMapping("/{id}")
  public void deleteReport(@PathVariable Long id) {
    reportService.deleteReport(id);
  }
}
