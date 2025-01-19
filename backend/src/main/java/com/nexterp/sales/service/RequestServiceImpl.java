package com.nexterp.sales.service;

import com.nexterp.sales.entity.Request;
import com.nexterp.sales.entity.RequestCategory;
import com.nexterp.sales.repository.RequestRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class RequestServiceImpl implements RequestService {

    private final RequestRepository requestRepository;

    public RequestServiceImpl(RequestRepository requestRepository) {
        this.requestRepository = requestRepository;
    }

    // 요청 목록 조회 (요청자/승인자 이름으로 검색, 페이징 처리)
    @Override
    public Page<Request> getRequests(String name, Pageable pageable, boolean isRequester) {
        // 요청자 이름 또는 승인자 이름으로 검색 후 페이징 처리
        return isRequester
                ? requestRepository.findByRequesterNameContaining(name, pageable)
                : requestRepository.findByApproverNameContaining(name, pageable);
    }

    // 수정 승인 요청 상태 Enum 값으로 정렬하기
    @Override
    public Page<Request> getRequestsSortedByStatus(Pageable pageable, RequestCategory category) {
        Pageable sortedPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                Sort.by(Sort.Order.asc("status")));
        return category != null
                ? requestRepository.findByCategory(category, sortedPageable)
                : requestRepository.findAll(sortedPageable);
    }
}
