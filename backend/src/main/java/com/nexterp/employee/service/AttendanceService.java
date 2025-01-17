package com.nexterp.employee.service;

import com.nexterp.employee.dto.AttendanceDTO;
import com.nexterp.employee.entity.Attendance;
import com.nexterp.employee.entity.AttendanceStatus;
import com.nexterp.employee.entity.Employee;
import com.nexterp.employee.repository.AttendanceRepository;
import com.nexterp.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final EmployeeRepository employeeRepository;

    public AttendanceService(AttendanceRepository attendanceRepository, EmployeeRepository employeeRepository) {
        this.attendanceRepository = attendanceRepository;
        this.employeeRepository = employeeRepository;
    }

    // DTO 변환 메서드
    private AttendanceDTO convertToDTO(Attendance attendance) {
        return new AttendanceDTO(
            attendance.getId(),
            attendance.getEmployee().getId(),
            attendance.getEmployee().getName(),
            attendance.getDate(),
            attendance.getCheckInTime(),
            attendance.getCheckOutTime(),
            attendance.getOvertimeHours(),
            attendance.getStatus().toString()
        );
    }

    // 전체 근태 기록 조회 (DTO 변환)
    public List<AttendanceDTO> getAllAttendances() {
        return attendanceRepository.findAll()
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    // 특정 직원의 근태 기록 조회 (DTO 변환)
    public List<AttendanceDTO> getAttendanceByEmployee(Employee employee) {
        return attendanceRepository.findByEmployee(employee)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    // 특정 날짜의 근태 기록 조회 (DTO 변환)
    public List<AttendanceDTO> getAttendanceByDate(LocalDate date) {
        return attendanceRepository.findByDate(date)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
    }

    // 근태 기록 저장
    public AttendanceDTO saveAttendance(AttendanceDTO dto) {
        // 1. Employee 조회
        Employee employee = employeeRepository.findById(dto.getEmployeeId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid Employee ID: " + dto.getEmployeeId()));

        // 2. Attendance 엔티티 생성
        Attendance attendance = Attendance.builder()
            .employee(employee)
            .date(dto.getDate())
            .checkInTime(dto.getCheckInTime())
            .checkOutTime(dto.getCheckOutTime())
            .status(AttendanceStatus.valueOf(dto.getStatus()))
            .build();

        // 3. 초과 근무 시간 계산
        if (attendance.getCheckOutTime() != null && attendance.getCheckOutTime().isAfter(LocalTime.of(18, 0))) {
            Duration overtime = Duration.between(LocalTime.of(18, 0), attendance.getCheckOutTime());
            attendance.setOvertimeHours(BigDecimal.valueOf(overtime.toMinutes() / 60.0));
        } else {
            attendance.setOvertimeHours(BigDecimal.ZERO);
        }

        // 4. Attendance 저장 후 DTO 변환
        return convertToDTO(attendanceRepository.save(attendance));
    }


    // 근태 기록 삭제
    public void deleteAttendance(Integer id) {
        attendanceRepository.deleteById(id);
    }
}
