package com.nexterp.common.controller;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.common.controller
 * FileName       : LocalDateFormatter
 * Author         : paesir
 * Date           : 25. 1. 17.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 17.오후 6:51  paesir      최초 생성
 */


import org.springframework.format.Formatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateFormatter implements Formatter<LocalDate> {
  @Override
  public LocalDate parse(String text, Locale locale) {
    return LocalDate.parse(text, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
  }

  @Override
  public String print(LocalDate object, Locale locale) {
    return DateTimeFormatter.ofPattern("yyyy-MM-dd").format(object);
  }
}