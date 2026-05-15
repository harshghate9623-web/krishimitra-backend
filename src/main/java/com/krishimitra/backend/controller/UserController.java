package com.krishimitra.backend.controller;

import com.krishimitra.backend.entity.User;
import com.krishimitra.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.Map;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/{uid}")
    public User getUser(@PathVariable Long uid) {
        return userRepository.findById(uid).orElse(null);
    }

    @PutMapping("/{uid}")
    public String updateUser(
            @PathVariable Long uid,
            @RequestBody User request
    ) {

        User user = userRepository.findById(uid).orElse(null);

        if (user == null) return "User not found";

        user.setName(request.getName());
        user.setPhone(request.getPhone());

        userRepository.save(user);

        return "Profile updated successfully";
    }

    @PostMapping("/{uid}/profile-image")
    public ResponseEntity<?> uploadProfileImage(@PathVariable Long uid, @RequestParam("file") MultipartFile file) {
        // Upload file to S3/Cloudinary/Local Directory
        // Update user's profileImage URL in Aiven MySQL
        return ResponseEntity.ok(Map.of("message", "Profile image uploaded successfully"));
    }
}