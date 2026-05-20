//package com.ems.entity;
//
//import jakarta.persistence.*;
//import java.time.LocalDateTime;
//@Entity
//public class AuditLog {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//    private Long employeeId;
//    private String action;
//    private String source;
//    private LocalDateTime timestamp;
//
//    public Long getId()
//    {
//        return id;
//    }
//
//    public Long getEmployeeId()
//    {
//        return employeeId;
//    }
//    public void setEmployeeId(Long employeeId)
//    {
//        this.employeeId = employeeId;
//    }
//
//    public String getAction()
//    {
//        return action;
//    }
//    public void setAction(String action)
//    {
//        this.action = action;
//    }
//
//    public String getSource()
//    {
//        return source;
//    }
//    public void setSource(String source)
//    {
//        this.source = source;
//    }
//
//    public LocalDateTime getTimestamp()
//    {
//        return timestamp;
//    }
//    public void setTimestamp(LocalDateTime timestamp)
//    {
//        this.timestamp = timestamp;
//    }
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
public class AuditLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long employeeId;

    @NotBlank
    private String action;

    @NotBlank
    private String source;

    @NotNull
    private LocalDateTime timestamp;
}