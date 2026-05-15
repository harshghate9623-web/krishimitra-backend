package com.krishimitra.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "farm_history")
public class FarmHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userUid;

    private String farmName;
    private String cropName;
    private String estimatedYield;
    private String completedDate;
    private String farmSize;

    private LocalDateTime createdAt = LocalDateTime.now();
}
