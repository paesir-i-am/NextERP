package com.nexterp.accounting.controller;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.controller
 * FileName       : InvoiceController
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오후 3:40  paesir      최초 생성
 */

import com.nexterp.accounting.dto.InvoiceDTO;
import com.nexterp.accounting.service.InvoiceService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoices")
public class InvoiceController {
  private final InvoiceService invoiceService;

  public InvoiceController(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @GetMapping
  public Page<InvoiceDTO> getAllInvoices(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size
  ) {
    Pageable pageable = PageRequest.of(page, size);
    return invoiceService.getAllInvoices(pageable);
  }

  @GetMapping("/{id}")
  public InvoiceDTO getInvoiceById(@PathVariable Long id) {
    return invoiceService.getInvoiceById(id);
  }

  @PostMapping
  public InvoiceDTO createInvoice(@RequestBody InvoiceDTO invoiceDTO) {
    return invoiceService.createInvoice(invoiceDTO);
  }

  @PutMapping("/{id}")
  public InvoiceDTO updateInvoice(@PathVariable Long id, @RequestBody InvoiceDTO invoiceDTO) {
    return invoiceService.updateInvoice(id, invoiceDTO);
  }

  @DeleteMapping("/{id}")
  public void deleteInvoice(@PathVariable Long id) {
    invoiceService.deleteInvoice(id);
  }
}
