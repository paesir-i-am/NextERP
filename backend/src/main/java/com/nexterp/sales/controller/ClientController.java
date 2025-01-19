package com.nexterp.sales.controller;

import com.nexterp.sales.dto.ClientDTO;
import com.nexterp.sales.dto.PageRequestDTO;
import com.nexterp.sales.dto.PageResponseDTO;
import com.nexterp.sales.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    // 거래처 목록 조회 (페이징 처리 포함)
    @GetMapping
    public ResponseEntity<PageResponseDTO<ClientDTO>> getClients(
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "businessName", required = false) String businessName,
            @RequestParam(value = "registrationDate", required = false) String registrationDate,
            @RequestParam(value = "employeeName", required = false) String employeeName) {

        PageRequestDTO pageRequestDTO = new PageRequestDTO(page, size);
        PageResponseDTO<ClientDTO> clients = clientService.getClients(pageRequestDTO, businessName, registrationDate, employeeName);
        return ResponseEntity.ok(clients);
    }

    // 거래처 상세 조회
    @GetMapping("/{client_id}")
    public ResponseEntity<ClientDTO> getClientDetail(@PathVariable("client_id") Long clientId) {
        ClientDTO client = clientService.getClientDetail(clientId);
        return ResponseEntity.ok(client);
    }

    // 신규 거래처 등록
    @PostMapping
    public ResponseEntity<Void> createClient(@RequestBody ClientDTO clientDTO) {
        clientService.createClient(clientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    // 거래처 수정 요청 (수정 승인 절차) -> 아직 미완성
    @PutMapping("/{client_id}")
    public ResponseEntity<Void> updateClient(@PathVariable("client_id") Long clientId, @RequestBody ClientDTO clientDTO) {
        clientService.updateClient(clientId, clientDTO);  // 수정 요청을 처리하는 서비스 호출
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    // 거래처 삭제
    @DeleteMapping("/{client_id}")
    public ResponseEntity<Void> deleteClient(@PathVariable("client_id") Long clientId) {
        clientService.deleteClient(clientId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
