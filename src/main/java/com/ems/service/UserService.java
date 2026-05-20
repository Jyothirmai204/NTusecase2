package com.ems.service;

import com.ems.entity.User;
import com.ems.repository.UserRepository;
import com.ems.security.JwtUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository repo,
                       PasswordEncoder encoder,
                       JwtUtil jwtUtil) {
        this.repo = repo;
        this.encoder = encoder;
        this.jwtUtil = jwtUtil;
    }

    // ✅ REGISTER USER
    public User register(User user) {

        // ✅ Encode password
        user.setPassword(encoder.encode(user.getPassword()));

        return repo.save(user);
    }

    // ✅ LOGIN USER
    public String login(String username, String password) {

        User user = repo.findByUsername(username);

        // ✅ Check user exists
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        // ✅ Match password
        if (!encoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        // ✅ Generate JWT token
        return jwtUtil.generateToken(username);
    }
}