package com.ems.mapper;

import com.ems.dto.UserRequestDTO;
import com.ems.dto.UserResponseDTO;
import com.ems.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    // ✅ DTO → ENTITY
    public User toEntity(UserRequestDTO dto, String encodedPassword) {

        return new User(
                null,
                dto.getName(),
                dto.getEmail(),
                dto.getPhone(),
                encodedPassword
        );
    }

    // ✅ ENTITY → RESPONSE
    public UserResponseDTO toDTO(User user) {

        return new UserResponseDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone()
        );
    }
}
