//package com.ems.entity;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.*;
//import jakarta.xml.bind.annotation.*;
//import lombok.*;
//
//@XmlRootElement(name = "Employee")
//@XmlAccessorType(XmlAccessType.FIELD)
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class Employee {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @NotBlank(message = "Name cannot be empty")
//    private String name;
//
//    @Email(message = "Invalid email format")
//    @NotBlank(message = "Email cannot be empty")
//    private String email;
//
//    @NotBlank(message = "Department cannot be empty")
//    private String department;
//
//    @NotBlank(message = "Date of Joining cannot be empty")
//    private String dateOfJoining;
//}

package com.ems.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import jakarta.xml.bind.annotation.*;
import lombok.*;
import java.time.LocalDateTime;

@XmlRootElement(name = "Employee")
@XmlAccessorType(XmlAccessType.FIELD)
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Name cannot be empty")
    @Size(min = 3, message = "Name should be at least 3 characters")
    private String name;

    @NotBlank(message = "Email cannot be empty")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Department cannot be empty")
    private String department;

    @NotBlank(message = "Date cannot be empty")
    private String dateOfJoining;

    @NotBlank(message = "Phone cannot be empty")
    @Pattern(regexp = "\\d{10}", message = "Phone must be 10 digits")
    private String phone;

    @NotNull(message = "Salary required")
    @Min(value = 1000, message = "Salary must be above 1000")
    private Double salary;

    @NotBlank(message = "Status required")
    private String status; // ACTIVE / INACTIVE

    private LocalDateTime createdAt;
}