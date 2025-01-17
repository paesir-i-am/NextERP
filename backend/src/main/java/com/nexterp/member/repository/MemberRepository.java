package com.nexterp.member.repository;

import com.nexterp.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
    // 필요한 경우 사용자 정의 메서드 추가
}