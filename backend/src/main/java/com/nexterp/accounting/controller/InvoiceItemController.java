package com.nexterp.accounting.controller;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.controller
 * FileName       : InvoiceItemController
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오후 4:05  paesir      최초 생성
 */

import com.nexterp.accounting.dto.InvoiceItemDTO;
import com.nexterp.accounting.service.InvoiceItemService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoice-items")
public class InvoiceItemController {
  private final InvoiceItemService invoiceItemService;

  public InvoiceItemController(InvoiceItemService invoiceItemService) {
    this.invoiceItemService = invoiceItemService;
  }

  @GetMapping
  public Page<InvoiceItemDTO> getInvoiceItems(
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size
  ) {
    Pageable pageable = PageRequest.of(page, size);
    return invoiceItemService.getInvoiceItems(pageable);
  }

  @GetMapping("/{id}")
  public InvoiceItemDTO getInvoiceItemById(@PathVariable Long id) {
    return invoiceItemService.getInvoiceItemById(id);
  }

  @PostMapping
  public InvoiceItemDTO createInvoiceItem(@RequestBody InvoiceItemDTO invoiceItemDTO) {
    return invoiceItemService.createInvoiceItem(invoiceItemDTO);
  }

  @PutMapping("/{id}")
  public InvoiceItemDTO updateInvoiceItem(
      @PathVariable Long id,
      @RequestBody InvoiceItemDTO invoiceItemDTO
  ) {
    return invoiceItemService.updateInvoiceItem(id, invoiceItemDTO);
  }

  @DeleteMapping("/{id}")
  public void deleteInvoiceItem(@PathVariable Long id) {
    invoiceItemService.deleteInvoiceItem(id);
  }
}
