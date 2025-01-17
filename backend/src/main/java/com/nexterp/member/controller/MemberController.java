package com.nexterp.member.controller;

import com.nexterp.employee.entity.Employee;
import com.nexterp.employee.repository.EmployeeRepository;
import com.nexterp.member.dto.MemberRequestDTO;
import com.nexterp.member.dto.MemberResponseDTO;
import com.nexterp.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;
    private final EmployeeRepository employeeRepository;

    public MemberController(MemberService memberService, EmployeeRepository employeeRepository) {
        this.memberService = memberService;
        this.employeeRepository = employeeRepository;
    }

    @PostMapping
    public ResponseEntity<MemberResponseDTO> createMember(@RequestBody MemberRequestDTO requestDTO) {
        // Employee 조회
        Employee employee = employeeRepository.findById(requestDTO.getEmployeeId())
            .orElseThrow(() -> new IllegalArgumentException("Invalid Employee ID: " + requestDTO.getEmployeeId()));

        // Member 생성
        MemberResponseDTO responseDTO = memberService.createMember(employee);

        return ResponseEntity.ok(responseDTO);
    }


    @GetMapping("/{id}")
    public ResponseEntity<MemberResponseDTO> getMemberById(@PathVariable Integer id) {
        MemberResponseDTO responseDTO = memberService.getMemberById(id);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable Integer id, @RequestBody String newPassword) {
        memberService.changePassword(id, newPassword);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/reset-password")
    public ResponseEntity<Void> resetPassword(
        @PathVariable Integer id,
        @RequestParam String senderEmail,
        @RequestParam String senderPassword) {
        memberService.resetPassword(id, senderEmail, senderPassword);
        return ResponseEntity.ok().build();
    }
}
