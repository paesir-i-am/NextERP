package com.nexterp.accounting.controller;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.controller
 * FileName       : VATController
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오후 5:33  paesir      최초 생성
 */

import com.nexterp.accounting.entity.VAT;
import com.nexterp.accounting.service.VATService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/vat")
public class VATController {
  private final VATService vatService;

  public VATController(VATService vatService) {
    this.vatService = vatService;
  }

  @PostMapping("/create")
  public ResponseEntity<VAT> createVAT(
      @RequestParam Long transactionId,
      @RequestParam BigDecimal vatRate,
      @RequestParam BigDecimal baseAmount
  ) {
    VAT vat = vatService.createVAT(transactionId, vatRate, baseAmount);
    return ResponseEntity.ok(vat);
  }

  @GetMapping("/{transactionId}")
  public ResponseEntity<VAT> getVATByTransactionId(@PathVariable Long transactionId) {
    VAT vat = vatService.getVATByTransactionId(transactionId);
    return ResponseEntity.ok(vat);
  }

  @DeleteMapping("/{vatId}")
  public ResponseEntity<Void> deleteVAT(@PathVariable Long vatId) {
    vatService.deleteVAT(vatId);
    return ResponseEntity.noContent().build();
  }
}
