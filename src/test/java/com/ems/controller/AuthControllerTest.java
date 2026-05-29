package com.ems.controller;

import com.ems.dto.ResponseStructure;
import com.ems.dto.UserRequestDTO;
import com.ems.dto.UserResponseDTO;
import com.ems.service.UserService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Mock
    private UserService service;

    @InjectMocks
    private AuthController controller;

    private UserRequestDTO requestDTO;
    private UserResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        requestDTO = new UserRequestDTO();
        requestDTO.setName("Test User");
        requestDTO.setEmail("test@gmail.com");
        requestDTO.setPassword("123456");
        requestDTO.setPhone("1234567890");

        responseDTO = new UserResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("Test User");
        responseDTO.setEmail("test@gmail.com");
    }

    // ✅ REGISTER TEST
    @Test
    void testRegister_success() {

        when(service.register(requestDTO)).thenReturn(responseDTO);

        ResponseEntity<ResponseStructure<UserResponseDTO>> response =
                controller.register(requestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        ResponseStructure<UserResponseDTO> body = response.getBody();

        assertEquals("success", body.getStatus());
        assertEquals("User registered successfully", body.getMessage());
        assertEquals(responseDTO, body.getData());

        verify(service, times(1)).register(requestDTO);
    }


    // ✅ LOGIN TEST
    @Test
    void testLogin_success() {

        String mockToken = "mock-jwt-token";

        when(service.login(requestDTO.getEmail(), requestDTO.getPassword()))
                .thenReturn(mockToken);

        ResponseEntity<ResponseStructure<String>> response =
                controller.login(requestDTO);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());

        ResponseStructure<String> body = response.getBody();

        assertEquals("success", body.getStatus());
        assertEquals("Login successful", body.getMessage());
        assertEquals(mockToken, body.getData());

        verify(service, times(1))
                .login(requestDTO.getEmail(), requestDTO.getPassword());
    }
}