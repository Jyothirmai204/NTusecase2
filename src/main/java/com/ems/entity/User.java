
package com.ems.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 3, message = "Name must be at least 3 chars")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    @Column(unique = true)
    private String email;

    @NotBlank(message = "Phone cannot be empty")
    @Pattern(regexp = "\\d{10}", message = "Phone must be 10 digits")
    private String phone;

    @NotBlank(message = "Password cannot be empty")
    @Size(min = 6, message = "Password must be at least 6 characters")
    private String password;
}
