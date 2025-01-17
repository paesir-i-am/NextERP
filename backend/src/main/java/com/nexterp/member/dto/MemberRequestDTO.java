package com.nexterp.member.dto;

/*
 * Description    :
 * ProjectName    : NextERP
 * PackageName    : com.nexterp.member.dto
 * FileName       : MemberRequestDTO
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
public class MemberRequestDTO {
  private Integer employeeId; // 사원 ID
  private String password; // 비밀번호 (생성/수정 요청 시 사용)
}
