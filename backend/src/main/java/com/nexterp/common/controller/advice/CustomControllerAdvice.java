package com.nexterp.common.controller.advice;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.common.controller.advice
 * FileName       : CustomControllerAdvice
 * Author         : paesir
 * Date           : 25. 1. 17.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 17.오후 6:49  paesir      최초 생성
 */


import com.nexterp.common.util.CustomJWTException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class CustomControllerAdvice {


  @ExceptionHandler(NoSuchElementException.class)
  protected ResponseEntity<?> notExist(NoSuchElementException e) {

    String msg = e.getMessage();

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("msg", msg));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  protected ResponseEntity<?> handleIllegalArgumentException(MethodArgumentNotValidException e) {

    String msg = e.getMessage();

    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(Map.of("msg", msg));
  }

  @ExceptionHandler(CustomJWTException.class)
  protected ResponseEntity<?> handleJWTException(CustomJWTException e) {

    String msg = e.getMessage();

    return ResponseEntity.ok().body(Map.of("error", msg));
  }

  @ExceptionHandler(MissingRequestHeaderException.class)
  public ResponseEntity<String> handleMissingRequestHeader(MissingRequestHeaderException ex) {
    return ResponseEntity.badRequest().body("Missing required header: " + ex.getHeaderName());
  }
}
