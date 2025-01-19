package com.nexterp.sales.service;

import com.nexterp.sales.dto.ClientDTO;
import com.nexterp.sales.dto.PageRequestDTO;
import com.nexterp.sales.dto.PageResponseDTO;


public interface ClientService {

    // 거래처 목록 조회 (페이징 + 검색)
    PageResponseDTO<ClientDTO> getClients
    (PageRequestDTO pageRequestDTO, String businessName, String registrationDate, String employeeName);

    // 거래처 상세 조회
    ClientDTO getClientDetail(Long id);

    // 거래처 신규 등록
    void createClient(ClientDTO clientDTO);

    // 거래처 수정
    void updateClient(Long id, ClientDTO clientDTO);

    // 거래처 삭제
    void deleteClient(Long id);
}
