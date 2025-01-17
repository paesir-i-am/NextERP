package com.nexterp.employee.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@Entity
@Table(name = "attendance")
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id; // 근태 기록 ID (Primary Key)

    @ManyToOne
    @JoinColumn(name = "employee_id", referencedColumnName = "employee_id")
    private Employee employee; // 사원 ID (Foreign Key, Employee 참조)

    @Column(name = "date", nullable = false)
    private LocalDate date; // 근무 날짜

    @Column(name = "check_in_time")
    private LocalTime checkInTime; // 출근 시간

    @Column(name = "check_out_time")
    private LocalTime checkOutTime; // 퇴근 시간

    @Column(name = "overtime_hours", precision = 5, scale = 2)
    private BigDecimal overtimeHours; // 초과 근무 시간 (소수점 2자리)

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private AttendanceStatus status; // 근무 상태 (ENUM)
}
