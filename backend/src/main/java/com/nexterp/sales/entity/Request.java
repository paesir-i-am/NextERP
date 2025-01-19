package com.nexterp.sales.entity;

import com.nexterp.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "request")
@Getter
@Setter
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestCategory category; // 요청 카테고리 (거래처, 주문 등)

    @ManyToOne
    @JoinColumn(name = "requester_id", referencedColumnName = "employee_id", nullable = false)
    private Employee requester; // 요청자 (사원)

    @ManyToOne
    @JoinColumn(name = "approver_id", referencedColumnName = "employee_id")
    private Employee approver; // 승인자 (관리자)

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RequestStatus status; // 상태 (승인, 반려, 보류)

    @Column(nullable = false)
    private String originalData; // 수정 전 데이터 (JSON)

    @Column(nullable = false)
    private String modifiedData; // 수정 후 데이터 (JSON)

    private String reason; // 반려 사유

    private LocalDateTime lastUpdated; // 최종 승인 일자
}
