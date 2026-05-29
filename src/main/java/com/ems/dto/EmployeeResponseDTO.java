package com.ems.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String department;
    private String dateOfJoining;
    private String phone;
    private Double salary;
    private String status;
    private LocalDateTime createdAt;
}