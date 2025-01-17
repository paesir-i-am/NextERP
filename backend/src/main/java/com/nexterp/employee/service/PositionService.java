package com.nexterp.employee.service;

import com.nexterp.employee.entity.Position;
import com.nexterp.employee.dto.PositionDTO;
import com.nexterp.employee.repository.PositionRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PositionService {

    private final PositionRepository positionRepository;

    public PositionService(PositionRepository positionRepository) {
        this.positionRepository = positionRepository;
    }

    // Position -> PositionDTO 변환
    private PositionDTO convertToDTO(Position position) {
        return new PositionDTO(
                position.getPositionId(),
                position.getTitle(),
                position.getRole()
        );
    }

    // DTO -> Position 변환
    private Position convertToEntity(PositionDTO positionDTO) {
        return new Position(
                positionDTO.getPositionId(),
                positionDTO.getTitle(),
                positionDTO.getRole()
        );
    }

    // 모든 직위 조회
    public List<PositionDTO> getAllPositions() {
        return positionRepository.findAll()
                .stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // 특정 직위 조회
    public PositionDTO getPositionById(Integer id) {
        return positionRepository.findById(id)
                .map(this::convertToDTO)
                .orElse(null);
    }

    // 직위 생성
    public PositionDTO createPosition(PositionDTO positionDTO) {
        Position position = convertToEntity(positionDTO);
        Position savedPosition = positionRepository.save(position);
        return convertToDTO(savedPosition);
    }

    // 직위 수정
    public PositionDTO updatePosition(Integer id, PositionDTO positionDTO) {
        return positionRepository.findById(id).map(existingPosition -> {
            existingPosition.setTitle(positionDTO.getTitle());
            existingPosition.setRole(positionDTO.getRole());
            return convertToDTO(positionRepository.save(existingPosition));
        }).orElse(null);
    }

    // 직위 삭제
    public boolean deletePosition(Integer id) {
        if (positionRepository.existsById(id)) {
            positionRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
