package com.nexterp.employee.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AnnouncementDTO {

    private Integer id; // 공지사항 ID
    private String title; // 공지 제목
    private String content; // 공지 내용
    private Integer authorId; // 작성자 ID
    private String authorName; // 작성자 이름
    private LocalDateTime createdAt; // 작성일시
    private LocalDateTime updatedAt; // 수정일시
}
