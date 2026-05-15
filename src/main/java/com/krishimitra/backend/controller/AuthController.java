package com.krishimitra.backend.controller;
import com.krishimitra.backend.dto.LoginRequest;
import com.krishimitra.backend.dto.RegisterRequest;
import com.krishimitra.backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public Map<String, String> register(
            @RequestBody RegisterRequest request) {

        String msg = authService.register(request);

        return Map.of("message", msg);
    }
    @PostMapping("/login")
    public Object login(@RequestBody LoginRequest request) {
        return authService.login(request);
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody Map<String, String> request) {
        // String email = request.get("email");
        // Generate reset token, save to DB, send email
        return ResponseEntity.ok(Map.of("message", "Password reset instructions sent to email"));
    }
}