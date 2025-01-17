package com.nexterp.accounting.service;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.service
 * FileName       : InvoiceItemService
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오후 4:01  paesir      최초 생성
 */


import com.nexterp.accounting.dto.InvoiceItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface InvoiceItemService {
    Page<InvoiceItemDTO> getInvoiceItems(Pageable pageable);
    InvoiceItemDTO getInvoiceItemById(Long id);
    InvoiceItemDTO createInvoiceItem(InvoiceItemDTO invoiceItemDTO);
    InvoiceItemDTO updateInvoiceItem(Long id, InvoiceItemDTO invoiceItemDTO);
    void deleteInvoiceItem(Long id);
}
