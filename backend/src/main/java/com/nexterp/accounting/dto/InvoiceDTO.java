package com.nexterp.accounting.dto;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.dto
 * FileName       : InvoiceDTO
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오후 3:31  paesir      최초 생성
 */


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTO {
  private Long id;
  private String invoiceNumber;
  private LocalDateTime date;
  private String buyer;
  private String seller;
  private BigDecimal totalAmount;
  private BigDecimal vatAmount;
  private String description;
  private List<InvoiceItemDTO> items;
}
