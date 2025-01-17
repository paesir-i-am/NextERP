package com.nexterp.common.security.filter;

import com.google.gson.Gson;
import com.nexterp.common.util.JWTUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {

  private final JWTUtil jwtUtil;
  private final UserDetailsService userDetailsService;

  public JWTCheckFilter(JWTUtil jwtUtil, UserDetailsService userDetailsService) {
    this.jwtUtil = jwtUtil;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    log.info("Executing JWTCheckFilter...");

    // 요청 헤더에서 Authorization 추출
    String authHeader = request.getHeader("Authorization");

    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
      log.warn("Missing or invalid Authorization header");
      respondWithUnauthorized(response, "MISSING_AUTHORIZATION_HEADER");
      return;
    }

    // Bearer 토큰 추출
    String token = authHeader.substring(7);

    try {
      // JWT 유효성 검증 및 클레임 추출
      Map<String, Object> claims = jwtUtil.validateToken(token);
      log.info("JWT Claims: {}", claims);

      // 클레임에서 사용자 정보 추출
      Integer memberId = Integer.valueOf(claims.get("memberId").toString());

      // 사용자 정보 로드
      UserDetails userDetails = userDetailsService.loadUserByUsername(String.valueOf(memberId));

      // 인증 객체 생성 및 SecurityContext 설정
      UsernamePasswordAuthenticationToken authentication =
          new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
      SecurityContextHolder.getContext().setAuthentication(authentication);

      log.info("Authenticated user: {}", memberId);

      // 다음 필터로 요청 전달
      filterChain.doFilter(request, response);

    } catch (Exception e) {
      log.error("JWT Validation Error: {}", e.getMessage());
      respondWithUnauthorized(response, "INVALID_OR_EXPIRED_TOKEN");
    }
  }

  @Override
  protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
    if (request.getMethod().equalsIgnoreCase("OPTIONS")) {
      return true; // Preflight 요청 제외
    }

    String path = request.getRequestURI();
    log.info("Checking URI for filtering: {}", path);

    // 필터를 적용하지 않을 경로 지정
    List<String> excludedPaths = List.of("/api/members/login");

    return excludedPaths.stream().anyMatch(path::startsWith);
  }

  private void respondWithUnauthorized(HttpServletResponse response, String errorMessage) throws IOException {
    Gson gson = new Gson();
    String errorResponse = gson.toJson(Map.of("error", errorMessage));
    response.setContentType("application/json");
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    try (PrintWriter writer = response.getWriter()) {
      writer.write(errorResponse);
    }
  }
}