package com.nexterp.sales.util;

import com.nexterp.sales.dto.ClientDTO;
import com.nexterp.sales.entity.Client;

public class JsonFormatter {

    private JsonFormatter() {
        // 유틸리티 클래스이므로 인스턴스화 방지
    }

    // Client -> JSON 문자열
    public static String formatClientToJson(Client client) {
        return String.format(
                "{\"id\":%d,\"businessName\":\"%s\",\"createdDate\":\"%s\",\"clientAddress\":\"%s\",\"registrationNumber\":\"%s\",\"memo\":\"%s\"}",
                client.getId(),
                client.getBusinessName(),
                client.getCreatedDate(),
                client.getClientAddress(),
                client.getRegistrationNumber(),
                client.getMemo()
        );
    }

    // ClientDTO -> JSON 문자열
    public static String formatClientDTOToJson(ClientDTO clientDTO) {
        return String.format(
                "{\"id\":%d,\"businessName\":\"%s\",\"createdDate\":\"%s\",\"clientAddress\":\"%s\",\"registrationNumber\":\"%s\",\"memo\":\"%s\"}",
                clientDTO.getId(),
                clientDTO.getBusinessName(),
                clientDTO.getCreatedDate(),
                clientDTO.getClientAddress(),
                clientDTO.getRegistrationNumber(),
                clientDTO.getMemo()
        );
    }
}
