package com.nexterp.member.repository;

import com.nexterp.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    // employee_id로 Member를 찾는 쿼리 메서드
    Member findByEmployeeId(Integer employeeId);
}