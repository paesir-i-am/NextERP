package com.nexterp.employee.repository;

import com.nexterp.employee.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PositionRepository  extends JpaRepository<Position, Integer> {

}