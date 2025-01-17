package com.nexterp.employee.controller;

import com.nexterp.employee.entity.Attendance;
import com.nexterp.employee.entity.Employee;
import com.nexterp.employee.service.AttendanceService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/attendances")
public class AttendanceController {

    private final AttendanceService attendanceService;

    public AttendanceController(AttendanceService attendanceService) {
        this.attendanceService = attendanceService;
    }

    // 모든 근태 기록 조회
    @GetMapping
    public List<Attendance> getAllAttendances() {
        return attendanceService.getAllAttendances();
    }

    // 특정 직원의 근태 기록 조회
    @GetMapping("/employee/{employeeId}")
    public List<Attendance> getAttendanceByEmployee(@PathVariable Integer employeeId) {
        Employee employee = new Employee();
        employee.setId(employeeId);
        return attendanceService.getAttendanceByEmployee(employee);
    }

    // 특정 날짜의 근태 기록 조회
    @GetMapping("/date/{date}")
    public List<Attendance> getAttendanceByDate(@PathVariable String date) {
        LocalDate localDate = LocalDate.parse(date);
        return attendanceService.getAttendanceByDate(localDate);
    }

    // 근태 기록 생성
    @PostMapping
    public ResponseEntity<Attendance> saveAttendance(@RequestBody Attendance attendance) {
        Attendance savedAttendance = attendanceService.saveAttendance(attendance);
        return ResponseEntity.ok(savedAttendance);
    }

    // 근태 기록 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAttendance(@PathVariable Integer id) {
        attendanceService.deleteAttendance(id);
        return ResponseEntity.ok("Attendance record deleted successfully.");
    }
}
