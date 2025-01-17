package com.nexterp.employee.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "employee")
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 매개변수를 받는 생성자
public class Employee {

    @Id
    @Column(name = "employee_id", nullable = false, unique = true, length = 8)
    private Integer id; // 사원 번호 (직접 입력, 8자리 제한)

    @Column(name = "name", nullable = false, length = 100)
    private String name; // 이름

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate; // 생년월일

    @Column(name = "gender", nullable = false)
    private Boolean gender; // 성별: 남자(false), 여자(true)

    @Column(name = "phone", length = 20)
    private String phone; // 전화번호

    @Column(name = "email", length = 100)
    private String email; // 이메일

    @Column(name = "address", columnDefinition = "TEXT")
    private String address; // 주소

    @ManyToOne
    @JoinColumn(name = "department_id", referencedColumnName = "department_id")
    private Department department; // 부서 ID (Foreign Key)

    @ManyToOne
    @JoinColumn(name = "position_id", referencedColumnName = "position_id")
    private Position position; // 직위 ID (Foreign Key)

    @Column(name = "hire_date", nullable = false)
    private LocalDate hireDate; // 입사일

    @Column(name = "termination_date")
    private LocalDate terminationDate; // 퇴사일 (null이면 재직 중)
}