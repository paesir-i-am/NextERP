package com.nexterp.common.util;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.common.util
 * FileName       : CustomJWTException
 * Author         : paesir
 * Date           : 25. 1. 17.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 17.오후 5:18  paesir      최초 생성
 */


public class CustomJWTException extends RuntimeException {

  public CustomJWTException(String message) {
    super(message);
  }

  public CustomJWTException(String msg, Throwable cause){
    super(msg, cause);
  }

}
