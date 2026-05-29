//package com.ems.controller;
//
//import com.ems.entity.User;
//import com.ems.service.UserService;
//import org.springframework.web.bind.annotation.*;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//    private final UserService service;
//
//    public AuthController(UserService service) {
//        this.service = service;
//    }
//
//
//    @PostMapping("/register")
//    public User register(@RequestBody User user) {
//        return service.register(user);
//    }
//
//
//    @PostMapping("/login")
//    public String login(@RequestBody User user) {
//        return service.login(user.getEmail(), user.getPassword());
//    }
//}

package com.ems.controller;

import com.ems.dto.*;
import com.ems.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService service;

    // ✅ REGISTER
    @PostMapping("/register")
    public ResponseEntity<ResponseStructure<UserResponseDTO>> register(
            @Valid @RequestBody UserRequestDTO dto) {

        UserResponseDTO data = service.register(dto);

        ResponseStructure<UserResponseDTO> response = new ResponseStructure<>(
                "success",
                "User registered successfully",
                data
        );

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ✅ LOGIN
    @PostMapping("/login")
    public ResponseEntity<ResponseStructure<String>> login(
            @RequestBody UserRequestDTO dto) {

        String token = service.login(dto.getEmail(), dto.getPassword());

        ResponseStructure<String> response = new ResponseStructure<>(
                "success",
                "Login successful",
                token
        );

        return ResponseEntity.ok(response);
    }
}

