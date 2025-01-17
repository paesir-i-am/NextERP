package com.nexterp.member.entity;

import com.nexterp.employee.entity.Employee;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "member")
public class Member {

    @Id
    @Column(name = "employee_id") // 기본 키로 선언
    private Integer id;

    @OneToOne(cascade = CascadeType.PERSIST)
    @MapsId // employeeId를 매핑
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private Employee employee;

    @Column(name = "password", nullable = false, length = 255)
    private String password; // 로그인 비밀번호 (암호화 저장)

    @Column(name = "is_initial_password", nullable = false)
    private Boolean isInitialPassword = true; // 초기 비밀번호 상태

}

