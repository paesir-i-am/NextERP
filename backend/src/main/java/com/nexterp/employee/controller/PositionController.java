package com.nexterp.employee.controller;

import com.nexterp.employee.dto.PositionDTO;
import com.nexterp.employee.service.PositionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/positions")
public class PositionController {

    private final PositionService positionService;

    public PositionController(PositionService positionService) {
        this.positionService = positionService;
    }

    // 모든 직위 조회
    @GetMapping
    public ResponseEntity<List<PositionDTO>> getAllPositions() {
        return ResponseEntity.ok(positionService.getAllPositions());
    }

    // 특정 직위 조회
    @GetMapping("/{id}")
    public ResponseEntity<PositionDTO> getPositionById(@PathVariable Integer id) {
        PositionDTO position = positionService.getPositionById(id);
        if (position != null) {
            return ResponseEntity.ok(position);
        }
        return ResponseEntity.notFound().build();
    }

    // 직위 생성
    @PostMapping
    public ResponseEntity<PositionDTO> createPosition(@RequestBody PositionDTO positionDTO) {
        return ResponseEntity.ok(positionService.createPosition(positionDTO));
    }

    // 직위 수정
    @PutMapping("/{id}")
    public ResponseEntity<PositionDTO> updatePosition(@PathVariable Integer id, @RequestBody PositionDTO positionDTO) {
        PositionDTO updatedPosition = positionService.updatePosition(id, positionDTO);
        if (updatedPosition != null) {
            return ResponseEntity.ok(updatedPosition);
        }
        return ResponseEntity.notFound().build();
    }

    // 직위 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePosition(@PathVariable Integer id) {
        boolean deleted = positionService.deletePosition(id);
        if (deleted) {
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
