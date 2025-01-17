package com.nexterp.member.controller;

import com.nexterp.member.entity.Member;
import com.nexterp.member.service.MemberService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        Member createdMember = memberService.createMember(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdMember);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Member> getMemberById(@PathVariable Integer id) {
        Member member = memberService.getMemberById(id);
        return ResponseEntity.ok(member);
    }

    @PutMapping("/{id}/change-password")
    public ResponseEntity<Void> changePassword(@PathVariable Integer id, @RequestBody String newPassword) {
        memberService.changePassword(id, newPassword);
        return ResponseEntity.ok().build();
    }
    // 비밀번호 초기화 API (동적 발신 이메일 처리)
    @PutMapping("/{id}/reset-password")
    public ResponseEntity<Void> resetPassword(
            @PathVariable Integer id,
            @RequestParam String senderEmail,
            @RequestParam String senderPassword) {
        memberService.resetPassword(id, senderEmail, senderPassword);
        return ResponseEntity.ok().build();
    }
}
