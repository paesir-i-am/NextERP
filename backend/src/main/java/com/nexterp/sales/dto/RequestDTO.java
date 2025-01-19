package com.nexterp.sales.dto;

import com.nexterp.employee.dto.EmployeeDTO;
import com.nexterp.sales.entity.RequestCategory;
import com.nexterp.sales.entity.RequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder(builderMethodName = "withAll") // 빌더 메서드 이름 지정
@AllArgsConstructor
@NoArgsConstructor
public class RequestDTO {
    private Long id; // 요청 고유 ID
    private RequestCategory category; // 요청 카테고리
    private EmployeeDTO requester; // 요청자 정보
    private EmployeeDTO approver; // 승인자 정보
    private RequestStatus status; // 상태: 승인, 반려, 보류
    private String originalData; // 수정 전 데이터
    private String modifiedData; // 수정 후 데이터
    private String reason; // 반려 사유
    private LocalDateTime lastUpdated; // 최종 승인 일자
}
