package com.nexterp.accounting.service;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.service
 * FileName       : AccountServiceImpl
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오후 5:15  paesir      최초 생성
 */

import com.nexterp.accounting.entity.Account;
import com.nexterp.accounting.repository.AccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

  private final AccountRepository accountRepository;

  public AccountServiceImpl(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public void updateBalance(String accountCode, BigDecimal amount) {
    Account account = accountRepository.findById(accountCode)
        .orElseThrow(() -> new IllegalArgumentException("Account not found"));

    account.setBalance(account.getBalance().add(amount));
    accountRepository.save(account);
  }
}
