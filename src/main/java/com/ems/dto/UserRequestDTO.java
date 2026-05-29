package com.ems.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    @NotBlank(message = "Name required")
    private String name;

    @Email(message = "Invalid email")
    @NotBlank(message = "Email required")
    private String email;

    @NotBlank(message = "Phone required")
    private String phone;

    @NotBlank(message = "Password required")
    private String password;
}