package com.nexterp.employee.controller;

import com.nexterp.employee.dto.AnnouncementDTO;
import com.nexterp.employee.service.AnnouncementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/announcements")
public class AnnouncementController {

    private final AnnouncementService announcementService;

    public AnnouncementController(AnnouncementService announcementService) {
        this.announcementService = announcementService;
    }

    // 모든 공지사항 조회
    @GetMapping
    public List<AnnouncementDTO> getAllAnnouncements() {
        return announcementService.getAllAnnouncements();
    }

    // 특정 공지사항 조회
    @GetMapping("/{id}")
    public ResponseEntity<AnnouncementDTO> getAnnouncementById(@PathVariable Integer id) {
        AnnouncementDTO announcementDTO = announcementService.getAnnouncementById(id);
        return ResponseEntity.ok(announcementDTO);
    }

    // 공지사항 생성
    @PostMapping
    public ResponseEntity<AnnouncementDTO> createAnnouncement(@RequestBody AnnouncementDTO announcementDTO) {
        AnnouncementDTO savedAnnouncement = announcementService.saveAnnouncement(announcementDTO);
        return ResponseEntity.ok(savedAnnouncement);
    }

    // 공지사항 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAnnouncement(@PathVariable Integer id) {
        announcementService.deleteAnnouncement(id);
        return ResponseEntity.ok("Announcement deleted successfully.");
    }
}
