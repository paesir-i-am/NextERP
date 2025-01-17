package com.nexterp.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO {
    private Integer id; // 사원 번호
    private String name; // 이름
    private LocalDate birthDate; // 생년월일
    private Boolean gender; // 성별
    private String phone; // 전화번호
    private String email; // 이메일
    private String address; // 주소
    private Integer departmentId; // 부서 ID
    private Integer positionId; // 직위 ID
    private LocalDate hireDate; // 입사일
    private LocalDate terminationDate; // 퇴사일
}
