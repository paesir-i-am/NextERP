package com.nexterp.accounting.service;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.service
 * FileName       : AccountService
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오후 5:14  paesir      최초 생성
 */


import java.math.BigDecimal;

public interface AccountService {
  void updateBalance(String accountCode, BigDecimal amount);
}
