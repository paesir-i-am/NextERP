package com.nexterp.common.config;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.common.config
 * FileName       : CustomServletConfig
 * Author         : paesir
 * Date           : 25. 1. 17.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 17.오후 6:50  paesir      최초 생성
 */


import com.nexterp.common.controller.LocalDateFormatter;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomServletConfig implements WebMvcConfigurer {

  @Override
  public void addFormatters(FormatterRegistry registry) {
    registry.addFormatter(new LocalDateFormatter());
  }
}
