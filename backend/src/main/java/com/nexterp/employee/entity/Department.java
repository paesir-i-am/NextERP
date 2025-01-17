package com.nexterp.employee.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "department_id")
    private Integer departmentId; // 부서 ID (Primary Key)

    @Column(name = "name", nullable = false, length = 100)
    private String name; // 부서명

    @Column(name = "contact_email", nullable = false)
    private String contactEmail; // 부서 연락처 이메일

}

