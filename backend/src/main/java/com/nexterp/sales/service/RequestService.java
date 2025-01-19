package com.nexterp.sales.service;

import com.nexterp.sales.entity.Request;
import com.nexterp.sales.entity.RequestCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RequestService {

    // 요청 목록 조회 (요청자, 승인자명으로 검색, 카테고리별로 필터링, 페이징 처리)
    Page<Request> getRequests(String name, Pageable pageable, boolean isRequester);

    // 수정 승인 요청 상태(Enum 값)으로 정렬된 요청 목록 조회
    Page<Request> getRequestsSortedByStatus(Pageable pageable, RequestCategory category);
}
