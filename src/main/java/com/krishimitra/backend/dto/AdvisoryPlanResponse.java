package com.krishimitra.backend.dto;

import lombok.Data;

@Data
public class AdvisoryPlanResponse {
    private Long id;
    private Long farmId;
    private String crop;
    private String planDetails;
}
