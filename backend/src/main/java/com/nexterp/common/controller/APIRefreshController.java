package com.nexterp.common.controller;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.common.controller
 * FileName       : APIRefreshController
 * Author         : paesir
 * Date           : 25. 1. 17.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 17.오후 6:25  paesir      최초 생성
 */


import com.nexterp.common.util.CustomJWTException;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import com.nexterp.common.util.JWTUtil;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
public class APIRefreshController {

  private final JWTUtil jwtUtil;

  @PostMapping("/member/refresh")
  public Map<String, Object> refresh(
      @RequestHeader(value = "Refresh-Token", required = false) String refreshToken) {

    if (refreshToken == null) {
      throw new CustomJWTException("NULL_REFRESH");
    }

    // Refresh Token 검증
    Map<String, Object> claims = jwtUtil.validateToken(refreshToken);

    String newAccessToken = jwtUtil.generateToken(claims, 1440);

    String newRefreshToken = checkTime((Integer) claims.get("exp"))
        ? jwtUtil.generateToken(claims, 43200)
        : refreshToken;

    return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);
  }


  //시간이 1시간 미만으로 남았다면
  private boolean checkTime(Integer exp) {

    //JWT exp를 날짜로 변환
    java.util.Date expDate = new java.util.Date( (long)exp * (1000 ));

    //현재 시간과의 차이 계산 - 밀리세컨즈
    long gap   = expDate.getTime() - System.currentTimeMillis();

    //분단위 계산
    long leftMin = gap / (1000 * 60);

    //1시간도 안남았는지..
    return leftMin < 60;
  }

  private boolean checkExpiredToken(String token) {

    try{
      jwtUtil.validateToken(token);
    }catch(CustomJWTException ex) {
      if(ex.getMessage().equals("Expired")){
        return true;
      }
    }
    return false;
  }

}
