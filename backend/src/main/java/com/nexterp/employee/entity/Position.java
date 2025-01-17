package com.nexterp.employee.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "positions")
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "position_id")
    private Integer positionId; // 직위 ID (Primary Key)

    @Column(name = "title", nullable = false, length = 100)
    private String title; // 직위명 (사원, 대리 등)

    @Column(name = "role", nullable = false, length = 50)
    private String role;
}
