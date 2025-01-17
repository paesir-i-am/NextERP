package com.nexterp.common.security;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.common.security
 * FileName       : CustomUserDetailsService
 * Author         : paesir
 * Date           : 25. 1. 17.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 17.오후 4:36  paesir      최초 생성
 */


import com.nexterp.employee.entity.Position;
import com.nexterp.member.entity.Member;
import com.nexterp.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Log4j2
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

  private final MemberRepository memberRepository;

  @Override
  public UserDetails loadUserByUsername(String employeeId) throws UsernameNotFoundException {
    log.info("Authenticating employeeId: {}", employeeId);

    // Integer 타입의 employeeId를 변환
    Integer id;
    try {
      id = Integer.parseInt(employeeId);
    } catch (NumberFormatException e) {
      throw new UsernameNotFoundException("Invalid employee ID format");
    }

    // Member 정보를 DB에서 조회
    Member member = memberRepository.findById(id)
        .orElseThrow(() -> new UsernameNotFoundException("Member not found with employeeId: " + employeeId));

    log.info("Found member: {}", member);

    Position position = member.getEmployee().getPosition();
    if (position == null) {
      throw new UsernameNotFoundException("Employee not found with employeeId: " + employeeId);
    }

    // UserDetails 반환
    return User.builder()
        .username(employeeId) // employeeId를 username으로 사용
        .password(member.getPassword()) // 암호화된 비밀번호
        .roles(position.getRole()) // 권한 설정
        .build();
  }
}