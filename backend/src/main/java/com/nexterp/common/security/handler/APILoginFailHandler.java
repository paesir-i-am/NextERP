package com.nexterp.common.security.handler;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.common.sercurity.handler
 * FileName       : APILoginFailHandler
 * Author         : paesir
 * Date           : 25. 1. 17.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 17.오전 10:49  paesir      최초 생성
 */



import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.google.gson.Gson;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class APILoginFailHandler implements AuthenticationFailureHandler{

  @Override
  public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception)
      throws IOException, ServletException{
    log.info("Login fail...."+ exception);

    Gson gson = new Gson();

    String jsonStr = gson.toJson(Map.of("error", "ERROR_LOGIN"));

    response.setContentType("application/json");

    PrintWriter printWriter = response.getWriter();
    printWriter.println(jsonStr);
    printWriter.close();
  }
}
