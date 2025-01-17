package com.nexterp.accounting.dto;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.dto
 * FileName       : ReportDTO
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오후 4:12  paesir      최초 생성
 */

import com.nexterp.accounting.entity.ReportType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
  private Long id;
  private ReportType type;
  private LocalDateTime periodStart;
  private LocalDateTime periodEnd;
  private String fileUrl;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

}
