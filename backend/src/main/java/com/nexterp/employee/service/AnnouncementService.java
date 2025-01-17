package com.nexterp.employee.service;

import com.nexterp.employee.dto.AnnouncementDTO;
import com.nexterp.employee.entity.Announcement;
import com.nexterp.employee.entity.Employee;
import com.nexterp.employee.repository.AnnouncementRepository;
import com.nexterp.employee.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;
    private final EmployeeRepository employeeRepository;

    public AnnouncementService(AnnouncementRepository announcementRepository, EmployeeRepository employeeRepository) {
        this.announcementRepository = announcementRepository;
        this.employeeRepository = employeeRepository;
    }

    // DTO 변환 메서드
    private AnnouncementDTO convertToDTO(Announcement announcement) {
        return new AnnouncementDTO(
                announcement.getAnnouncementId(),
                announcement.getTitle(),
                announcement.getContent(),
                announcement.getAuthor().getId(),
                announcement.getAuthor().getName(),
                announcement.getCreatedAt(),
                announcement.getUpdatedAt()
        );
    }

    // 전체 공지사항 조회
    public List<AnnouncementDTO> getAllAnnouncements() {
        List<Announcement> announcements = announcementRepository.findAll();
        return announcements.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 특정 공지사항 조회
    public AnnouncementDTO getAnnouncementById(Integer id) {
        Announcement announcement = announcementRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid Announcement ID: " + id));
        return convertToDTO(announcement);
    }

    // 공지사항 저장
    public AnnouncementDTO saveAnnouncement(AnnouncementDTO announcementDTO) {
        Employee author = employeeRepository.findById(announcementDTO.getAuthorId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid Employee ID: " + announcementDTO.getAuthorId()));

        Announcement announcement = new Announcement(
                null,
                announcementDTO.getTitle(),
                announcementDTO.getContent(),
                author,
                announcementDTO.getCreatedAt(),
                announcementDTO.getUpdatedAt()
        );

        return convertToDTO(announcementRepository.save(announcement));
    }

    // 공지사항 삭제
    public void deleteAnnouncement(Integer id) {
        announcementRepository.deleteById(id);
    }
}
