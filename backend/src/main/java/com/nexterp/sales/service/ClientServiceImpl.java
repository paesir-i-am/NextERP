package com.nexterp.sales.service;

import com.nexterp.sales.dto.ClientDTO;
import com.nexterp.sales.dto.PageRequestDTO;
import com.nexterp.sales.dto.PageResponseDTO;
import com.nexterp.sales.entity.Client;
import com.nexterp.sales.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    @Override
    public PageResponseDTO<ClientDTO> getClients(PageRequestDTO pageRequestDTO, String businessName, String registrationDate, String employeeName) {
        Pageable pageable = PageRequest.of(pageRequestDTO.getPage() - 1, pageRequestDTO.getSize());

        // 리포지토리 호출: 단일 조건에 따라 적절한 메서드 사용
        Page<Client> result = Optional.ofNullable(businessName)
                .map(name -> clientRepository.findByBusinessNameContaining(name, pageable))
                .or(() -> Optional.ofNullable(registrationDate)
                        .map(date -> {
                            String[] dates = date.split(" to "); // "YYYY-MM-DD to YYYY-MM-DD" 형식 가정
                            LocalDateTime startDate = LocalDate.parse(dates[0].trim()).atStartOfDay(); // 시작 날짜를 LocalDateTime으로 변환
                            LocalDateTime endDate = LocalDate.parse(dates[1].trim()).atTime(23, 59, 59); // 종료 날짜를 LocalDateTime으로 변환
                            return clientRepository.findByCreatedDateBetween(startDate, endDate, pageable);
                        }))
                .or(() -> Optional.ofNullable(employeeName)
                        .map(name -> clientRepository.findByEmployee_NameContaining(name, pageable)))
                .orElseGet(() -> clientRepository.findAll(pageable)); // 조건이 없을 경우 전체 조회

        // 엔티티 -> DTO 변환
        List<ClientDTO> dtoList = result.getContent().stream()
                .map(client -> modelMapper.map(client, ClientDTO.class)) // 바로 변환해서 반환
                .collect(Collectors.toList());

        return new PageResponseDTO<>(dtoList, pageRequestDTO, result.getTotalElements());
    }

    @Override
    public ClientDTO getClientDetail(Long id) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("거래처를 찾을 수 없습니다."));

        // ModelMapper를 사용하여 변환
        return modelMapper.map(client, ClientDTO.class);
    }

    @Override
    public void createClient(ClientDTO clientDTO) {
        // DTO -> 엔티티 변환
        Client client = modelMapper.map(clientDTO, Client.class);
        client.setCreatedDate(LocalDateTime.now());
        clientRepository.save(client);
    }

    @Override
    public void updateClient(Long id, ClientDTO clientDTO) {
        Client client = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("거래처를 찾을 수 없습니다."));

        // 수정 내용을 즉시 반영하지 않고 로그나 보류 상태로 처리
        logPendingUpdate(client, clientDTO);
        // 주석 처리: clientRepository.save(client); - 데이터는 수정되지 않음.
    }

    @Override
    public void deleteClient(Long id) {
        clientRepository.deleteById(id);
    }

    // 수정 요청 내용을 로그로 저장 (추후 Request 테이블에 저장 가능)
    private void logPendingUpdate(Client original, ClientDTO updated) {
        System.out.println("Pending Update:");
        System.out.println("Original: " + original);
        System.out.println("Updated: " + updated);
        // 추후 Request 엔티티로 저장하도록 확장 가능
    }
}
