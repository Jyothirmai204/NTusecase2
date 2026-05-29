//package com.ems.service;
//
//import com.ems.entity.User;
//import com.ems.repository.UserRepository;
//import com.ems.security.JwtUtil;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//
//@Service
//public class UserService {
//
//    private final UserRepository repo;
//    private final PasswordEncoder encoder;
//    private final JwtUtil jwtUtil;
//
//    public UserService(UserRepository repo,
//                       PasswordEncoder encoder,
//                       JwtUtil jwtUtil) {
//        this.repo = repo;
//        this.encoder = encoder;
//        this.jwtUtil = jwtUtil;
//    }
//
//
//    public User register(User user) {
//        user.setPassword(encoder.encode(user.getPassword()));
//        return repo.save(user);
//    }
//
//
//    public String login(String email, String password) {
//
//        User user = repo.findByEmail(email);
//
//        if (user == null) {
//            throw new RuntimeException("User not found");
//        }
//
//        if (!encoder.matches(password, user.getPassword())) {
//            throw new RuntimeException("Invalid password");
//        }
//
//        return jwtUtil.generateToken(email);
//    }
//}

package com.ems.service;

import com.ems.dto.*;
import com.ems.entity.User;
import com.ems.exception.*;
import com.ems.mapper.UserMapper;
import com.ems.repository.UserRepository;
import com.ems.security.JwtUtil;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repo;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;
    private final UserMapper mapper;

    // ✅ REGISTER
    public UserResponseDTO register(UserRequestDTO dto) {

        if (repo.findByEmail(dto.getEmail()) != null) {
            throw new DuplicateResourceException("Email already exists");
        }

        String encodedPassword = encoder.encode(dto.getPassword());
        User user = mapper.toEntity(dto, encodedPassword);
        User saved = repo.save(user);
        return mapper.toDTO(saved);
    }

    // ✅ LOGIN
    public String login(String email, String password) {

        User user = repo.findByEmail(email);
        if (user == null) {
            throw new ResourceNotFoundException("User not found");
        }
        if (!encoder.matches(password, user.getPassword())) {
            throw new BadRequestException("Invalid password");
        }
        return jwtUtil.generateToken(email);
    }
}