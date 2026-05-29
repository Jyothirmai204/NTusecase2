//package com.ems.entity;
//
//import jakarta.persistence.*;
//import jakarta.validation.constraints.*;
//import lombok.*;
//import java.time.LocalDateTime;
//
//@Entity
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//public class AuditLog {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    private Long employeeId;
//
//    @NotBlank
//    private String action;
//
//    @NotBlank
//    private String source;
//
//    @NotNull
//    private LocalDateTime timestamp;
//}

package com.ems.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "audit_logs")
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Employee ID required")
    private Long employeeId;

    @NotBlank(message = "Action cannot be empty")
    private String action; // CREATE / UPDATE / DELETE

    @NotBlank(message = "Source cannot be empty")
    private String source; // REST / JMS

    @NotNull(message = "Timestamp required")
    private LocalDateTime timestamp;
}
