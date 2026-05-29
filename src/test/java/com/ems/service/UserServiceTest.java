package com.ems.service;

import com.ems.dto.UserRequestDTO;
import com.ems.dto.UserResponseDTO;
import com.ems.entity.User;
import com.ems.exception.BadRequestException;
import com.ems.exception.DuplicateResourceException;
import com.ems.exception.ResourceNotFoundException;
import com.ems.mapper.UserMapper;
import com.ems.repository.UserRepository;
import com.ems.security.JwtUtil;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    @Mock
    private UserRepository repo;

    @Mock
    private PasswordEncoder encoder;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private UserMapper mapper;

    @InjectMocks
    private UserService service;

    private UserRequestDTO requestDTO;
    private User user;
    private UserResponseDTO responseDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        requestDTO = new UserRequestDTO();
        requestDTO.setName("Test User");
        requestDTO.setEmail("test@gmail.com");
        requestDTO.setPassword("123456");
        requestDTO.setPhone("9876543210");

        user = new User();
        user.setId(1L);
        user.setName("Test User");
        user.setEmail("test@gmail.com");
        user.setPassword("encodedPassword");

        responseDTO = new UserResponseDTO();
        responseDTO.setId(1L);
        responseDTO.setName("Test User");
        responseDTO.setEmail("test@gmail.com");
    }

    // ✅ REGISTER SUCCESS
    @Test
    void testRegister_success() {

        when(repo.findByEmail(requestDTO.getEmail())).thenReturn(null);
        when(encoder.encode(requestDTO.getPassword())).thenReturn("encodedPassword");
        when(mapper.toEntity(requestDTO, "encodedPassword")).thenReturn(user);
        when(repo.save(user)).thenReturn(user);
        when(mapper.toDTO(user)).thenReturn(responseDTO);

        UserResponseDTO result = service.register(requestDTO);

        assertNotNull(result);
        assertEquals("Test User", result.getName());

        verify(repo).save(user);
    }

    // ✅ REGISTER DUPLICATE EMAIL
    @Test
    void testRegister_duplicateEmail() {

        when(repo.findByEmail(requestDTO.getEmail())).thenReturn(user);

        assertThrows(DuplicateResourceException.class,
                () -> service.register(requestDTO));

        verify(repo, never()).save(any());
    }

    // ✅ LOGIN SUCCESS
    @Test
    void testLogin_success() {

        when(repo.findByEmail(requestDTO.getEmail())).thenReturn(user);
        when(encoder.matches(requestDTO.getPassword(), user.getPassword())).thenReturn(true);
        when(jwtUtil.generateToken(requestDTO.getEmail())).thenReturn("mockToken");

        String token = service.login(requestDTO.getEmail(), requestDTO.getPassword());

        assertNotNull(token);
        assertEquals("mockToken", token);

        verify(jwtUtil).generateToken(requestDTO.getEmail());
    }

    // ✅ LOGIN USER NOT FOUND
    @Test
    void testLogin_userNotFound() {

        when(repo.findByEmail(requestDTO.getEmail())).thenReturn(null);

        assertThrows(ResourceNotFoundException.class,
                () -> service.login(requestDTO.getEmail(), requestDTO.getPassword()));
    }

    // ✅ LOGIN INVALID PASSWORD
    @Test
    void testLogin_invalidPassword() {

        when(repo.findByEmail(requestDTO.getEmail())).thenReturn(user);
        when(encoder.matches(requestDTO.getPassword(), user.getPassword())).thenReturn(false);

        assertThrows(BadRequestException.class,
                () -> service.login(requestDTO.getEmail(), requestDTO.getPassword()));
    }
}