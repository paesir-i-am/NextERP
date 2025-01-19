package com.nexterp.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SimpleEmployeeDTO {
    private Integer id; // 사원 번호
    private String name; // 이름
}
