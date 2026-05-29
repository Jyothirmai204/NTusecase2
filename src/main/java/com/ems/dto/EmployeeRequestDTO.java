package com.ems.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeRequestDTO {

    @NotBlank(message = "Name required")
    private String name;

    @NotBlank
    @Email(message = "Invalid email")
    private String email;

    @NotBlank
    private String department;

    @NotBlank
    private String dateOfJoining;

    @NotBlank
    @Pattern(regexp = "\\d{10}", message = "Phone must be 10 digits")
    private String phone;

    @NotNull
    private Double salary;

    @NotBlank
    private String status;
}