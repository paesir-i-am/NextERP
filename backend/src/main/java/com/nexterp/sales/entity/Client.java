package com.nexterp.sales.entity;

import com.nexterp.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "client")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime createdDate;

    @Column(length = 100, nullable = false)
    private String businessName;

    @Column(length = 50)
    private String businessCode;

    @Column(length = 20)
    private String registrationNumber;

    @Column(length = 100)
    private String clientAddress;

    @Column(length = 15)
    private String clientPhone;

    @ManyToOne // 단방향 매핑
    @JoinColumn(name = "employee_id", nullable = false) // 외래 키 컬럼 이름 지정
    private Employee employee; // 사원 번호

    @Column(length = 10)
    private String zipCode;

    @Column(length = 50)
    private String clientEmail;

    @Column(length = 50)
    private String businessType;

    @Column(length = 20)
    private String clientBank;

    @Column(length = 50)
    private String accountNumber;

    @Column(length = 20)
    private String clientOwner;

    private String memo;
}
