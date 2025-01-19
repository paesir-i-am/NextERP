package com.nexterp.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientDTO {
    private Long id; // 거래처 고유 ID
    private String businessName; // 사업자명
    private LocalDateTime createdDate; // 등록 날짜
    private String clientAddress; // 주소
    private String registrationNumber; // 사업자 번호
    private String memo; // 메모
    private SimpleEmployeeDTO employee; // EmployeeDTO 포함 (영업 담당자 정보)
    private String businessCode; // 사업자 코드
    private String clientPhone; // 전화번호
    private String zipCode; // 우편번호
    private String clientEmail; // 이메일 주소
    private String businessType; // 업종
    private String clientBank; // 은행명
    private String accountNumber; // 계좌번호
    private String clientOwner; // 대표자
}
