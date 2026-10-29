package com.krishimitra.backend.dto;

import lombok.Data;

@Data
public class AddFarmRequest {
    private Long userUid;
    private String name;
    private String size;
    private String location;
    private String currentCrop;
    private Double soilN;
    private Double soilP;
    private Double soilK;
}

