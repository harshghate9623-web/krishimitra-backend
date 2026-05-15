package com.krishimitra.backend.controller;

import com.krishimitra.backend.entity.FarmHistory;
import com.krishimitra.backend.repository.FarmHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/farm-history")
@RequiredArgsConstructor
public class HistoryController {

    private final FarmHistoryRepository farmHistoryRepository;

    @GetMapping("/user/{uid}")
    public ResponseEntity<List<FarmHistory>> getUserHistory(@PathVariable Long uid) {
        List<FarmHistory> history = farmHistoryRepository.findByUserUidOrderByCreatedAtDesc(uid);
        return ResponseEntity.ok(history);
    }

    @PostMapping("/add/{uid}")
    public ResponseEntity<String> addHistoryRecord(
            @PathVariable Long uid,
            @RequestBody FarmHistory request
    ) {
        request.setId(null); // Force as new entity
        request.setUserUid(uid);
        request.setCreatedAt(LocalDateTime.now());
        farmHistoryRepository.save(request);
        return ResponseEntity.ok("Farm history added successfully");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteHistoryRecord(@PathVariable Long id) {
        if (!farmHistoryRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        farmHistoryRepository.deleteById(id);
        return ResponseEntity.ok("Farm history deleted successfully");
    }
}
