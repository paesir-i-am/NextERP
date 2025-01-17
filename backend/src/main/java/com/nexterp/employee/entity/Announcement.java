package com.nexterp.employee.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@Table(name = "announcement")
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor // 모든 매개변수를 받는 생성자
public class Announcement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "announcement_id")
    private Integer announcementId; // 공지사항 ID (Primary Key)

    @Column(name = "title", nullable = false, length = 200)
    private String title; // 공지 제목

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content; // 공지 내용

    @ManyToOne
    @JoinColumn(name = "author_id", referencedColumnName = "employee_id", nullable = false)
    private Employee author; // 작성자 (Foreign Key, Employee 참조)

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // 작성일시

    @Column(name = "updated_at")    //수정되지 않았다면 NULL
    private LocalDateTime updatedAt; // 수정일시
}
