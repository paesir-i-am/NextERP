package com.nexterp.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentDTO {
    private Integer id;          // 부서 ID
    private String name;         // 부서명
    private String contactEmail; // 부서 연락처 이메일
}
