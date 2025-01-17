package com.nexterp.member.service;

import com.nexterp.employee.repository.EmployeeRepository;
import com.nexterp.member.dto.MemberResponseDTO;
import com.nexterp.member.entity.Member;
import com.nexterp.employee.entity.Employee;
import com.nexterp.member.repository.MemberRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.Random;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final EmployeeRepository employeeRepository;

    public MemberService(MemberRepository memberRepository, EmployeeRepository employeeRepository) {
        this.memberRepository = memberRepository;
        this.employeeRepository = employeeRepository;
    }

    // 회원 생성
    public MemberResponseDTO createMember(Employee employee) {
        // 이미 존재하는 Member가 있는지 확인
        if (memberRepository.existsById(employee.getId())) {
            throw new IllegalArgumentException("Member with the same Employee ID already exists: " + employee.getId());
        }

        // 새로운 Member 객체 생성
        Member member = new Member();
        member.setEmployee(employee);  // Employee와 연결
        member.setPassword("00000000");  // 기본 비밀번호 설정
        member.setIsInitialPassword(true);

        // Member 객체 저장
        Member savedMember = memberRepository.save(member);

        // MemberResponseDTO로 변환하여 반환
        return convertToResponseDTO(savedMember);
    }

    // 회원 조회
    public MemberResponseDTO getMemberById(Integer id) {
        Member member = memberRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + id));

        return convertToResponseDTO(member);
    }

    // 비밀번호 변경
    public void changePassword(Integer memberId, String newPassword) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + memberId));

        member.setPassword(newPassword);
        member.setIsInitialPassword(false);
        memberRepository.save(member);
    }

    // 비밀번호 초기화
    public void resetPassword(Integer memberId, String senderEmail, String senderPassword) {
        Member member = memberRepository.findById(memberId)
            .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + memberId));

        String newPassword = generateRandomPassword(8);
        member.setPassword(newPassword);
        member.setIsInitialPassword(true);
        memberRepository.save(member);

        sendPasswordResetEmail(member, newPassword, senderEmail, senderPassword);
    }

    // DTO 변환 메서드
    private MemberResponseDTO convertToResponseDTO(Member member) {
        return new MemberResponseDTO(
            member.getId(),
            member.getEmployee().getName(),
            member.getEmployee().getEmail(),
            member.getIsInitialPassword()
        );
    }

    // 랜덤 비밀번호 생성
    private String generateRandomPassword(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }

    // 이메일 전송
    private void sendPasswordResetEmail(Member member, String newPassword, String senderEmail, String senderPassword) {
        try {
            JavaMailSenderImpl mailSender = createMailSender(senderEmail, senderPassword);
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(member.getEmployee().getEmail());
            helper.setSubject("비밀번호 초기화 안내");
            helper.setText(String.format("안녕하세요, %s님. 비밀번호가 초기화되었습니다. 새 비밀번호: %s", member.getEmployee().getName(), newPassword));

            mailSender.send(message);
        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    private JavaMailSenderImpl createMailSender(String username, String password) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        return mailSender;
    }
}

