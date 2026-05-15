package com.krishimitra.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "farm")
public class Farm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userUid;

    private String name;
    private String size;
    private String location;
    private String currentCrop;

    private Double soilN = 0.0;
    private Double soilP = 0.0;
    private Double soilK = 0.0;

    private Integer progress = 0;

    private LocalDateTime createdAt = LocalDateTime.now();
}