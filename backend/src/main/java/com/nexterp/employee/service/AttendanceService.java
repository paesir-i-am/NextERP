package com.nexterp.employee.service;

import com.nexterp.employee.entity.Attendance;
import com.nexterp.employee.entity.Employee;
import com.nexterp.employee.repository.AttendanceRepository;
import com.nexterp.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, EmployeeRepository employeeRepository) {
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }



    // 전체 근태 기록 조회
    public List<Attendance> getAllAttendances() {
        return attendanceRepository.findAll();
    }

    // 특정 직원의 근태 기록 조회
    public List<Attendance> getAttendanceByEmployee(Employee employee) {
        return attendanceRepository.findByEmployee(employee);
    }

    // 특정 날짜의 근태 기록 조회
    public List<Attendance> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByDate(date);
    }

    // 근태 기록 저장
    public Attendance saveAttendance(Attendance attendance) {
        // Employee 엔터티를 데이터베이스에서 조회
        Integer employeeId = attendance.getEmployee().getId();
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new IllegalArgumentException("Invalid employee ID: " + employeeId));

        // 영속화된 Employee를 Attendance에 설정
        attendance.setEmployee(employee);

        // 초과 근무 시간 계산 (18시 이후만 초과 근무로 간주)
        if (attendance.getCheckInTime() != null && attendance.getCheckOutTime() != null) {
            LocalTime checkOutTime = attendance.getCheckOutTime();
            LocalTime overtimeStart = LocalTime.of(18, 0); // 초과 근무 기준 시간 (18시)

            if (checkOutTime.isAfter(overtimeStart)) {
                // 초과 근무 시간 계산
                Duration overtimeDuration = Duration.between(overtimeStart, checkOutTime);
                attendance.setOvertimeHours(BigDecimal.valueOf(overtimeDuration.toMinutes() / 60.0)); // 초과 근무 시간(시간 단위)
            } else {
                attendance.setOvertimeHours(BigDecimal.ZERO); // 초과 근무 없음

            }
        }
        // Attendance 저장
        return attendanceRepository.save(attendance);
    }

    // 근태 기록 삭제
    public void deleteAttendance(Integer id) {
        attendanceRepository.deleteById(id);
    }
}
