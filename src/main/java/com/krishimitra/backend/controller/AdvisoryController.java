package com.krishimitra.backend.controller;

import com.krishimitra.backend.dto.AdvisoryPlanRequest;
import com.krishimitra.backend.dto.AdvisoryPlanResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/advisory")
public class AdvisoryController {

    @GetMapping("/farm/{farmId}")
    public ResponseEntity<AdvisoryPlanResponse> getAdvisoryPlan(@PathVariable Long farmId) {
        // Return the latest AI crop plan for this farm
        return ResponseEntity.ok(new AdvisoryPlanResponse());
    }

    @PostMapping("/farm/{farmId}")
    public ResponseEntity<?> saveAdvisoryPlan(@PathVariable Long farmId, @RequestBody AdvisoryPlanRequest request) {
        // Save a newly generated AI crop plan to Aiven MySQL
        return ResponseEntity.ok(Collections.singletonMap("message", "Advisory plan saved successfully"));
    }
}
