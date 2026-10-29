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

    // Returns only safe fields — never exposes password hash or internal fields
    @GetMapping("/{uid}")
    public ResponseEntity<?> getUser(@PathVariable Long uid) {
        User user = userRepository.findById(uid).orElse(null);
        if (user == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of(
                "uid", user.getUid(),
                "name", user.getName() != null ? user.getName() : "",
                "email", user.getEmail() != null ? user.getEmail() : "",
                "phone", user.getPhone() != null ? user.getPhone() : ""
        ));
    }

    // Accepts a map — only name and phone can be updated (email is immutable after registration)
    @PutMapping("/{uid}")
    public String updateUser(
            @PathVariable Long uid,
            @RequestBody Map<String, String> request
    ) {
        User user = userRepository.findById(uid).orElse(null);

        if (user == null) return "User not found";

        if (request.containsKey("name")) user.setName(request.get("name"));
        if (request.containsKey("phone")) user.setPhone(request.get("phone"));

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
