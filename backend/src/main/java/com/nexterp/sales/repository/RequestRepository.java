package com.nexterp.sales.repository;

import com.nexterp.sales.entity.Request;
import com.nexterp.sales.entity.RequestCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RequestRepository extends JpaRepository<Request, Long> {

    // 카테고리별 요청 필터링
    Page<Request> findByCategory(RequestCategory category, Pageable pageable);

    // 요청자 이름으로 검색
    Page<Request> findByRequesterNameContaining(String requesterName, Pageable pageable);

    // 승인자 이름으로 검색
    Page<Request> findByApproverNameContaining(String approverName, Pageable pageable);
}
