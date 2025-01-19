package com.nexterp.SalesService;

import com.nexterp.employee.entity.Employee;
import com.nexterp.employee.repository.EmployeeRepository;
import com.nexterp.sales.dto.ClientDTO;
import com.nexterp.sales.dto.PageRequestDTO;
import com.nexterp.sales.dto.PageResponseDTO;
import com.nexterp.sales.dto.SimpleEmployeeDTO;
import com.nexterp.sales.entity.Client;
import com.nexterp.sales.repository.ClientRepository;
import com.nexterp.sales.service.ClientService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@Log4j2
public class ClientServiceTests {
    @Autowired
    private ClientService clientService;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private EmployeeRepository employeeRepository;

    // 거래처 신규 생성
    @Test
    public void testCreateClientWithExistingEmployee() {

        // Given: 실제 DB에 저장된 Employee 사용
        Employee existingEmployee = employeeRepository.findById(12341234)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found in database"));

        ClientDTO clientDTO = new ClientDTO();
        clientDTO.setBusinessName("Test Business15");
        clientDTO.setClientAddress("Test Address15");
        clientDTO.setRegistrationNumber("123-45-904248");
        clientDTO.setMemo("Test Memo15");
        clientDTO.setEmployee(new SimpleEmployeeDTO(existingEmployee.getId(), existingEmployee.getName()));

        // When
        clientService.createClient(clientDTO);

        // Then
        // 단순히 DB에서 데이터 개수 출력
        List<Client> clients = clientRepository.findAll();
    }

    // 거래처명으로 검색
    @Test
    public void testFindClientByBusinessName() {
        // Given: 검색 조건 (추가된 거래처명을 기준으로 검색)
        String businessName = "Test Business2";

        // When: 실제 DB에서 거래처명 검색
        List<Client> results = clientRepository.findByBusinessNameContaining(businessName, Pageable.unpaged()).getContent();

        // Then: 검색 결과 검증
        Assertions.assertFalse(results.isEmpty(), "검색 결과가 비어있으면 안 됩니다.");
        Assertions.assertEquals(1, results.size(), "검색 결과가 예상과 다릅니다.");
        Assertions.assertEquals(businessName, results.get(0).getBusinessName(), "검색된 거래처명이 다릅니다.");

        // 결과 출력
        results.forEach(client ->
                log.info("검색된 거래처명: {}, 등록일: {}", client.getBusinessName(), client.getCreatedDate())
        );
    }

    @Test
    public void testFindClientByEmployeeName() {
        // Given: 검색 조건 (담당자 이름 '관리자'로 검색)
        String employeeName = "관리자";

        // When: 실제 DB에서 담당자 이름으로 거래처 검색
        List<Client> results = clientRepository.findByEmployee_NameContaining(employeeName, Pageable.unpaged()).getContent();

        // Then: 검색 결과 검증
        Assertions.assertFalse(results.isEmpty(), "검색 결과가 비어있으면 안 됩니다.");

        // 검색된 거래처 중 '관리자'라는 이름을 가진 담당자만 있는지 확인
        results.forEach(client -> {
            Assertions.assertTrue(client.getEmployee().getName().contains(employeeName), "담당자 이름이 다릅니다.");
            log.info("검색된 거래처명: {}, 담당자 이름: {}, 전화번호: {}", client.getBusinessName(), client.getEmployee().getName(), client.getClientPhone());
        });

        // 결과 크기 검증 (여러 결과가 있을 수 있음)
        log.info("검색된 거래처 수: {}", results.size());
    }

    // 등록 일자로 검색
    @Test
    public void testFindClientByCreatedDate() {
        // Given: 시작일과 종료일 정의
        LocalDateTime startDate = LocalDateTime.of(2025, 1, 19, 0, 0, 0);  // 2025-01-19 00:00:00
        LocalDateTime endDate = LocalDateTime.of(2025, 1, 19, 23, 59, 59); // 2025-01-19 23:59:59

        // When: 날짜 범위로 검색
        Pageable pageable = PageRequest.of(0, 10);
        Page<Client> result = clientRepository.findByCreatedDateBetween(startDate, endDate, pageable);

        // Then: 결과 확인
        Assertions.assertFalse(result.isEmpty(), "검색 결과가 비어 있으면 안 됩니다.");
        result.getContent().forEach(client -> {
            Assertions.assertTrue(client.getCreatedDate().isAfter(startDate.minusSeconds(1)) &&
                            client.getCreatedDate().isBefore(endDate.plusSeconds(1)),
                    "클라이언트의 생성 날짜가 범위 내에 있어야 합니다.");
        });
    }

    // 거래처 삭제
    @Test
    public void testDeleteClientWithExistingData() {
        log.info("Testing Client Deletion with Existing Data...");

        // Given: 실제 DB에 저장된 Client의 ID 사용
        Long clientId = 4L; // 실제 DB에 존재하는 Client ID로 수정하세요
        Optional<Client> existingClient = clientRepository.findById(clientId);
        Assertions.assertTrue(existingClient.isPresent(), "Client should exist in the database before deletion");

        // When
        clientService.deleteClient(clientId);

        // Then
        Optional<Client> deletedClient = clientRepository.findById(clientId);
        Assertions.assertTrue(deletedClient.isEmpty(), "Client should be deleted from the database");
    }

    // 거래처 상세 조회
    @Test
    public void testGetClientDetail() {
        // Given: 실제 DB에 저장된 Client ID 사용
        Long existingClientId = 5L; // 실제 DB에 존재하는 Client의 ID로 수정해야 함
        log.info("Testing Client Detail Retrieval for ID: {}", existingClientId);

        // When: 서비스 단의 getClientDetail 메서드 호출
        ClientDTO clientDetail = clientService.getClientDetail(existingClientId);
        log.info("Retrieved Client Detail: {}", clientDetail);
    }

    @Test
    public void testGetClientsWithPagingFromDB() {
        // Given: 페이지 요청 생성
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
                .page(1) // 첫 번째 페이지
                .size(5) // 한 페이지에 5개의 데이터
                .build();

        // When: 페이징 처리된 결과를 가져옴
        PageResponseDTO<ClientDTO> pageResponse = clientService.getClients(pageRequestDTO, null, null, null);

        // 로그 출력
        log.info("Page DTO List: {}", pageResponse.getDtoList());
        log.info("Page Numbers: {}", pageResponse.getPageNumList());
        log.info("Total Count: {}", pageResponse.getTotalCount());
        log.info("Current Page: {}", pageResponse.getPageRequestDTO().getPage());
        log.info("Has Previous Page: {}", pageResponse.isPrev());
        log.info("Has Next Page: {}", pageResponse.isNext());
    }
}
