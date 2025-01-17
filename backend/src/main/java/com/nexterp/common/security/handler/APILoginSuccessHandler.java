package com.nexterp.common.security.handler;

import com.google.gson.Gson;
import com.nexterp.common.util.JWTUtil;
import com.nexterp.member.dto.MemberResponseDTO;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {

  private final JWTUtil jwtUtil;

  public APILoginSuccessHandler(JWTUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
  }

  @Override
  public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
      throws IOException, ServletException {
    log.info("Login successful!");

    // Authentication에서 MemberResponseDTO 추출
    MemberResponseDTO memberResponseDTO = (MemberResponseDTO) authentication.getPrincipal();

    // 사용자 정보를 기반으로 JWT 생성
    Integer memberId = memberResponseDTO.getId(); // 사원번호
    Map<String, Object> additionalClaims = new HashMap<>();
    additionalClaims.put("employeeName", memberResponseDTO.getEmployeeName());
    additionalClaims.put("employeeEmail", memberResponseDTO.getEmployeeEmail());

    String accessToken = jwtUtil.generateToken(additionalClaims, 60); // 60분 유효
    String refreshToken = jwtUtil.generateToken(additionalClaims, 1440); // 24시간 유효

    // 응답 데이터 생성
    Map<String, Object> responseMap = new HashMap<>();
    responseMap.put("accessToken", accessToken);
    responseMap.put("refreshToken", refreshToken);
    responseMap.put("user", memberResponseDTO);

    // JSON 응답
    Gson gson = new Gson();
    String jsonResponse = gson.toJson(responseMap);

    response.setContentType("application/json; charset=UTF-8");
    PrintWriter writer = response.getWriter();
    writer.println(jsonResponse);
    writer.close();
  }
}