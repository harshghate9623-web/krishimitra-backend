package com.krishimitra.backend.dto;

import lombok.Data;

@Data
public class FarmHistoryRequest {
    private Long farmId;
    private String action;
    private String details;
}
