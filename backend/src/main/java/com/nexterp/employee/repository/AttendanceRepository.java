package com.nexterp.employee.repository;


import com.nexterp.employee.entity.Attendance;
import com.nexterp.employee.entity.AttendanceStatus;
import com.nexterp.employee.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AttendanceRepository extends JpaRepository<Attendance, Integer> {
    // 특정 직원의 근태 기록 조회
    List<Attendance> findByEmployee(Employee employee);

    // 특정 날짜의 전체 근태 기록 조회
    List<Attendance> findByDate(LocalDate date);

    // 특정 상태를 가진 근태 기록 조회
    List<Attendance> findByStatus(AttendanceStatus status);
}
