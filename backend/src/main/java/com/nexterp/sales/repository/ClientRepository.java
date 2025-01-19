package com.nexterp.sales.repository;

import com.nexterp.sales.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    // 사업자명을 포함하는 검색
    Page<Client> findByBusinessNameContaining(String businessName, Pageable pageable);

    // 등록 날짜를 기준으로 검색 (기간 지정)
    Page<Client> findByCreatedDateBetween(LocalDateTime startDate, LocalDateTime endDate, Pageable pageable);

    // 담당자 이름으로 검색
    Page<Client> findByEmployee_NameContaining(String employeeName, Pageable pageable);
}