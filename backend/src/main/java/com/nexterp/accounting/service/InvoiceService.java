package com.nexterp.accounting.service;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.service
 * FileName       : InvoiceService
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오후 3:31  paesir      최초 생성
 */


import com.nexterp.accounting.dto.InvoiceDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InvoiceService {
Page<InvoiceDTO> getAllInvoices(Pageable pageable);
InvoiceDTO getInvoiceById(Long id);
InvoiceDTO createInvoice(InvoiceDTO invoiceDTO);
InvoiceDTO updateInvoice(Long id, InvoiceDTO invoiceDTO);
void deleteInvoice(Long id);
}
