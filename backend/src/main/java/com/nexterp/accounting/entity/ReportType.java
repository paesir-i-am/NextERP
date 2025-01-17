package com.nexterp.accounting.entity;

/*
 * Description    : 보고서 타입, 재무상태표,손익계산서,현금흐름표
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.accounting.entity
 * FileName       : ReportType
 * Author         : paesir
 * Date           : 25. 1. 16.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 16.오전 10:53  paesir      최초 생성
 */


public enum ReportType {
  BALANCE_SHEET("BalanceSheet"),
  CASH_FLOW("CashFlow"),
  INCOME_STATEMENT("IncomeStatement");

  private final String fileNamePrefix;

  ReportType(String fileNamePrefix) {
    this.fileNamePrefix = fileNamePrefix;
  }

  public String getFileNamePrefix() {
    return fileNamePrefix;
  }
}
