package com.krishimitra.backend.service;
import com.krishimitra.backend.dto.LoginRequest;
import java.util.Map;
import com.krishimitra.backend.dto.RegisterRequest;
import com.krishimitra.backend.entity.User;
import com.krishimitra.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import com.krishimitra.backend.dto.FarmRequest;
import com.krishimitra.backend.entity.Farm;
import java.time.LocalDateTime;
import com.krishimitra.backend.repository.FarmRepository;
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final FarmRepository farmRepository;

    @Transactional
    public String register(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail()))
            return "Email already exists";

        if (userRepository.existsByPhone(request.getPhone()))
            return "Phone already exists";

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setCreatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(user);

        if (request.getFarms() != null) {

            for (FarmRequest f : request.getFarms()) {

                Farm farm = new Farm();

                farm.setUserUid(savedUser.getUid());
                farm.setName(f.getName());
                farm.setSize(f.getSize());
                farm.setLocation(f.getLocation());
                farm.setCurrentCrop(f.getCurrentCrop());

                farm.setSoilN(f.getSoilN());
                farm.setSoilP(f.getSoilP());
                farm.setSoilK(f.getSoilK());

                farm.setProgress(0);

                farmRepository.save(farm);
            }
        }

        return "User created successfully";
    }
    public Object login(LoginRequest request) {

        User user = userRepository.findByEmail(request.getUsername())
                .orElseGet(() ->
                        userRepository.findByPhone(request.getUsername())
                                .orElse(null)
                );

        if (user == null) {
            return "User not found";
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return "Invalid password";
        }

        return Map.of(
                "message", "Login successful",
                "uid", user.getUid(),
                "name", user.getName(),
                "email", user.getEmail()
        );
    }
}