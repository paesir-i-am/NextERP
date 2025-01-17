package com.nexterp.common.util;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.common.util
 * FileName       : JWTUtil
 * Author         : paesir
 * Date           : 25. 1. 17.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 17.오후 4:53  paesir      최초 생성
 */

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Date;

@Log4j2
@Component
public class JWTUtil {

  private final SecretKey secretKey;

  // 생성자를 통해 secret-key 주입
  public JWTUtil(@Value("${jwt.secret-key}") String key) {
    this.secretKey = Keys.hmacShaKeyFor(key.getBytes());
  }

  /**
   * JWT 토큰 생성
   * @param valueMap 클레임 값
   * @param min 만료 시간 (분)
   * @return 생성된 JWT 문자열
   */
  public String generateToken(Map<String, Object> valueMap, int min) {
    try {
      return Jwts.builder()
          .setHeader(Map.of("typ", "JWT"))
          .setClaims(valueMap)
          .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
          .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
          .signWith(secretKey)
          .compact();
    } catch (Exception e) {
      log.error("Error generating JWT token: {}", e.getMessage());
      throw new RuntimeException("Error generating token", e);
    }
  }

  /**
   * JWT 토큰 검증 및 클레임 추출
   * @param token JWT 토큰
   * @return 클레임 맵
   */
  public Map<String, Object> validateToken(String token) {
    try {
      return Jwts.parserBuilder()
          .setSigningKey(secretKey)
          .build()
          .parseClaimsJws(token) // 파싱 및 검증
          .getBody();
    } catch (MalformedJwtException malformedJwtException) {
      throw new CustomJWTException("MalFormed", malformedJwtException);
    } catch (ExpiredJwtException expiredJwtException) {
      throw new CustomJWTException("Expired", expiredJwtException);
    } catch (InvalidClaimException invalidClaimException) {
      throw new CustomJWTException("Invalid", invalidClaimException);
    } catch (io.jsonwebtoken.JwtException jwtException) {
      throw new CustomJWTException("JWTError", jwtException);
    } catch (Exception e) {
      throw new CustomJWTException("Error", e);
    }
  }

  /**
   * 토큰에서 사원번호 추출
   * @param token JWT 토큰
   * @return 사원번호
   */
  public Integer extractMemberId(String token) {
    return Integer.valueOf(validateToken(token).get("memberId").toString());
  }

  /**
   * 토큰에서 추가 클레임 추출
   * @param token JWT 토큰
   * @param claimKey 클레임 키
   * @return 클레임 값
   */
  public Object extractClaim(String token, String claimKey) {
    return validateToken(token).get(claimKey);
  }
}

