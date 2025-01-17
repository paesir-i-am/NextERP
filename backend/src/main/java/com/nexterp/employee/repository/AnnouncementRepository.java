package com.nexterp.employee.repository;

import com.nexterp.employee.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AnnouncementRepository  extends JpaRepository<Announcement, Integer> {
}
