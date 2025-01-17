package com.nexterp.accounting.service;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.service
 * FileName       : VATService
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오후 5:30  paesir      최초 생성
 */


import com.nexterp.accounting.entity.VAT;

import java.math.BigDecimal;

public interface VATService {
  VAT createVAT(Long transactionId, BigDecimal vatRate, BigDecimal baseAmount);

  VAT getVATByTransactionId(Long transactionId);

  void deleteVAT(Long vatId);
}
