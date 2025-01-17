package com.nexterp.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PositionDTO {
    private Integer positionId; // 직위 ID
    private String title; // 직위명
    private String role; // 역할
}
