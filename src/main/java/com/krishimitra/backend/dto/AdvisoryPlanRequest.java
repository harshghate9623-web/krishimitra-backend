package com.krishimitra.backend.dto;

import lombok.Data;

@Data
public class AdvisoryPlanRequest {
    private String crop;
    private String soilType;
    private String notes;
}
