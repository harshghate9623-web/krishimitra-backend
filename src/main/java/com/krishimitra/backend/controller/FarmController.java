package com.krishimitra.backend.controller;
import com.krishimitra.backend.dto.AddFarmRequest;
import com.krishimitra.backend.dto.FarmUpdateRequest;
import com.krishimitra.backend.entity.Farm;
import com.krishimitra.backend.repository.FarmRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.time.LocalDateTime;
@RestController
@RequestMapping("/farm")
@RequiredArgsConstructor
public class FarmController {

    private final FarmRepository farmRepository;

    @GetMapping("/user/{uid}")
    public List<Farm> getUserFarms(@PathVariable Long uid) {
        return farmRepository.findByUserUid(uid);
    }

    @PutMapping("/{id}")
    public String updateFarm(
            @PathVariable Long id,
            @RequestBody FarmUpdateRequest request
    ) {
        Farm farm = farmRepository.findById(id).orElse(null);

        if (farm == null) return "Farm not found";

        farm.setName(request.getName());
        farm.setSize(request.getSize());
        farm.setLocation(request.getLocation());
        farm.setCurrentCrop(request.getCurrentCrop());
        farm.setSoilN(request.getSoilN());
        farm.setSoilP(request.getSoilP());
        farm.setSoilK(request.getSoilK());
        farm.setProgress(request.getProgress());

        farmRepository.save(farm);

        return "Farm updated";
    }

    // Accepts a proper DTO — prevents id/createdAt injection via raw entity
    @PostMapping("/add")
    public String addFarm(@RequestBody AddFarmRequest request) {
        Farm farm = new Farm();
        farm.setUserUid(request.getUserUid());
        farm.setName(request.getName());
        farm.setSize(request.getSize());
        farm.setLocation(request.getLocation());
        farm.setCurrentCrop(request.getCurrentCrop());
        farm.setSoilN(request.getSoilN() != null ? request.getSoilN() : 0.0);
        farm.setSoilP(request.getSoilP() != null ? request.getSoilP() : 0.0);
        farm.setSoilK(request.getSoilK() != null ? request.getSoilK() : 0.0);
        farm.setProgress(0);
        farm.setCreatedAt(LocalDateTime.now());
        farmRepository.save(farm);
        return "Farm added";
    }

    @DeleteMapping("/{id}")
    public String deleteFarm(@PathVariable Long id) {
        if (!farmRepository.existsById(id)) {
            return "Farm not found";
        }
        farmRepository.deleteById(id);
        return "Farm deleted";
    }
}
