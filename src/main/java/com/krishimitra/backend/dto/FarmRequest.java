package com.krishimitra.backend.dto;

import lombok.Data;

@Data
public class FarmRequest {

    private String name;
    private String size;
    private String location;
    private String currentCrop;

    private Double soilN;
    private Double soilP;
    private Double soilK;
    private Integer progress;
}