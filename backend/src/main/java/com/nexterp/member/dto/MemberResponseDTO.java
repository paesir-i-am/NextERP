package com.nexterp.member.dto;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.member.dto
 * FileName       : MemberResponseDTO
 * Author         : paesir
 * Date           : 25. 1. 17.
 * ===========================================================
 * DATE                  AUTHOR       NOTE
 * -----------------------------------------------------------
 * 25. 1. 17.오후 4:08  paesir      최초 생성
 */


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberResponseDTO {
  private Integer id; // Member ID
  private String employeeName; // 사원의 이름
  private String employeeEmail; // 사원의 이메일
  private Boolean isInitialPassword; // 초기 비밀번호 여부
}
