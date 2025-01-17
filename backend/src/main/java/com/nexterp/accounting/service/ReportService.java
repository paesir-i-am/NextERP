package com.nexterp.accounting.service;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.service
 * FileName       : ReportService
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오후 4:13  paesir      최초 생성
 */


import com.nexterp.accounting.dto.ReportDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ReportService {
    Page<ReportDTO> getAllReports(Pageable pageable);

    ReportDTO getReportById(Long id);

    ReportDTO createReport(ReportDTO reportDTO, Pageable pageable);

    ReportDTO updateReport(Long id, ReportDTO reportDTO);

    void deleteReport(Long id);
}
