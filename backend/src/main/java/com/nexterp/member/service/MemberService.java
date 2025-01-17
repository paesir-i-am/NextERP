package com.nexterp.member.service;

import com.nexterp.employee.repository.EmployeeRepository;
import com.nexterp.member.entity.Member;
import com.nexterp.employee.entity.Employee;
import com.nexterp.member.repository.MemberRepository;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Properties;
import java.util.Random;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final EmployeeRepository employeeRepository;
    private final JavaMailSender mailSender;

    public MemberService(MemberRepository memberRepository, EmployeeRepository employeeRepository, JavaMailSender mailSender) {
        this.memberRepository = memberRepository;
        this.employeeRepository = employeeRepository;
        this.mailSender = mailSender;
    }

    // 회원 생성
    public Member createMember(Member member) {
        Integer employeeId = member.getId();
        Employee employee = employeeRepository.findById(employeeId)
            .orElseThrow(() -> new IllegalArgumentException("Invalid Employee ID: " + employeeId));

        member.setEmployee(employee);
        member.setPassword("00000000"); // 초기 비밀번호 설정
        member.setIsInitialPassword(true); // 초기 상태 설정

        return memberRepository.save(member);
    }

    // 회원 조회
    public Member getMemberById(Integer id) {
        return memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + id));
    }

    // 비밀번호 변경
    public void changePassword(Integer memberId, String newPassword) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + memberId));

        member.setPassword(newPassword); // 새 비밀번호 설정
        member.setIsInitialPassword(false); // 초기 상태 해제

        memberRepository.save(member);
    }

    // 비밀번호 재설정
    public void resetPassword(Integer memberId, String senderEmail, String senderPassword) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("Member not found with ID: " + memberId));

        String newPassword = generateRandomPassword(8); // 랜덤 비밀번호 생성
        member.setPassword(newPassword); // 새 비밀번호 설정
        member.setIsInitialPassword(true); // 초기 상태로 변경

        memberRepository.save(member); // 비밀번호 저장

        // 동적 발신 이메일로 전송
        sendPasswordResetEmail(member, newPassword, senderEmail, senderPassword);
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
    // 이메일 전송 (발신자 동적 처리)
    private void sendPasswordResetEmail(Member member, String newPassword, String senderEmail, String senderPassword) {
        try {
            JavaMailSenderImpl mailSender = createMailSender(senderEmail, senderPassword); // 동적 메일 발송기 생성

            // 이메일 구성
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            String recipientEmail = member.getEmployee().getEmail(); // 수신자 이메일
            helper.setTo(recipientEmail);
            helper.setSubject("비밀번호 초기화 안내");
            helper.setText(String.format(
                    "안녕하세요, %s님.\n\n비밀번호가 초기화되었습니다.\n새 비밀번호: %s\n\n로그인 후 반드시 비밀번호를 변경해주세요.",
                    member.getEmployee().getName(),
                    newPassword
            ));

            // 이메일 전송
            mailSender.send(message);
            System.out.println("비밀번호 초기화 이메일 전송 성공");
        } catch (Exception e) {
            throw new RuntimeException("이메일 전송 실패", e);
        }
    }

    // 동적 JavaMailSenderImpl 생성 메서드
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

