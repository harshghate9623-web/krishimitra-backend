package com.krishimitra.backend.dto;

import lombok.Data;

@Data
public class FarmHistoryResponse {
    private Long id;
    private Long farmId;
    private String action;
    private String details;
    private String timestamp;
}
